package com.cmoit.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cmoit.model.CmoitCard;
import com.cmoit.service.CMoitAgentService;
import com.cmoit.service.CMoitCardAgentService;
import com.common.CodeUtil;
import com.model.Agent;
import com.model.Grid;
import com.model.Pagination;
import com.model.QueryData;
import com.model.UnicomInfoVo;
import com.service.UserService;

import net.sf.json.JSONObject;

@RequestMapping("/cmoit/card")
@Controller
public class CMoitAgentController {
	 
	
	@Autowired
	CMoitAgentService service ;
	@Autowired
	UserService userService ;
	@Autowired
	CMoitCardAgentService cardAgentService;
	
	
	@RequestMapping("/card_move")
	public void moveCard(String iccids , String agentid ,String pacId ,String table, HttpServletResponse response  ){
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			service.updateCardAgent(iccids,agentid,table,pacId );
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
	
	

	@ResponseBody
	@RequestMapping("/card_query/{agentId}")
	public Grid queryCard(@PathVariable("agentId") Integer agentId, HttpServletResponse response, 
			HttpServletRequest request  ,HttpSession session , QueryData qo) {
		Grid grid = new  Grid();
		String pageNo = request.getParameter("pageNumber");
		String pageSize = request.getParameter("pageSize");
		//System.out.println(userName);
		Pagination page =  new Pagination(pageNo, pageSize , 100) ;
	    CodeUtil.initPagination(page);
	    List<CmoitCard>  list = cardAgentService.queryCardInfo(agentId , page , qo , "cmoit");
	    Long total = (long)cardAgentService.queryCardTotal(agentId, qo , "cmoit");
	    grid.setRows(list);
	    grid.setTotal(total);
	    return grid;
	}
	
	@ResponseBody
	@RequestMapping("/cmoit_kickback_query/{agentId}")
	public Grid   queryCmccKickback(@PathVariable("agentId") Integer agentId, HttpServletResponse response, 
			HttpServletRequest request  ,HttpSession session , QueryData qo ) {
		String pageNo = request.getParameter("pageNumber");
		String pageSize = request.getParameter("pageSize");
		//System.out.println(userName);
		Grid grid = new Grid();
		Pagination page =  new Pagination(pageNo, pageSize , 100) ;
	    CodeUtil.initPagination(page);
	    List<Map<String,String>>  list = cardAgentService.queryKickbackInfo(agentId, qo  , page , qo.getTimeType(),"cmoit");
	    Map<String , Double > map = cardAgentService.queryKickbackTotal(agentId , qo , qo.getTimeType(),"cmoit");
	   grid.setRows(list);
	   grid.setTotal(map.get("total").longValue());
	    return grid;
	}

	@ResponseBody
	@RequestMapping("/all_kickback_query/{agentId}")
	public Grid   queryAllKickback(@PathVariable("agentId") Integer agentId, HttpServletResponse response,
									HttpServletRequest request  ,HttpSession session , QueryData qo ) {
		String pageNo = request.getParameter("pageNumber");
		String pageSize = request.getParameter("pageSize");
		//System.out.println(userName);
		Grid grid = new Grid();
		Pagination page =  new Pagination(pageNo, pageSize , 100) ;
		CodeUtil.initPagination(page);
		List<Map<String,String>>  list = cardAgentService.queryAllKickbackInfo(agentId, qo  , page , qo.getTimeType(),"cmcc,unicom");
		Map<String , Double > map = cardAgentService.queryAllKickbackTotal(agentId , qo , qo.getTimeType(),"cmcc,unicom");
		grid.setRows(list);
		grid.setTotal(map.get("total").longValue());
		return grid;
	}
	
}
