package com.pay.util;

import java.security.MessageDigest;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.pay.config.WxPayConfig;
  
/** 
 * 官方给的使用js的验证工具 
 * @author Administrator 
 * 
 */  
public class JsSignUtil {  
    public static String accessToken = null;  
      
    public static Map<String, String> sign(String url) {  
        String jsapi_ticket = WeixinPayUtil.getTicket();  
        Map<String, String> ret = new HashMap<String, String>();  
        String nonce_str = create_nonce_str();  
        String timestamp = create_timestamp();  
        String string1;  
        String signature = "";  
  
        //注意这里参数名必须全部小写，且必须有序  
        string1 = "jsapi_ticket=" + jsapi_ticket +  
                  "&noncestr=" + nonce_str +  
                  "&timestamp=" + timestamp +  
                  "&url=" + url;  
        System.out.println("string1="+string1);  
  
        	
       /* MessageDigest crypt = MessageDigest.getInstance("SHA-1");  
        crypt.reset();  
        crypt.update(string1.getBytes("UTF-8"));  
        signature = byteToHex(crypt.digest());  */
        ret.put("url", url);  
        ret.put("jsapi_ticket", jsapi_ticket);  
        ret.put("nonceStr", nonce_str);  
        ret.put("timestamp", timestamp);  
        ret.put("signature", getSha1(string1));  
        ret.put("appId",  WxPayConfig.appid);  
  
       /* System.out.println("1.ticket(原始)="+jsapi_ticket);  
        System.out.println("2.url="+ret.get("url"));  
        System.out.println("3.jsapi_ticket（处理后）="+ret.get("jsapi_ticket"));  
        System.out.println("4.nonceStr="+ret.get("nonceStr"));  
        System.out.println("5.signature="+ret.get("signature"));  
        System.out.println("6.timestamp="+ret.get("timestamp"));  */
          
        return ret;  
    }  
    
    public static String getSha1(String str){
        if(str==null||str.length()==0){
            return null;
        }
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9',
                'a','b','c','d','e','f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j*2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];      
            }
            return new String(buf);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }
      
    /** 
     * 随机加密 
     * @param hash 
     * @return 
     */  
    private static String byteToHex(final byte[] hash) {  
        Formatter formatter = new Formatter();  
        for (byte b : hash)  
        {  
            formatter.format("%02x", b);  
        }  
        String result = formatter.toString();  
        formatter.close();  
        return result;  
    }  
  
    /** 
     * 产生随机串--由程序自己随机产生 
     * @return 
     */  
    private static String create_nonce_str() {  
        return UUID.randomUUID().toString();  
    }  
  
    /** 
     * 由程序自己获取当前时间 
     * @return 
     */  
    private static String create_timestamp() {  
        return Long.toString(System.currentTimeMillis() / 1000);  
    }  
}
