package com.pay.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.common.ContextString;
import com.pay.dto.MyX509TrustManager;

import net.sf.json.JSONObject;

public class WXAuthUtil {
	public static JSONObject doGetJson(String url) throws ClientProtocolException, IOException {
		    JSONObject jsonObject =null;
		    DefaultHttpClient client = new DefaultHttpClient();
		    HttpGet httpGet =new HttpGet(url);
		    HttpResponse response = client.execute(httpGet);
		    HttpEntity entity =response.getEntity();
		    if(entity!=null)
		    {
		      //把返回的结果转换为JSON对象
		      String result =EntityUtils.toString(entity, "UTF-8");
		      jsonObject =JSONObject.fromObject(result);
		    }
		    return jsonObject;
	}
		
	public static  String getAccessToken() throws ClientProtocolException, IOException{  
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
        
        requestUrl=requestUrl.replace("APPID", ContextString.appid);  
        requestUrl=requestUrl.replace("APPSECRET", ContextString.appsecret);
        
        JSONObject jsonObject = WXAuthUtil.httpRequest(requestUrl, "GET", null);
        System.out.println("jsonObject" + jsonObject);
        String access_token = jsonObject.getString("access_token");
        return access_token;
      
	}
	
	
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
	    JSONObject jsonObject = null;
	    StringBuffer buffer = new StringBuffer();
	    try {
	        // 创建SSLContext对象，并使用我们指定的信任管理器初始化
	        TrustManager[] tm = { new MyX509TrustManager() };
	        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
	        sslContext.init(null, tm, new java.security.SecureRandom());
	        // 从上述SSLContext对象中得到SSLSocketFactory对象
	        SSLSocketFactory ssf = sslContext.getSocketFactory();
	        URL url = new URL(requestUrl);
	        HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
	        httpUrlConn.setSSLSocketFactory(ssf);
	        httpUrlConn.setDoOutput(true);
	        httpUrlConn.setDoInput(true);
	        httpUrlConn.setUseCaches(false);
	        // 设置请求方式（GET/POST）
	        httpUrlConn.setRequestMethod(requestMethod);
	        if ("GET".equalsIgnoreCase(requestMethod))
	            httpUrlConn.connect();
	        // 当有数据需要提交时
	        if (null != outputStr) {
	            OutputStream outputStream = httpUrlConn.getOutputStream();
	            // 注意编码格式，防止中文乱码
	            outputStream.write(outputStr.getBytes("UTF-8"));
	            outputStream.close();
	        }
	        // 将返回的输入流转换成字符串
	        InputStream inputStream = httpUrlConn.getInputStream();
	        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
	        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	        String str = null;
	        while ((str = bufferedReader.readLine()) != null) {
	            buffer.append(str);
	        }
	        bufferedReader.close();
	        inputStreamReader.close();
	        // 释放资源
	        inputStream.close();
	        inputStream = null;
	        httpUrlConn.disconnect();
	        jsonObject = JSONObject.fromObject(buffer.toString());
	    } catch (ConnectException ce) {
	      System.out.println( ce.getMessage()); 
	    } catch (Exception e) {
	       System.out.println(e.getMessage());
	    }
	    return jsonObject;
	    }
}
