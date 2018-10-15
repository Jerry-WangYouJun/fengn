package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.common.ContextString;
import com.common.ResponseURLDataUtil;
import com.dao.MlbCmccCardMapper;
import com.dao.MlbUnicomCardMapper;
import com.model.InfoVo;
import com.model.MlbCmccCard;
import com.model.MlbUnicomCard;
import com.model.UnicomInfoVo;
import com.pay.util.JsSignUtil;
import com.service.CardInfoService;
import com.service.UnicomUploadService;


@Controller
public class UnicomCardInfoController {
	  
	    @Autowired
	    CardInfoService service;
	    
	    @Autowired
	    UnicomUploadService  updateService;
	    
	    @Autowired
	    MlbCmccCardMapper cmccDao ; 
	    
	    @Autowired
	    MlbUnicomCardMapper unicomDao ;
	    
	    @RequestMapping("/c")
	    public ModelAndView getCmccCardInfo(String iccid){
	    	ModelAndView mv = new ModelAndView("pages/cardInfo");
			try {
				MlbCmccCard  cardInfo = cmccDao.selectByIccid(iccid);
		    	if(cardInfo != null ){
		    		 if(StringUtils.isEmpty(cardInfo.getSim())){//有卡信息 未更新
		    			 Map map = new HashMap();
	    				 map.put("simIds",cardInfo.getSimid() );
	    				 JSONObject json = ResponseURLDataUtil.getMLBData(ResponseURLDataUtil.getToken(),ContextString.URL_CMCC_BIND , map);
	    				 List<MlbCmccCard> list =updateService.getResultCmccFromMlb(json);
	    				 if(list != null){
	    					 cmccDao.updateBatch(list);
	    					 if(list.size() == 1){
	    						 MlbCmccCard muc = list.get(0);
	    						 cardInfo.setActiveTime(muc.getActiveTime());
	    						 cardInfo.setPackagePeriodSrc(muc.getPackagePeriodSrc());
	    						 cardInfo.setPackagename(muc.getPackagename());
	    						 cardInfo.setHoldName(muc.getHoldName());
	    						 mv.addObject("cmccCard", cardInfo);
	    					 }else{
	    						 UnicomInfoVo   wrongInfo = new UnicomInfoVo();
	    						 wrongInfo.setGprsRest("卡号重复，请联系管理员确认");
	    						 mv.addObject("info", wrongInfo);
	    					 }
	    				 }
		    		 }else{
		    			 mv.addObject("cmccCard", cardInfo);
		    		 }
		    	}else{
		    		UnicomInfoVo   wrongInfo = new UnicomInfoVo();
		    		wrongInfo.setGprsRest("卡号不存在，请联系管理员确认");
		    		mv.addObject("info", wrongInfo);
		    	}
		    	String tel = service.queryTelByICCID(iccid , "cmcc");
		    	mv.addObject("tel", tel);
			} catch (Exception e) {
				InfoVo   wrongInfo = new InfoVo();
				wrongInfo.setUserStatus("系统错误，请联系管理员:" + e.getMessage());
				mv.addObject("info", wrongInfo);
			}
	    	return mv ;
	    	
	    }
	    
	    @RequestMapping("/u")
	    public ModelAndView getUnicomCardInfo(String iccid){
	    	ModelAndView mv = new ModelAndView("pages/cardInfo");
			try {
				MlbUnicomCard  cardInfo = unicomDao.selectByIccid(iccid);
		    	if(cardInfo!=null ){
		    		 if(StringUtils.isEmpty(cardInfo.getSim())){
		    			 Map map = new HashMap();
	    				 map.put("simIds",cardInfo.getSimid() );
	    				 JSONObject json = ResponseURLDataUtil.getMLBData(ResponseURLDataUtil.getToken(),ContextString.URL_UNICOM_BIND , map);
	    				 List<MlbUnicomCard> list =updateService.getResultUnicomFromMlb(json);
	    				 if(list != null){
	    					 unicomDao.updateBatch(list);
	    					 if(list.size() == 1){
	    						 MlbUnicomCard muc = list.get(0);
	    						 cardInfo.setSim(muc.getSim());
	    						 cardInfo.setOutWarehouseDate(muc.getOutWarehouseDate());
	    						 cardInfo.setFlowlefttime(muc.getFlowlefttime());
	    						 cardInfo.setImsi(muc.getImsi());
	    						 cardInfo.setLastactivetime(muc.getLastactivetime());
	    						 mv.addObject("unicomCard", cardInfo);
	    					 }else{
	    						 UnicomInfoVo   wrongInfo = new UnicomInfoVo();
	    						 wrongInfo.setGprsRest("卡号重复，请联系管理员确认");
	    						 mv.addObject("info", wrongInfo);
	    					 }
	    				 }
		    		 }else{
		    			 mv.addObject("unicomCard", cardInfo);
		    		 }
		    	}else{
		    		UnicomInfoVo   wrongInfo = new UnicomInfoVo();
		    		wrongInfo.setGprsRest("卡号不存在，请联系管理员确认");
		    		mv.addObject("info", wrongInfo);
		    	}
		    	String tel = service.queryTelByICCID(iccid , "unicom");
		    	mv.addObject("tel", tel);
			} catch (Exception e) {
				InfoVo   wrongInfo = new InfoVo();
				wrongInfo.setUserStatus("系统错误，请联系管理员:" + e.getMessage());
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
	    
	    @RequestMapping("/searchInit")
	    public ModelAndView getSearchInit(){
	    	ModelAndView mv = new ModelAndView("search");
	    	Map<String, String> ret = JsSignUtil.sign("http://www.pay-sf.com/card/searchInit");
	    	mv.addObject("ret", ret);
	    	return mv ;
	    }
	    
	    
	    @RequestMapping("/xinfu_wechat_pay")
	    public ModelAndView getPay(@RequestParam("iccid") String iccid , HttpServletRequest request){
	    	if(iccid==null){
	    		iccid = request.getParameter("iccid");
	    	}
	    	ModelAndView mv = new ModelAndView("xfpay");
	    	mv.addObject("iccid", iccid);
	    	return mv;
	    }
	    
}
