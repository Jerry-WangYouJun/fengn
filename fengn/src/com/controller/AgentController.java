package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.common.CodeUtil;
import com.common.ContextString;
import com.model.Agent;
import com.model.Pagination;
import com.model.QueryData;
import com.model.User;
import com.service.AgentService;
import com.service.UserService;

@RequestMapping("/agent")
@Controller
public class AgentController {
	 
	
	@Autowired
	AgentService service ;
	@Autowired
	UserService userService ;
	
	@ResponseBody
	@RequestMapping("/user_query")
	public List<User>  queryTest( HttpServletResponse response, HttpServletRequest request  ,HttpSession session ) {
		String userName = request.getParameter("userName");
		String pageNo = request.getParameter("pageNo");
		String pageSize = request.getParameter("pageSize");
	    User user = new User();
	    user.setUserName(userName);
	    String agentCode = session.getAttribute("agentcode").toString();
		user.setAgentCode(agentCode);
		Pagination page =  new Pagination(pageNo, pageSize) ;
	    CodeUtil.initPagination(page);
		List<User> list = userService.queryList(user , page );
		return list;
	}
	
	@RequestMapping("/card_move")
	public void moveCard(String iccids , String agentid ,HttpServletResponse response  ){
		PrintWriter out;
		try {
			service.updateCardAgent(iccids,agentid);
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
	
	@RequestMapping("/move")
	public ModelAndView  moveCardInit(QueryData  qo , HttpSession session , String pageNo , String pageSize ,String iccids){
		ModelAndView  mv = new ModelAndView("/agent/agent/agent_move");
		List<Agent> list =  new ArrayList<>();
		String agentCode = session.getAttribute("agentcode").toString();
		Pagination page = new Pagination();
		QueryData  qd = new QueryData();
		qd.setAgentid(qo.getMoveAgent());
		List<Agent> listAgent = service.queryList(qd , page);
		if(listAgent != null && listAgent.size() >0) {
			 agentCode = listAgent.get(0).getCode();
		}
		qo.setAgentCode(agentCode);
		page.setPageNo(pageNo==null?1:Integer.valueOf(pageNo));
		page.setPageSize(pageSize ==null?50:Integer.valueOf(pageSize));
		page.setTotal(service.queryTatol(qo ));
		CodeUtil.initPagination(page);
		list = service.queryList(qo , page);
		mv.addObject("list", list);
		 mv.addObject("page", page);
		 mv.addObject("ids", iccids);
		return mv ;
	}
	
	
	
	@RequestMapping("/agent_query")
	public ModelAndView  agentList(QueryData  qo , HttpSession session , String pageNo , String pageSize ){
		ModelAndView  mv = new ModelAndView("/agent/agent/agent_query");
		List<Agent> list =  new ArrayList<>();
		String agentCode = session.getAttribute("agentcode").toString();
		qo.setAgentCode(agentCode);
		Pagination page = new Pagination();
		page.setPageNo(pageNo==null?1:Integer.valueOf(pageNo));
		page.setPageSize(pageSize ==null?50:Integer.valueOf(pageSize));
		page.setTotal(service.queryTatol(qo ));
		CodeUtil.initPagination(page);
		List<String> typeList = service.getTypeList();
		list = service.queryList(qo , page);
		mv.addObject("list", list);
		 mv.addObject("page", page);
		 mv.addObject("typeList", typeList);
		return mv ;
	}
	
	@RequestMapping("/checkAgent")
	public void  checkAgent(String agentName , HttpServletResponse response ,HttpSession session   ){
		QueryData qo = new QueryData();
		qo.setAgentName(agentName);
		qo.setAgentCode(session.getAttribute("agentcode").toString());
		List<Agent> list =  service.queryList(qo , new Pagination());
		response.setContentType("text/text;charset=UTF-8");
		PrintWriter out;
		try {
			
			out = response.getWriter();
			JSONObject json = new JSONObject();
			json.put("msg", "操作成功");
			if(list.size() > 0 ){
				 json.put("success", true);
				 json.put("agentid", list.get(0).getId());
			}else{
				json.put("success", false);
			}
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/addInit")
	public ModelAndView addInit(){
		ModelAndView  mv = new ModelAndView( "/agent/agent/agent_add");
		Agent agent = new Agent();
		mv.addObject("agent", agent);
		return mv;
	}
	
	@RequestMapping("/insert")
	public void insert(Agent agent , HttpSession session ,HttpServletResponse response ){
		if(agent.getId()!=null && agent.getId() >0){
			service.update(agent);
		}else{
			agent.setCreater(session.getAttribute("user").toString());
			agent.setCode(session.getAttribute("agentcode").toString());
			int agentId = service.insert(agent );
			User user = new User();
			user.setAgentId(agentId);
			user.setUserNo(agent.getUserNo());
			user.setUserName(agent.getName());
			user.setPwd("123456");
			String roleId = session.getAttribute("roleid").toString();
			if(ContextString.ROLE_ADMIN.equals(roleId)
					|| ContextString.ROLE_AGENT.equals(roleId)) {
				 user.setRoleId("2");
			}else{
				user.setRoleId("4");
			}
			userService.insert(user);
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
		ModelAndView  mv = new ModelAndView( "/agent/agent/agent_add");
		QueryData qo = new QueryData();
		qo.setAgentid(id+ "");
		List<Agent> list = service.queryList(qo , new Pagination());
		mv.addObject("agent", list.get(0));
		return mv;
	}
	
	@RequestMapping(value="/agent_delete/{id}")
	public void delete(@PathVariable("id") Integer id, HttpServletResponse response ){
		PrintWriter out;
		try {
			service.delete(id);
			userService.delete(id);
			response.setCharacterEncoding("UTF-8"); 
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
	
	@RequestMapping(value="/checkUser")
	public void checkUser(String userNo, HttpServletResponse response ) {
		PrintWriter out;
		int num = service.checkUser(userNo);
		try {
			out = response.getWriter();
			JSONObject json = new JSONObject();
			if(num > 0 ) {
				json.put("success", true);
			}else {
				json.put("success", false);
			}
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
