package com.controller;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.common.CodeUtil;
import com.model.Grid;
import com.model.Pagination;
import com.model.QueryData;
import com.model.UnicomInfoVo;
import com.service.AgentService;
import com.service.UnicomCardAgentService;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("/unicom")
public class UnicomTreeController {
	  
		@Autowired
		AgentService service ;
		
		@Autowired
		UnicomCardAgentService cardAgentService;
	
//		@RequestMapping("/tree")
//	    public void getTreeData(HttpSession session, HttpServletResponse response , HttpServletRequest request){
//	    	  try {
//	    		List<TreeNode> list = new ArrayList<>();
//	    		TreeNode  treeNode = new TreeNode();
//	    		treeNode.setText("SIM卡管理");
//	    		list.add(treeNode);
//	    		//子节点
//	    		List<TreeNode> listChild = new ArrayList<>();
//	    		Integer agentid = Integer.valueOf(session.getAttribute("agentId").toString());
//	    		listChild = service.getTreeData(agentid , "card" , request);
//	    		treeNode.setChildren(listChild);
//	    		JSONArray json = JSONArray.fromObject(treeNode);
//    		    response.setCharacterEncoding("utf-8");
//				response.getWriter().write(json.toString());
//				response.getWriter().flush(); 
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//	    }
//		
//		@RequestMapping("/kickback")
//	    public void getKickbackData(HttpSession session, HttpServletResponse response , HttpServletRequest request ){
//	    	  try {
//	    		
//	    		List<TreeNode> list = new ArrayList<>();
//	    		TreeNode  treeNode = new TreeNode();
//	    		treeNode.setText("返佣管理");
//	    		list.add(treeNode);
//	    		//子节点
//	    		List<TreeNode> listChild = new ArrayList<>();
//	    		Integer agentid = Integer.valueOf(session.getAttribute("agentId").toString());
//	    		listChild = service.getTreeData(agentid , "kickback" ,request);
//	    		treeNode.setChildren(listChild);
//	    		JSONArray json = JSONArray.fromObject(treeNode);
//    		    response.setCharacterEncoding("utf-8");
//				response.getWriter().write(json.toString());
//				response.getWriter().flush(); 
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//	    }
	 
		
		@ResponseBody
		@RequestMapping("/card_query/{agentId}")
		public List<UnicomInfoVo> queryCard(@PathVariable("agentId") Integer agentId, HttpServletResponse response, 
				HttpServletRequest request  ,HttpSession session , QueryData qo) {
			String pageNo = request.getParameter("pageNo");
			String pageSize = request.getParameter("pageSize");
			//System.out.println(userName);
			Grid grid = new Grid();
			Pagination page =  new Pagination(pageNo, pageSize , 100) ;
		    CodeUtil.initPagination(page);
		    List<UnicomInfoVo>  list = cardAgentService.queryCardInfo(agentId , page , qo);
		    return list;
		}
		
		@ResponseBody
		@RequestMapping("/kickback_query/{agentId}")
		public List<Map<String,String>>   queryKickback(@PathVariable("agentId") Integer agentId, HttpServletResponse response, 
				HttpServletRequest request  ,HttpSession session , QueryData qo ) {
			String pageNo = request.getParameter("pageNo");
			String pageSize = request.getParameter("pageSize");
			//System.out.println(userName);
			Grid grid = new Grid();
			Pagination page =  new Pagination(pageNo, pageSize , 100) ;
		    CodeUtil.initPagination(page);
		    List<Map<String,String>>  list = cardAgentService.queryKickbackInfo(agentId, qo  , page , qo.getTimeType());
		    Map<String , Double > map = cardAgentService.queryKickbackTotal(agentId , qo , qo.getTimeType());
		    return list;
		}
		
}
