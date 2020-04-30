package com.common;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cmoit.model.CmoitCard;

public class CMOIT_API_Util {
	public static String token = "";
	private static String appid = "QIQNEFAB7K1QDX";
	private static String password = "9jYT2le2T";
	
	/**CMIOT_API23S00  卡基本信息
	 * msisdn  transid  token 
	 * 
	 */
	private static String URL_CARD_INFO="https://api.iot.10086.cn/v2/cardinfo";
	private static String EBID_CARD_INFO = "0001000000010";
	
	/**CMIOT_API23U03  卡剩余流量查询
	 *  transid msisdn  token
	 * 
	 */
	private static String  URL_CARD_GPRS_USED ="https://api.iot.10086.cn/v2/gprsrealtimeinfo";
	private static String  EBID_CARD_GPRS_USED = "0001000000083";
	/**CMIOT_API23U07 单卡本月套餐内流量使用量实时查询  服务地址
	 *  transid msisdn  token
	 */
	private static String URL_CARD_PACKAGE_BATCH = "https://api.iot.10086.cn/v2/batchcurrentmonthgprsinfo";
	private static String EBID_CARD_PACKAGE_BATCH = "0001000024903";
	
	public static void main(String[] args) {
		try {
			getReturnData("http://iot.iot10.cn/mlb/order?iccid=999");			  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	/**
	 *  
	 * @return 物联网查询基本信息的url
	 * @throws Exception 
	 */
	public static CmoitCard  queryCmoitDataInfo(CmoitCard card) throws Exception{
		String gprsUrl = getURLByMSISDNS(card.getMsisdn() ,  URL_CARD_PACKAGE_BATCH , EBID_CARD_PACKAGE_BATCH );
		JSONObject  gprsJson =   JSONObject.fromObject(getReturnData(gprsUrl));
		if("0".equals(gprsJson.get("status"))){
			JSONArray  resultArrs = (JSONArray)gprsJson.get("result");
				JSONObject result = resultArrs.getJSONObject(0);
				System.out.println(result.get("gprsTotal"));
				card.setGprssum((result.getDouble("gprsTotal")) + "");
				card.setGprsused(1000+"");
		}else{
			System.out.println(gprsJson.toString());
			
		}
		return card;
	}
	
	public static String  getURLByMSISDNS(String msisdn , String baseUrl , String ebid) throws Exception{
		String transid =  getTransid(msisdn.substring(5, 13)) ;
		String token = getSHA256( appid+password+transid );
		System.out.println("token:" + token);
		return  baseUrl+  "?appid="+appid+"&transid="+transid+"&ebid="+ebid+"&token=" +  token + "&msisdns=" +  msisdn ;
	}
	
	public static String  getURLByMSISDN(String msisdn , String baseUrl , String ebid) throws Exception{
		String transid =  getTransid(msisdn.substring(5, 13)) ;
		String token = getSHA256( appid+password+transid );
		System.out.println("token:" + token);
		return  baseUrl+  "?appid="+appid+"&transid="+transid+"&ebid="+ebid+"&token=" +  token + "&msisdn=" +  msisdn ;
	}
	
	public static String  getCardInfoByMSISDN(String msisdn , String baseUrl , String ebid) throws Exception{
		String transid =  getTransid(msisdn.substring(5, 13)) ;
		String token = getSHA256( appid+password+transid );
		System.out.println("token:" + token);
		return  baseUrl+  "?appid="+appid+"&transid="+transid+"&ebid="+ebid+"&token=" +  token + "&card_info=" +  msisdn  +"&type=0";
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
    
    
    public static String getToken(String transid) throws Exception{
		String  urlString = "https://api.iot.10086.cn/v5/ec/get/token"
				+ "?appid="+appid+"&password="+password+"&transid="+transid;
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
    
		
		
		
		
		 /**
		    * 利用java原生的类实现SHA256加密
		    * @param str 加密后的报文
		    * @return
		    */
		    public static String getSHA256(String str){
		    	System.out.println(str);
		      MessageDigest messageDigest;
		     String encodestr = "";
		     try {
		      messageDigest = MessageDigest.getInstance("SHA-256");
		      messageDigest.update(str.getBytes("UTF-8"));
		      encodestr = byte2Hex(messageDigest.digest());
		     } catch (NoSuchAlgorithmException e) {
		      e.printStackTrace();
		     } catch (UnsupportedEncodingException e) {
		      e.printStackTrace();
		     }
		     return encodestr;
		    }
		    /**
		    * 将byte转为16进制
		    * @param bytes
		    * @return
		    */
		    private static String byte2Hex(byte[] bytes){
		     StringBuffer stringBuffer = new StringBuffer();
		     String temp = null;
		     for (int i=0;i<bytes.length;i++){
		      temp = Integer.toHexString(bytes[i] & 0xFF);
		      if (temp.length()==1){
		      //1得到一位的进行补0操作
		      stringBuffer.append("0");
		      }
		      stringBuffer.append(temp);
		     }
		     return stringBuffer.toString();
		    }
		
}
