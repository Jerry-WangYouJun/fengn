package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.common.CodeUtil;
import com.model.Pagination;
import com.model.User;
import com.service.AgentService;
import com.service.UserService;

import net.sf.json.JSONObject;


@Controller
public class UserController {
	 
	@Autowired
	UserService service ;
	
	@Autowired
	AgentService agentService ;
	
	@RequestMapping("/checkUser")
	public String checkUser(User user , HttpServletRequest request , HttpSession session){
		user = service.checkUser(user);
		if(user.getAgentCode() != null ){
			session.setAttribute("agentcode", user.getAgentCode());
			session.setAttribute("user", user.getUserName());
			session.setAttribute("agentId", user.getAgentId());
			session.setAttribute("roleid", user.getRoleId());
			session.setAttribute("groupId", user.getGroupId());
//			if(ContextString.ROLE_ADMIN.equals(user.getRoleId())
//					|| ContextString.ROLE_AGENT.equals(user.getRoleId())) {
//				
//				return "/agent/index" ;
//			}else {
//				return "/unicom/index" ;
//			}
			return "pages/newIndex2" ;
		}else{
			request.setAttribute("msg", "用户名或者密码错误");
			return "pages/login" ;
		}
	}
	
	@RequestMapping("/login")
	public String login(){
		return "pages/login";
	}
	
//	@RequestMapping("/unicom")
//	public String loginUnicom() {
//		return "/unicom/login";
//	}
	@RequestMapping("/loginOut")
	public String logout(HttpSession session){
		session.removeAttribute("agentcode");
		session.removeAttribute("user");
		session.removeAttribute("groupId");
		session.removeAttribute("agentId");
		return "pages/login";
	}
	
	@RequestMapping("/user_query")
	public ModelAndView quetyList(User user , HttpSession session , String pageNo , String pageSize){
		  ModelAndView mv = new ModelAndView("/agent/user/user_list");
		  String agentCode = session.getAttribute("agentcode").toString();
		  user.setAgentCode(agentCode);
		  Pagination page = new Pagination();
		  List<User> totalList = service.queryList(user , page );
		  page.setPageNo(pageNo==null?1:Integer.valueOf(pageNo));
		  page.setPageSize(pageSize ==null?50:Integer.valueOf(pageSize));
		  page.setTotal(totalList.size());
		  CodeUtil.initPagination(page);
		  List<User> list = service.queryList(user , page);
		  mv.addObject("list", list);
		  mv.addObject("page", page);
		  return  mv ;
	}
	
	@RequestMapping("/addInit")
	public ModelAndView addInit(){
		ModelAndView  mv = new ModelAndView( "/agent/user/user_add");
		User user = new User();
		mv.addObject("user", user);
		return mv;
	}
	
	@RequestMapping("/insert")
	public void insert(User user , HttpSession session ,HttpServletResponse response ){
		user.setPwd(user.getPwd().split(",")[0]);
		if(user.getId()!=null && user.getId() >0){
			service.update(user);
		}else{
			service.insert(user);
		}
		response.setContentType("text/text;charset=UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			JSONObject json = new JSONObject();
			json.put("msg", "操作成功");
			json.put("success", true);
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	
	@RequestMapping(value="/updateInit/{id}")
	public ModelAndView  updateInit(@PathVariable("id") Integer id){
		ModelAndView  mv = new ModelAndView( "/agent/user/user_add");
		User user = new User();
		user.setId(id );
		List<User> list = service.queryList(user , new Pagination() );
		mv.addObject("user", list.get(0));
		return mv;
	}
	
	@RequestMapping(value="/user_delete/{id}")
	public void delete(@PathVariable("id") Integer id, HttpServletResponse response ){
		response.setContentType("text/text;charset=UTF-8");
		PrintWriter out;
		try {
			service.delete(id);
			out = response.getWriter();
			JSONObject json = new JSONObject();
			json.put("msg", "操作成功");
			json.put("success", true);
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
