package com.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.cmoit.model.CmoitCard;
import com.pay.util.MD5Util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class CMOIT_API_Util {
	public static String token = "";

	
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
	
	static public final String 
	 URL_CMCC_BIND_NEW ="http://iot.iot1860.com/api/YDSimListFire/Binding";
	
		static public final String 
		URL_CMCC_QUERY_NEW ="http://iot.iot1860.com/api/YDSimListFire/Search";
//	
//	public static void main(String[] args) {
//		try {
//			getReturnData("http://iot.iot10.cn/mlb/order?iccid=999");			  
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	
	
	
	
	
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
		String token = getSHA256( ContextString.CMOIT_APPID+ContextString.CMOIT_PASSWORD+transid );
		System.out.println("token:" + token);
		return  baseUrl+  "?appid="+ContextString.CMOIT_APPID+"&transid="+transid+"&ebid="+ebid+"&token=" +  token + "&msisdns=" +  msisdn ;
	}
	
	public static String  getURLByMSISDN(String msisdn , String baseUrl , String ebid) throws Exception{
		String transid =  getTransid(msisdn.substring(5, 13)) ;
		String token = getSHA256( ContextString.CMOIT_APPID+ContextString.CMOIT_PASSWORD+transid );
		System.out.println("token:" + token);
		return  baseUrl+  "?appid="+ContextString.CMOIT_APPID+"&transid="+transid+"&ebid="+ebid+"&token=" +  token + "&msisdn=" +  msisdn ;
	}
	
	public static String  getCardInfoByMSISDN(String msisdn , String baseUrl , String ebid) throws Exception{
		String transid =  getTransid(msisdn.substring(5, 13)) ;
		String token = getSHA256( ContextString.CMOIT_APPID+ContextString.CMOIT_PASSWORD+transid );
		System.out.println("token:" + token);
		return  baseUrl+  "?appid="+ContextString.CMOIT_APPID+"&transid="+transid+"&ebid="+ebid+"&token=" +  token + "&card_info=" +  msisdn  +"&type=0";
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
    	return ContextString.CMOIT_APPID + DateUtils.formatDate("yyyyMMddHHmmss")+ seqid;
    }
    
    
    public static String getToken(String transid) throws Exception{
		String  urlString = "https://api.iot.10086.cn/v5/ec/get/token"
				+ "?appid="+ContextString.CMOIT_APPID+"&password="+ContextString.CMOIT_PASSWORD+"&transid="+transid;
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
		    
		    
		    
		    public static void main(String[] args) throws Exception {
				//	updateRenewData(1000 , "2019-01-01" , "2018-01-01");
//					getReturnData("http://iot.iot10.cn/mlb/order?iccid=999");
//					Map map = new HashMap();
//					 map.put("batchCardStr", "1440381500241");
//					 String token = getToken("fnmyyd","8989123");
//					 System.out.println(token);
//					 
//					 JSONObject json = getMLBData(URL_CMCC_QUERY_NEW  ,token ,map);
//					 System.out.println(json);
		    	//String  url = "http://open.iot1860.com/open/Help/CheckTerminal";
		    	//String url ="http://open.iot1860.com/open/cmcc/RenewalsPackageList";
		    	String url ="http://open.iot1860.com/open/cmcc/TerminalDetail";
				 long timeStampSec = System.currentTimeMillis()/1000;
			     String timestamp = String.format("%010d", timeStampSec);
			     String id = "131416124";
			     String key ="DF459BBED5D2E9B024B7AC6C5D80DC5B";
			     String str = id+key+timestamp;
				 String sign = MD5Util.md5(str);
				 System.out.println("字符串"+str);
				 System.out.println("sign:" + sign);
				 Map map = new HashMap();
				 map.put("num", "1440381500241");
				 map.put("num_type", "sim");
				 map.put("userId", id);
				 map.put("timestamp",timestamp);
				 map.put("sign", sign.toUpperCase());
				// JSONObject  json = JSONObject.fromObject(map);
		    	doPost(url, "1440381500241");
		    	//System.out.println("result" + result);
				}
		    
		    
		    
		    /**
			 * @description:使用httpClient对象执行 post 请求
			 * @param: uri 需要跨域请求的uri , formDataMap  模拟表单需要提交数据 （name - value 形式）
			 * @author:wu
			 * @createDate:2018年2月28日 下午4:36:55
			 */
			public static JSONObject doPost(String uri, String iccid) throws ClientProtocolException, IOException{
				 long timeStampSec = System.currentTimeMillis()/1000;
			     String timestamp = String.format("%010d", timeStampSec);
			     String id = ContextString.MLB_USER_ID ;
			     String key = ContextString.MLB_KEY;
			     String str = id+key+timestamp;
				 String sign = MD5Util.md5(str);
				 System.out.println("字符串"+str);
				 System.out.println("sign:" + sign);
				 Map map = new HashMap();
				 map.put("num", iccid);
				 map.put("num_type", "iccid");
				 map.put("userId", id);
				 map.put("timestamp",timestamp);
				 map.put("sign", sign.toUpperCase());
				// 1、创建httpClient 对象
				CloseableHttpClient httpClient = HttpClients.createDefault();
				// 2、 创建post 对象
				HttpPost post =new HttpPost(uri);
				StringEntity entitys = new StringEntity(JSONObject.fromObject(map).toString(), "UTF-8");
				entitys.setContentEncoding("UTF-8");
				entitys.setContentType("application/json");
				post.setEntity(entitys);
				// 5、 执行post请求
				CloseableHttpResponse response = httpClient.execute(post);
				// 6、 获取响应数据
				HttpEntity entity = response.getEntity();
				// 7、 响应数据转换为字符串
				String data = EntityUtils.toString(entity);
				// 8、 关闭 httpClient对象、关闭 response
				response.close();
				httpClient.close();
				System.out.println(data);
				JSONObject  obj = JSONObject.fromObject(data);
				return obj;
			}
}
