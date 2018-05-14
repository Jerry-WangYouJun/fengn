package com.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dao.CardInfoMapper;
import com.model.InfoVo;
import com.model.UnicomInfoVo;
import com.pay.util.JsSignUtil;
import com.service.CardInfoService;


@Controller
@RequestMapping("/c")
public class UnicomCardInfoController {
	  
	    @Autowired
	    CardInfoService service;
	    
	    @Autowired
	    CardInfoMapper cardInfoDao ; 
	    
	    
	    @RequestMapping("/q")
	    public ModelAndView getCardInfo(String iccid){
	    	ModelAndView mv = new ModelAndView("unicom/cardInfo");
			try {
				UnicomInfoVo  cardInfo = cardInfoDao.selectByIccid(iccid);
		    	if(cardInfo!=null ){
		    		if("1".equals(cardInfo.getOrderStatus())) {
		    			return new ModelAndView("redirect:http://open.m-m10010.com/Html/Terminal/"
		    					+ "simcard_lt_new.aspx?simNo="+iccid+"&apptype=null"
		    					+ "&wechatId=oyVv8s1UDHqZ9BtLIYJsD5P8QA9k&mchId=&accessname=null");
		    		}
		    		mv.addObject("info", cardInfo);
		    		
		    	}else{
		    		UnicomInfoVo   wrongInfo = new UnicomInfoVo();
		    		wrongInfo.setGprsRest("卡号错误，请联系管理员确认");
		    		mv.addObject("info", wrongInfo);
		    	}
			} catch (Exception e) {
				InfoVo   wrongInfo = new InfoVo();
				wrongInfo.setUserStatus("卡号错误:" + e.getMessage());
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
