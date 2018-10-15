package com.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.DateUtils;
import com.dao.UnicomUploadDao;
import com.service.UnicomUploadService;

@Controller
@RequestMapping("/unicomUpload")
public class UnicomUploadController {
	
	@Autowired
	UnicomUploadDao uploadDao;
	
	@Autowired
	private UnicomUploadService dataServices;

	@ResponseBody
	@RequestMapping(value = "/uploadExcelUnicom", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void uploadExcelUnicom(HttpServletRequest request,
			HttpServletResponse response ,  HttpSession session, String apiCode, String createdate) throws Exception {
		//MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		response.setCharacterEncoding("utf-8");
		Long startTime = System.currentTimeMillis(); 
		PrintWriter out =  response.getWriter();
		System.out.println("导入表数据开始：" + DateUtils.formatDate("MM-dd:HH-mm-ss"));
		//List<List<Object>> listob = dataServices.getDataList(multipartRequest, response);
		String agentId = session.getAttribute("agentId").toString();
		String tableName = "";
		String msg = "";
		System.out.println("开始获取数据" + DateUtils.formatDate("yyyyMMddHHmmss"));
		switch (apiCode) {
		case "1":
			tableName="cmcc";
			dataServices.deleteDataTemp( "mlb_" + tableName + "_card_copy");
			msg = dataServices.insertCmccTemp("insert"  , createdate, tableName);
			if(msg == null){
				msg = "当天无出库信息！";
			}
			uploadDao.insertAgentCard(agentId , tableName );
			break;
		case "2":
			tableName ="unicom";
			dataServices.deleteDataTemp("mlb_" + tableName + "_card_copy");
			msg = dataServices.insertUnicomTemp("insert"  , tableName , createdate);
			if(msg == null){
				msg = "当天无出库信息！";
			}
			uploadDao.insertAgentCard(agentId , tableName );
			break;
		default:
			msg="数据错误，请重试或联系管理员！";
			break;
		}
		System.out.println("插入数据表,用时" + (System.currentTimeMillis() - startTime));
		JSONObject json = new JSONObject();
		json.put("msg", msg);
		out.print(msg);
		out.flush();
		out.close();
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/cmcc_update", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void updateCmccData(HttpServletResponse response ,  HttpSession session) throws Exception {
		//MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		response.setCharacterEncoding("utf-8");
		Long startTime = System.currentTimeMillis(); 
		PrintWriter out =  response.getWriter();
		System.out.println("导入表数据开始：" + DateUtils.formatDate("MM-dd:HH-mm-ss"));
		//List<List<Object>> listob = dataServices.getDataList(multipartRequest, response);
		String msg = "";
		System.out.println("开始获取数据" + DateUtils.formatDate("yyyyMMddHHmmss"));
			dataServices.deleteDataTemp("mlb_cmcc_card_copy");
			msg =  dataServices.insertCmccTemp("update" , "1" , "cmcc");
		System.out.println("插入数据表,用时" + (System.currentTimeMillis() - startTime));
		JSONObject json = new JSONObject();
		json.put("msg", msg);
		out.print(json);
		out.flush();
		out.close();
	}
	
	@ResponseBody
	@RequestMapping(value = "/unicom_update", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void updateUnicomData(HttpServletResponse response ,  HttpSession session) throws Exception {
		//MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		response.setCharacterEncoding("utf-8");
		Long startTime = System.currentTimeMillis(); 
		PrintWriter out =  response.getWriter();
		System.out.println("导入表数据开始：" + DateUtils.formatDate("MM-dd:HH-mm-ss"));
		//List<List<Object>> listob = dataServices.getDataList(multipartRequest, response);
		String msg = "";
		System.out.println("开始获取数据" + DateUtils.formatDate("yyyyMMddHHmmss"));
			dataServices.deleteDataTemp("mlb_unicom_card_copy");
			msg =  dataServices.insertUnicomTemp("update" , "unicom" , "3");
		System.out.println("插入数据表,用时" + (System.currentTimeMillis() - startTime));
		JSONObject json = new JSONObject();
		json.put("msg", msg);
		out.print(json);
		out.flush();
		out.close();
	}
	
}
