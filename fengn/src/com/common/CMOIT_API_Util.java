package com.common;

import java.io.UnsupportedEncodingException;
import java.net.URL;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CMOIT_API_Util {
	public static String token = "";
	private static String appid = "QIQNEFAB7K1QDX";
	private static String password = "9jYT2le2T";
	
	/**
	 *  
	 * @return 物联网查询基本信息的url
	 */
	public static String getQueryUnicomUrl(){
		String url = "https://www.m-m10086.com/api/SimListFire/Search";
		return url;
	}
	
	/**
	 * 执行物联网新增续费的url获取 ， post类型的请求
	 * @param urlString
	 * @return
	 * @throws UnsupportedEncodingException
	 */
    public static String getReturnData(String urlString) throws UnsupportedEncodingException {  
        String res = "";   
        try {   
            URL url = new URL(urlString);  
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection)url.openConnection();  
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream(),"UTF-8"));  
            String line;  
            while ((line = in.readLine()) != null) {  
                res += line;  
            }  
            in.close();  
        } catch (Exception e) {  
            System.out.println("error in wapaction,and e is " + e.getMessage());  
        }  
        	//System.out.println(res);  	
        return res;  
    }  
    
    public static String getTransid(String seqid){
    	return appid + DateUtils.formatDate("yyyyMMddHHmmss")+ seqid;
    }
    
    
    public static String getToken() throws Exception{
		String  urlString = "https://api.iot.10086.cn/v5/ec/get/token"
				+ "?appid="+appid+"&password="+password+"&transid="+ getTransid("00000001");
		String msg = getReturnData(urlString);
		JSONObject  json  = JSONObject.fromObject(msg);  
		String  token= null;
		if("正确".equals(json.get("message"))){
			JSONArray  arr = (JSONArray)json.get("result");
			JSONObject s =  JSONObject.fromObject( arr.getString(0)); 
		    token = s.getString("token");
		}
		return token;
    }   
    
		
		public static void main(String[] args) {
			try {
				
				  getReturnData("http://223.99.141.141:10210/sdiotss/subs/queryBalances2?susinsphome=1440385364000");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
}
