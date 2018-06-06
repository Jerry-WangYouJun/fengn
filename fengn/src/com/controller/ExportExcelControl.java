package com.controller;

import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.common.ExportExcelUtil;
import com.model.InfoVo;
 

/**
 * 模板文件下载，废弃
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/ExportExcel/*")  
public class ExportExcelControl {
	
	@RequestMapping("hello")
	public String getUpoadPage(){
		return "hello";
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="ajaxExport.do",method={RequestMethod.GET,RequestMethod.POST})
	public  String  ajaxUploadExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		OutputStream os = null;  
		Workbook wb = null;    
		
		try {
			List<InfoVo> lo = new ArrayList<InfoVo>();
			ExportExcelUtil util = new ExportExcelUtil();
			File file =util.getExcelDemoFile("/ExcelDemoFile/移动充值卡.xlsx");
			String sheetName="sheet1";  
			wb = util.writeNewExcel(file, sheetName,lo); 
			
			String fileName="移动充值卡.xlsx";
		    response.setContentType("application/vnd.ms-excel");
		    response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName, "utf-8"));
		    os = response.getOutputStream();
			wb.write(os);  
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			os.flush();
			os.close();
			wb.close();
		} 
		return null;
	}


}
