package com.common;

import com.model.InfoVo;

public class WlkAPIUtil {
	  
	
	public static String getUrl(InfoVo infoVo,  String ebid  ){
		String baseUrl = "http://www.057110086.cn/api/WlkAPI.ashx?"
				+ "msisdn=" + infoVo.getCardCode().trim() + "&transid=" + infoVo.getICCID() + "&" 
				+ "ebid=" + ebid + "&appid=20170320213458465&token=20170320213458465";
		//System.out.println(baseUrl);
		return baseUrl;
	}
	
	
}
