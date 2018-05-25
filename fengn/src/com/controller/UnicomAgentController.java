package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.common.CodeUtil;
import com.model.Agent;
import com.model.Pagination;
import com.model.QueryData;
import com.service.UnicomAgentService;
import com.service.UnicomCardAgentService;
import com.service.UserService;

@RequestMapping("/unicom")
@Controller
public class UnicomAgentController {
	 
	
	@Autowired
	UnicomAgentService service ;
	@Autowired
	UserService userService ;
	@Autowired
	UnicomCardAgentService cardAgentService;
	
	
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
		ModelAndView  mv = new ModelAndView("/unicom/agent_move");
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
	
	@RequestMapping(value="/status/{id}")
	public void delete(@PathVariable("id") Integer id, HttpServletResponse response ){
		PrintWriter out;
		try {
			service.updateOrderStatus(id);
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
	
}
