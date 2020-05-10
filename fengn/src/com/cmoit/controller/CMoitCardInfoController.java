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
import com.dao.MlbCmccCardMapper;
import com.dao.MlbUnicomCardMapper;
import com.dao.PackageMapper;
import com.model.InfoVo;
import com.model.MlbCmccCard;
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
	    	ModelAndView mv = new ModelAndView("cmoit/cardInfo");
			try {
				QueryData qo = new QueryData();
				qo.setSimNum(iccid);
				 List<CmoitCard> list = ccaService.queryCardInfo(1, new Pagination(), qo , "cmoit");
				 CmoitCard card =  new CmoitCard();
				 if(list != null && list.size() > 0 && iccid.equals(list.get(0).getMsisdn())){
					 card	= list.get(0);
					 CMOIT_API_Util.queryCmoitDataInfo(card);
					 mv.addObject("apitype", "cmoit");
					 String tel = ccaService.queryTelByICCID(card.getIccid() , "cmoit");
				    	mv.addObject("tel", tel);
				 }else{
					 card = ccaService.getResultCmccFromMlb(CMOIT_API_Util.doPost(ContextString.URL_NEW_QUERY, iccid));
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
	    
	    
	    @RequestMapping("/search")
	    public ModelAndView searchHistory(String iccid){
	    	ModelAndView mv = new ModelAndView("history");
	    	InfoVo vo;
			try {
				vo = service.queryInfoByICCID(iccid);
				if(vo!=null){
					service.queryHistoryList(vo);
					mv.addObject("info", vo);
				}else{
					InfoVo   wrongInfo = new InfoVo();
					wrongInfo.setUserStatus("卡号信息异常");
					mv.addObject("info", wrongInfo);
				}
			} catch (Exception e) {
				InfoVo   wrongInfo = new InfoVo();
				wrongInfo.setUserStatus("卡号信息异常:" + e.getMessage());
				mv.addObject("info", wrongInfo);
			}
	    	return mv ;
	    	
	    }
	    
	    @RequestMapping("/xinfu_wechat_pay")
	    public ModelAndView getPay(@RequestParam("iccid") String iccid , String apitype, HttpServletRequest request){
	    	if(iccid==null){
	    		iccid = request.getParameter("iccid");
	    	}
	    	QueryData qo = new QueryData();
			qo.setSimNum(iccid);
			 List<CmoitCard> list = ccaService.queryCardInfo(1, new Pagination(), qo , "cmoit");
			 CmoitCard card =  list.get(0);
			 Packages  pac = pacDao.selectByPrimaryKey(card.getPacid());
	    	ModelAndView mv = new ModelAndView("xfpay");
	    	mv.addObject("iccid", card.getIccid());
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
			moveDataServices.uploadData(listob , apiCode , pacId);
			
//			int insertNum = moveDataServices.dataMoveSql2Oracle(apiCode);
//			System.out.println("执行结束            ：" + System.currentTimeMillis());
			out.print("新增数据" + listob.size()  + "条");
			out.flush();
			out.close();
		}
	    
}
