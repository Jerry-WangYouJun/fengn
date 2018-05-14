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
	@RequestMapping(value = "uploadExcelUnicom", method = { RequestMethod.GET,
			RequestMethod.POST })
	public void uploadExcelUnicom(HttpServletRequest request,
			HttpServletResponse response , String act, HttpSession session) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		response.setCharacterEncoding("utf-8");
		Long startTime = System.currentTimeMillis(); 
		PrintWriter out =  response.getWriter();
		System.out.println("导入表数据开始：" + DateUtils.formatDate("MM-dd:HH-mm-ss"));
		List<List<Object>> listob = dataServices.getDataList(multipartRequest, response);
		String agentId = session.getAttribute("agentId").toString();
		if(listob != null ){
			System.out.println("读取xls数据用时：" + (System.currentTimeMillis() - startTime));
			String msg = "";
			dataServices.deleteDataTemp("u_cmtp_temp");
			System.out.println("删除临时表,用时" + (System.currentTimeMillis() - startTime));
			if("insert".equals(act)) {
				msg = dataServices.insertUnicomList(listob ,agentId);
			}else if("update".equals(act)) {
				msg = dataServices.updateUnicomList(listob );
			}
			List<UnicomHistory>  historyList =  new ArrayList<>();
			for(int i = 0 ; i < listob.size() ; i ++){//1 iccid  10 money 11.updatetime
				try {
					String money  =  listob.get(i).get(9).toString();
					if(StringUtils.isNotEmpty(money) && Double.valueOf(money) > 0){
						UnicomHistory history =   new UnicomHistory();
						history.setIccid(listob.get(i).get(0).toString());
						history.setMoney(Double.valueOf(money));
						history.setUpdateDate(listob.get(i).get(10).toString());
						historyList.add(history);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			if(historyList != null && historyList.size() > 0) {
				
				dataServices.insertHistoryList(historyList);
			}
			out.print(msg);
		}
		out.flush();
		out.close();
	}
}
