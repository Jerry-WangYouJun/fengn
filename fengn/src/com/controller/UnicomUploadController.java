package com.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.common.ContextString;
import com.common.DateUtils;
import com.model.UnicomHistory;
import com.service.UnicomUploadService;

@Controller
@RequestMapping("/unicomUpload")
public class UnicomUploadController {
	
	@Autowired
	private UnicomUploadService dataServices;
	
	@RequestMapping(value = "uploadUnicomInit", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String  uploadUnicomInit(HttpSession session ){
		String roleId =  session.getAttribute("roleid").toString();
		if(!(ContextString.ROLE_ADMIN_UNICOM.equals(roleId) || ContextString.ROLE_ADMIN.equals(roleId) )){
			   return "unicom/agent";
		}
		 return "unicom/main";
	}
	

	@ResponseBody
	@RequestMapping(value = "/uploadExcelUnicom", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void uploadExcelUnicom(HttpServletRequest request,
			HttpServletResponse response , String act, HttpSession session) throws Exception {
		//MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		response.setCharacterEncoding("utf-8");
		Long startTime = System.currentTimeMillis(); 
		PrintWriter out =  response.getWriter();
		System.out.println("导入表数据开始：" + DateUtils.formatDate("MM-dd:HH-mm-ss"));
		//List<List<Object>> listob = dataServices.getDataList(multipartRequest, response);
		String agentId = session.getAttribute("agentId").toString();
		String apiCode = request.getParameter("apiCode");
		String tableName = "";
		switch (apiCode) {
		case "1":
			tableName="cmcc";
			break;
		case "2":
			tableName ="unicom";
			break;
		default:
			break;
		}
		//if(listob != null ){
			System.out.println("读取xls数据用时：" + (System.currentTimeMillis() - startTime));
			String msg = "";
			dataServices.deleteDataTemp( "mlb_" + tableName + "_card_copy");
			if("unicom".equals(tableName)) {
				msg = dataServices.insertUnicomList(null ,agentId , tableName);
			}else {
				System.out.println("开始获取数据" + DateUtils.formatDate("yyyyMMddHHmmss"));
				msg = dataServices.insertCmccList(null, agentId, tableName);
				System.out.println("插入数据表,用时" + (System.currentTimeMillis() - startTime));
			}
			out.print(msg);
		//}
		out.flush();
		out.close();
	}
}
