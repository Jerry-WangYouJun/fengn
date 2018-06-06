package com.controller;

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
	 
}
