package com.common;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.util.TextUtils;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import net.sf.json.JSONObject;

public class ResponseURLDataUtil {
	public static String token = "";
	
	public static JSONObject getLmbJsonData(String url) throws UnsupportedEncodingException {
		String jsonString;
		JSONObject jsonObject  = null;
		try {
			jsonString = ResponseURLDataUtil.getReturnDataWithCookie(url);
			 jsonObject  = JSONObject.fromObject(jsonString);  
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	/**
	 * 
	 * @param timeStart 开始时间
	 * @param timeEnd   结束时间
	 * @param psize  数据总数
	 * @param p    当前页码
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getUrlMlb(String timeStart ,String timeEnd ,int psize , int p) throws UnsupportedEncodingException {
		String url = "https://www.m-m10086.com/api/NewReport/RenewalsOrder?"
				+ "holdId=12896&p="+ p +"&psize="+ psize 
				+ "&payee=&source=&PayState=1,2&RenewalsState=&"
				+ "packageType=&oldPackageType=&timeType=4"
				+ "&stime=" + URLEncoder.encode(timeStart,"utf-8")
				+ "&etime=" +URLEncoder.encode(timeEnd,"utf-8")
				+ "&order=&id=&minamonth=&access=&simState=-1&"
				+ "commercialTenant=&batchType=iccid&batchValues=&groupHoldId=0";
		System.out.println(url);
		return url;
	}
	
	/**
	 *  
	 * @return 物联网查询基本信息的url
	 */
	public static String getQueryUnicomUrl(){
		String url = "https://www.m-m10086.com/api/SimListFire/Search";
		return url;
	}
	
	public static String getCookie(String token) {
		 String cookie = "aliyungf_tc=AQAAAA6GsVRs/QIAIIfHKofNPgd8nuCB; "
		 		+ "ASP.NET_SessionId=yae04reoticdwhqecaeqzlwe;"
		 		+ " RememberCookie=UserName=青岛丰宁贸易新&UserPwd=8989123; "
		 		+ "UserCookie=UserID=544939&UserName=%e9%9d%92%e5%b2%9b%e4%b8%b0%e5%ae%81%e8%b4%b8%e6%98%93%e6%96%b0"
		 		+ "&UserType=1&HoldID=12896&HoldName=%e9%9d%92%e5%b2%9b%e4%b8%b0%e5%ae%81%e8%b4%b8%e6%98%93%e6%96%b0"
		 		+ "&HoldLevel=4&HoldType=6&Token="
		 		+ token
		 		+ "&LoginFromType=0&OEMClient=";
		 
		 return cookie ;
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
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            conn.setDoOutput(true);  
            conn.setRequestMethod("POST");   
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
    
    /**
	 * 执行物联网新增续费的url获取 ， get类型的请求
	 * @param urlString
	 * @return
	 * @throws UnsupportedEncodingException
	 */
    public static String getReturnDataWithCookie(String urlString ) throws UnsupportedEncodingException {  
        String res = "";   
        try {   
            URL url = new URL(urlString);  
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection)url.openConnection();  
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            conn.setDoOutput(true);  
            conn.setRequestProperty("cookie", getCookie(getToken("青岛丰宁贸易新", "8989123")));
            conn.setRequestMethod("GET");   
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
    
    /**
     * 
     * @param urlString 请求的url
     * @param json  带查询条件的json字符串
     * @return 查询结果
     * @throws UnsupportedEncodingException
     */
    public static String getPOSTReturnDataWithCookie(String urlString ,String json , String token) throws UnsupportedEncodingException {  
        String res = "";   
        try {   
            URL url = new URL(urlString);  
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection)url.openConnection();  
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            conn.setDoOutput(true);  
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Charset", "UTF-8");
           // conn.setRequestProperty("cookie", getCookie(getToken("青岛丰宁贸易新", "8989123")));
            conn.setRequestProperty("cookie", getCookie(token));
            conn.setRequestMethod("POST");   
            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
            conn.setRequestProperty("accept","application/json");
            if (json != null && !TextUtils.isEmpty(json)) {
                byte[] writebytes = json.getBytes();
                // 设置文件长度
                conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                OutputStream outwritestream = conn.getOutputStream();
                outwritestream.write(json.getBytes());
                outwritestream.flush();
                outwritestream.close();
            }
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
    
    public static String getToken() throws Exception{
    	return getToken("青岛丰宁贸易新", "8989123");
    }
    
    /**
	 * 模拟登陆 ， 获取token生成cookie
	 * 
	 * @param userName
	 *            用户名
	 * @param pwd
	 *            密码
	 * 
	 * **/
	public static String getToken(String userName, String pwd) throws Exception {
		// 第一次请求
		Connection con = Jsoup
				.connect("https://www.m-m10086.com/User/LoginYD");// 获取连接
		con.header("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");// 配置模拟浏览器
		Response rs = con.execute();// 获取响应
		Document d1 = Jsoup.parse(rs.body());// 转换为Dom树
		List<Element> et = d1.select("form");// 获取form表单，可以通过查看页面源码代码得知
		// 获取，cooking和表单属性，下面map存放post时的数据
		Map<String, String> datas = new HashMap<>();
		for (Element e : et.get(0).getAllElements()) {
			if (e.attr("name").equals("userName")) {
				e.attr("value", userName);// 设置用户名
			}
			if (e.attr("name").equals("userPwd")) {
				e.attr("value", pwd); // 设置用户密码
			}
			if (e.attr("name").length() > 0) {// 排除空值表单属性
				datas.put(e.attr("name"), e.attr("value"));
			}
		}
		/**
		 * 第二次请求，post表单数据，以及cookie信息
		 * 
		 * **/
		Connection con2 = Jsoup
				.connect("https://www.m-m10086.com/User/LoginYD");
		con2.header("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
		// 设置cookie和post上面的map数据
		Response login = con2.ignoreContentType(true).method(Method.POST)
				.data(datas).cookies(rs.cookies()).execute();
		// 打印，登陆成功后的信息
		//System.out.println(login.body());
		String token = "";
		// 登陆成功后的cookie信息，可以保存到本地，以后登陆时，只需一次登陆即可
		Map<String, String> map = login.cookies();
		for (String s : map.keySet()) {
			String[] cookieArr = map.get(s).split("&");
			for(String cookie : cookieArr) {
				  if(cookie.startsWith("Token")) {
					        token = cookie.substring(6);
				  }
			}
 		}
		return token ;
	}
	
	
	public static JSONObject getUnicomCard(int pindex,int pRowCount , String iccids , String storeStatus  , String token) throws UnsupportedEncodingException {
		String jsonString;
		JSONObject jsonObject  = null;
		try {
			String url = getQueryUnicomUrl();
			Map map = new HashMap();
			map.put("p",  pindex);
			map.put("pRowCount", pRowCount);
			map.put("loginHoldId", "12896");
			map.put("key", "");
			map.put("storeState", storeStatus);
			map.put("noChild", "0");
			map.put("groupHoldId", "0");
			map.put("batchCardStr", iccids);
			map.put("batchType", "1");
			JSONObject json = JSONObject.fromObject(map);
			jsonString = ResponseURLDataUtil.getPOSTReturnDataWithCookie(url , json.toString() ,token);
			 jsonObject  = JSONObject.fromObject(jsonString);  
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
			return jsonObject ;
	}
	
	//10000  -114   1000 - 6  2000 -10  
	public static void main(String[] args) {
		try {
			System.out.println(DateUtils.formatDate("yyyyMMddHHmmss"));
			Map map = new HashMap();
//			map.put("p",  1);
			map.put("pRowCount", 1);
			map.put("loginHoldId", "12896");
//			map.put("key", "");
			//map.put("storeState", "3,4");
			//map.put("bootState", 3);
			map.put("distributeDate", "2018-06-01");
//			map.put("noChild", "0");
//			map.put("groupHoldId", "0");
//			map.put("batchCardStr", "898604010118C1517243");
//			map.put("batchType", "1");
			JSONObject json  = getMLBData( getToken(), ContextString.URL_UNICOM_SEARCH,map);
			System.out.println(json.toString());
			System.out.println(DateUtils.formatDate("yyyyMMddHHmmss"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static JSONObject getCmccCard(int pindex,int pRowCount , String iccids , String storeStatus , String token) throws UnsupportedEncodingException {
		String jsonString;
		JSONObject jsonObject  = null;
		try {
			String url = "https://www.m-m10086.com/api/YDSimListFire/Search";
			Map map = new HashMap();
			map.put("p",  pindex);
			map.put("pRowCount", pRowCount);
			map.put("loginHoldId", "12896");
			map.put("key", "");
			map.put("storeState", storeStatus);
			map.put("noChild", "0");
			map.put("groupHoldId", "0");
			map.put("batchCardStr", iccids);
			map.put("batchType", "1");
			JSONObject json = JSONObject.fromObject(map);
			jsonString = ResponseURLDataUtil.getPOSTReturnDataWithCookie(url , json.toString() , token);
			jsonObject  = JSONObject.fromObject(jsonString);  
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
			return jsonObject ;
	}
	
	public static JSONObject getCmccCardBind( String token) throws UnsupportedEncodingException {
		String jsonString;
		JSONObject jsonObject  = null;
		try {
			String url = "https://www.m-m10086.com/api/YDSimListFire/Binding";
			Map map = new HashMap();
			List<String> arr = new ArrayList<>();
			arr.add("9946186");
			arr.add("9946184");
			arr.add("9946176");
			map.put("simIds", arr);
			JSONObject json = JSONObject.fromObject(map);
			jsonString = ResponseURLDataUtil.getPOSTReturnDataWithCookie(url , json.toString() , token);
			 jsonObject  = JSONObject.fromObject(jsonString);  
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
			return jsonObject ;
	}
	
	
	public static JSONObject getMLBData( String token , String url , Map params) throws UnsupportedEncodingException {
		String jsonString;
		JSONObject jsonObject  = null;
		try {
			JSONObject json = JSONObject.fromObject(params);
			jsonString = ResponseURLDataUtil.getPOSTReturnDataWithCookie(url , json.toString() ,token);
			jsonObject  = JSONObject.fromObject(jsonString);  
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
			return jsonObject ;
	}
	
	
}
