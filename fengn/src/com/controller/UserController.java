package com.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.model.User;
import com.service.AgentService;
import com.service.UserService;


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
			session.setAttribute("userid", user.getId());
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
	
	@RequestMapping("/loginOut")
	public String logout(HttpSession session){
		session.removeAttribute("agentcode");
		session.removeAttribute("user");
		session.removeAttribute("groupId");
		session.removeAttribute("agentId");
		return "pages/login";
	}
	
}
