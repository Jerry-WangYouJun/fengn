package com.controller;

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

import com.common.CodeUtil;
import com.model.Grid;
import com.model.InfoVo;
import com.model.Pagination;
import com.model.QueryData;
import com.model.TreeNode;
import com.service.AgentService;
import com.service.CardAgentService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/treeindex")
public class TreeController {

	@Autowired
	AgentService service;

	@Autowired
	CardAgentService cardAgentService;

	@RequestMapping("/{group}/card")
	public void getTreeData(@PathVariable("group") String groupType, HttpSession session, HttpServletResponse response, HttpServletRequest request ) {
			TreeNode treeNode = new TreeNode();
			treeNode.setText("丰宁/永思SIM卡管理");
			getTreeDataBytype(session, response, request, "card",groupType , treeNode);
	}
	
	@RequestMapping("/{group}/unicom_card")
	public void getUnicomTreeData(@PathVariable("group") String groupType,HttpSession session, HttpServletResponse response, HttpServletRequest request) {
			TreeNode treeNode = new TreeNode();
			treeNode.setText("联通SIM卡管理");
			getTreeDataBytype(session, response, request, "unicom_card",groupType , treeNode);
	}
	@RequestMapping("/{group}/cmcc_card")
	public void getCmccTreeData(@PathVariable("group") String groupType,HttpSession session, HttpServletResponse response, HttpServletRequest request) {
			TreeNode treeNode = new TreeNode();
			treeNode.setText("移动SIM卡管理");
			getTreeDataBytype(session, response, request, "cmcc_card",groupType , treeNode);
	}
	
	@RequestMapping("/{group}/{pageName}")
	public void getCmoitTreeData(@PathVariable("group") String groupType,@PathVariable("pageName") String pageName , HttpSession session, HttpServletResponse response, HttpServletRequest request) {
			TreeNode treeNode = new TreeNode();
			switch (pageName) {
			case "cmoit_card":
				treeNode.setText("移动SIM卡管理");
				break;
			case "cmoit_kickback":
				treeNode.setText("移动S返佣管理");
				break;
			default:
				treeNode.setText("显示错误！");
				break;
			}
			getTreeDataBytype(session, response, request, pageName,groupType , treeNode);
	}

	@RequestMapping("/{group}/kickback")
	public void getKickbackData(@PathVariable("group") String groupType,HttpSession session, HttpServletResponse response, HttpServletRequest request) {
		TreeNode treeNode = new TreeNode();
		treeNode.setText("丰宁/永思返佣管理");
		getTreeDataBytype(session, response, request, "kickback",groupType , treeNode);
	}
	
	@RequestMapping("/{group}/unicom_kickback")
	public void getUnicomKickbackData(@PathVariable("group") String groupType,HttpSession session, HttpServletResponse response, HttpServletRequest request) {
		TreeNode treeNode = new TreeNode();
		treeNode.setText("联通返佣管理");
		getTreeDataBytype(session, response, request, "unicom_kickback",groupType , treeNode);
	}
	@RequestMapping("/{group}/cmcc_kickback")
	public void getCmccKickbackData(@PathVariable("group") String groupType,HttpSession session, HttpServletResponse response, HttpServletRequest request) {
		TreeNode treeNode = new TreeNode();
		treeNode.setText("移动返佣管理");
		getTreeDataBytype(session, response, request, "cmcc_kickback",groupType , treeNode);
	}

	@RequestMapping("/{group}/all_kickback")
	public void getAllKickbackData(@PathVariable("group") String groupType,HttpSession session, HttpServletResponse response, HttpServletRequest request) {
		TreeNode treeNode = new TreeNode();
		treeNode.setText("返佣管理");
		getTreeDataBytype(session, response, request, "all_kickback",groupType , treeNode);
	}

	public void getTreeDataBytype(HttpSession session, HttpServletResponse response, HttpServletRequest request 
			, String urlType ,String groupType , TreeNode treeNode) {
		try {
			List<TreeNode> list = new ArrayList<>();
			list.add(treeNode);
			// 子节点
			List<TreeNode> listChild = new ArrayList<>();
			Integer agentid = Integer.valueOf(session.getAttribute("agentId").toString());
			listChild = service.getTreeData(agentid, urlType , groupType ,  request);
			treeNode.setChildren(listChild);
			JSONArray json = JSONArray.fromObject(treeNode);
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(json.toString());
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 丰宁、永思接口数据查询
	 * @param agentId
	 * @param response
	 * @param request
	 * @param session
	 * @param qo
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/card_query/{agentId}")
	public Grid  queryCard(@PathVariable("agentId") Integer agentId, HttpServletResponse response,
			HttpServletRequest request, HttpSession session, QueryData qo) {
		Grid grid = new Grid();
 		String pageNo = request.getParameter("pageNumber");
		String pageSize = request.getParameter("pageSize");
		// System.out.println(userName);
		Pagination page = new Pagination(pageNo, pageSize, 100);
		CodeUtil.initPagination(page);
		List<InfoVo> list = cardAgentService.queryCardInfo(agentId, page, qo);
		Long total = (long)cardAgentService.queryCardTotal(agentId, qo);
		grid.setRows(list);
		grid.setTotal(total);
		return grid;
	}

	@ResponseBody
	@RequestMapping("/kickback_query/{agentId}")
	public Grid queryKickback(@PathVariable("agentId") Integer agentId, HttpServletResponse response,
			HttpServletRequest request, HttpSession session, QueryData qo) {
		String pageNo = request.getParameter("pageNumber");
		String pageSize = request.getParameter("pageSize");
		// System.out.println(userName);
		Grid grid = new Grid();
		Pagination page = new Pagination(pageNo, pageSize, 100);
		CodeUtil.initPagination(page);
		List<Map<String, String>> list = cardAgentService.queryKickbackInfo(agentId, qo, page, qo.getTimeType());
		Map<String, Double> map = cardAgentService.queryKickbackTotal(agentId, qo, qo.getTimeType());
		grid.setTotal(map.get("total").longValue());
		grid.setRows(list);
		return grid;
	}

	@RequestMapping("/kickback_sum/{agentId}")
	public void queryKickbackSum(@PathVariable("agentId") Integer agentId, HttpServletResponse response,
			HttpServletRequest request, HttpSession session, QueryData qo) {
		String pageNo = request.getParameter("pageNumber");
		String pageSize = request.getParameter("pageSize");
		// System.out.println(userName);
		Pagination page = new Pagination(pageNo, pageSize, 100);
		CodeUtil.initPagination(page);
		Map<String, Double> map = cardAgentService.queryKickbackTotal(agentId, qo, qo.getTimeType());
		PrintWriter out;
		try {
			response.setContentType("text/html;charset=UTF-8");
			out = response.getWriter();
			out.println(map.get("sumKick"));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/updateRemark/{type}")
	public void  updateInit(@PathVariable("type") String table , HttpServletResponse response,
			String iccid , String remark){
		response.setContentType("text/text;charset=UTF-8");
		PrintWriter out;
		try {
			cardAgentService.updateRemarks(iccid,remark , table );
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
