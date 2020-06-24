package com.cmoit.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.cmoit.model.CmoitCard;
import com.cmoit.service.CMoitCardAgentService;
import com.common.CMOIT_API_Util;
import com.common.ContextString;
import com.common.DateUtils;
import com.dao.PackageMapper;
import com.model.InfoVo;
import com.model.Packages;
import com.model.Pagination;
import com.model.QueryData;
import com.service.CardInfoService;
import com.service.DataMoveService;


@Controller
@RequestMapping("/cmoit/info")
public class CMoitCardInfoController {
	  
	    @Autowired
	    CardInfoService service;
	    @Autowired
	    CMoitCardAgentService  ccaService;
	    
	    @Autowired
	    PackageMapper  pacDao;
	    
	    @Autowired
		@Qualifier("dataMoveService")
		private DataMoveService moveDataServices;
	    
	    
	    
	    @RequestMapping("/c")
	    public ModelAndView getCmccCardInfo(String iccid){
	    	ModelAndView mv = new ModelAndView("cmoit/cardInfo_pay_first");
			try {
				QueryData qo = new QueryData();
				qo.setIccid(iccid);
				 List<CmoitCard> list = ccaService.queryCardInfo(1, new Pagination(), qo , "cmoit");
				 CmoitCard card =  new CmoitCard();
				 if(list != null && list.size() > 0 ){
					 card	= list.get(0);
					 CMOIT_API_Util.queryCmoitDataInfo(card);
					 mv.addObject("apitype", "cmoit");
					 String tel = ccaService.queryTelByICCID( iccid , "cmoit");
				    	mv.addObject("tel", tel);
				 }else{
					 card = ccaService.getResultCmccFromMlb(CMOIT_API_Util.doPost(ContextString.MLB_URL_NEW_QUERY, iccid));
					 if(card == null) {
						 throw new Exception("卡号错误，请重新输入或联系管理员");
					 }
					 String pacid = ccaService.queryPacIdByICCID(card.getIccid(), "cmcc");
					 Packages  pac =pacDao.selectByPrimaryKey(Integer.valueOf(pacid));
					 card.setDiscrip(pac.getDiscrip());
					 mv.addObject("apitype", "cmcc");
					 String tel = ccaService.queryTelByICCID(card.getIccid() , "cmcc");
				    	mv.addObject("tel", tel);
				 }
				 mv.addObject("cmoitInfo", card);
		    	
			} catch (Exception e) {
				InfoVo   wrongInfo = new InfoVo();
				wrongInfo.setUserStatus("卡号错误，请重新输入或联系管理员");
				mv.addObject("info", wrongInfo);
			}
	    	return mv ;
	    	
	    }
	    
	    @RequestMapping("/pay_second")
	    public ModelAndView pay_second(@RequestParam("iccid") String iccid , String apitype, HttpServletRequest request) throws Exception{
	    	if(iccid==null){
	    		iccid = request.getParameter("iccid");
	    	}
	    	QueryData qo = new QueryData();
			qo.setIccid(iccid);
			 List<CmoitCard> list = ccaService.queryCardInfo(1, new Pagination(), qo , apitype);
			 CmoitCard card =  list.get(0);
			 Packages  pac = pacDao.selectByPrimaryKey(card.getPacid());
	    	ModelAndView mv = new ModelAndView("cmoit/cardInfo_pay_second");
	    	mv.addObject("iccid", iccid);
	    	mv.addObject("pac",pac);
	    	return mv;
	    }
	    
	    @RequestMapping("/xinfu_wechat_pay")
	    public ModelAndView getPay(@RequestParam("iccid") String iccid , String apitype, HttpServletRequest request) throws Exception{
	    	if(iccid==null){
	    		iccid = request.getParameter("iccid");
	    	}
	    	QueryData qo = new QueryData();
			qo.setIccid(iccid);
			 List<CmoitCard> list = ccaService.queryCardInfo(1, new Pagination(), qo , apitype);
			 CmoitCard card =  list.get(0);
			 Packages  pac = pacDao.selectByPrimaryKey(card.getPacid());
	    	ModelAndView mv = new ModelAndView("xfpay");
	    	mv.addObject("iccid", iccid);
	    	mv.addObject("pac",pac);
	    	return mv;
	    }
	    
	    
	    @ResponseBody
		@RequestMapping(value = "ajaxUpload", method = { RequestMethod.GET,
				RequestMethod.POST })
		public void ajaxUploadExcel(HttpServletRequest request,
				HttpServletResponse response , String apiCode  , String pacId) throws Exception {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			response.setCharacterEncoding("utf-8");
			PrintWriter out =  response.getWriter();
			System.out.println("导入表数据开始：" + DateUtils.formatDate("MM-dd:HH-mm-ss"));
			List<List<Object>> listob = moveDataServices.getDataList(multipartRequest, response);
			System.out.println();
			String iccid =  listob.get(0).get("cmcc".equals(apiCode)?0:8).toString();
			int testIccid = moveDataServices.queryIccidForUpload(apiCode, iccid);
			if(testIccid > 0 ) {
				out.print("存才重复卡号，请检查是否重复导入");
				out.flush();
			}else {
				int  result  = moveDataServices.uploadData(listob , apiCode , pacId);
				out.print("新增数据" + result  + "条");
				out.flush();
				out.close();
			}
		}
	    
}
