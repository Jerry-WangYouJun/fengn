package com.pay.util;

import java.io.ByteArrayInputStream;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.http.HttpEntity;

import com.common.ResponseURLDataUtil;
import com.common.StringUtils;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import com.pay.config.WxPayConfig;
import com.pay.dto.WeixinInfoDTO;
import com.pay.util.http.HttpClientConnectionManager;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 微信支付工具类
 * @author Jeff Xu
 * @since 2015-09-01
 *
 */
public class WeixinPayUtil {
	
	public static final String SUBMIT_URL ="https://api.mch.weixin.qq.com/pay/unifiedorder";

//	public static DefaultHttpClient httpclient;

//	public static CloseableHttpClient  httpclient;
//	static {
////		httpclient =  HttpClients.createDefault();
//		httpclient = new DefaultHttpClient();
//		httpclient = (DefaultHttpClient) HttpClientConnectionManager
//				.getSSLInstance(httpclient);
////		httpclient = (CloseableHttpClient) HttpClientConnectionManager
////		.getSSLInstance(httpclient);
//	}
	public static CloseableHttpClient httpclient;
	static {
		 httpclient = HttpClients.createDefault();
	}
	
	@SuppressWarnings("rawtypes")
	public static String getPayNo(String url, String xmlParam) {
		/*
		 * DefaultHttpClient client = new DefaultHttpClient();
		 * client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
		 */
		CloseableHttpClient  httpclient = HttpClients.createDefault();
//		httpclient.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
		
		HttpPost httpost = new HttpPost(url);
//		HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
		String prepay_id = "";
		try {
			httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
			HttpResponse response = httpclient.execute(httpost);
//			CloseableHttpResponse response = httpclient.execute(httpost);
			String jsonStr = EntityUtils
					.toString(response.getEntity(), "UTF-8");
			//Map<String, Object> dataMap = new HashMap<String, Object>();

			if (jsonStr.indexOf("FAIL") != -1) {
				return prepay_id;
			}
			Map map = doXMLParse(jsonStr);
			//String return_code = (String) map.get("return_code");
			prepay_id = (String) map.get("prepay_id");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prepay_id;
	}

	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * 
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map doXMLParse(String strxml) throws Exception {
		if (null == strxml || "".equals(strxml)) {
			return null;
		}

		Map m = new HashMap();
		InputStream in = String2Inputstream(strxml);
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List children = e.getChildren();
			if (children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = getChildrenText(children);
			}

			m.put(k, v);
		}

		// 关闭流
		in.close();

		return m;
	}

	/**
	 * 获取子结点的xml
	 * 
	 * @param children
	 * @return String
	 */
	@SuppressWarnings("rawtypes")
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if (!children.isEmpty()) {
			Iterator it = children.iterator();
			while (it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if (!list.isEmpty()) {
					sb.append(getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}

		return sb.toString();
	}

	public static InputStream String2Inputstream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}

	/**
	 * 获取统一订单提交返回字符串值
	 * @param url
	 * @param xmlParam
	 * @return
	 */
	public static String getTradeOrder(String url, String xmlParam) {
//		DefaultHttpClient client = new DefaultHttpClient();
//		client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS,
//				true);
		CloseableHttpClient  httpclient = HttpClients.createDefault();
		HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
		String jsonStr = "";
		try {
			httpost.setEntity(new StringEntity(xmlParam, "UTF-8"));
			HttpResponse response = httpclient.execute(httpost);
			jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonStr;
	}
	
	/**
	 * 生成二维码地址
	 * @param weixinInfoDTO
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String generateCodeUrl(WeixinInfoDTO weixinInfoDTO){
		String codeUrl = "";
		String submitXml = buildWeixinXml(weixinInfoDTO);
		String resultStr = getTradeOrder(SUBMIT_URL,submitXml);
		if(StringUtils.isNotEmpty(resultStr)){
			try {
				Map map = doXMLParse(resultStr);
				codeUrl = (String)map.get("code_url");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return codeUrl;
	}

	/**
	 * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 */
	@SuppressWarnings("rawtypes")
	public static String createSign(SortedMap<String, String> packageParams) {
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + WxPayConfig.partnerkey);
		String sign = MD5Util.MD5Encode(sb.toString(), "UTF-8").toUpperCase();
		return sign;

	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String getSign(Map<String,String> paramMap,String key){
		List list = new ArrayList(paramMap.keySet());
		Object[] ary = list.toArray();
		Arrays.sort(ary);
		list = Arrays.asList(ary);
		String str = "";
		for(int i=0;i<list.size();i++){
			str+=list.get(i)+"="+paramMap.get(list.get(i)+"")+"&";
		}
		str+="key="+key;
		str = MD5Util.MD5Encode(str, "UTF-8").toUpperCase();

		return str;
	}
	
	/**
	 * 创建提交统一订单的xml
	 * @return
	 */
	public static String buildWeixinXml(WeixinInfoDTO weixinInfoDTO){
		String xml = "<xml>" + "<appid>"+weixinInfoDTO.getAppid()+"</appid>"
				+ "<body>"+weixinInfoDTO.getBody()+"</body>" + "<mch_id>"+weixinInfoDTO.getMch_id()+"</mch_id>"
				+ "<nonce_str>"+weixinInfoDTO.getNonce_str()+"</nonce_str>"
				+ "<notify_url>"+weixinInfoDTO.getNotify_url()+"</notify_url>"
				+ "<out_trade_no>"+weixinInfoDTO.getOut_trade_no()+"</out_trade_no>"
				+ "<spbill_create_ip>"+weixinInfoDTO.getSpbill_create_ip()+"</spbill_create_ip>"
				+ "<total_fee>"+weixinInfoDTO.getTotal_fee()+"</total_fee>"
				+ "<trade_type>"+weixinInfoDTO.getTrade_type()+"</trade_type>"
				+ "<sign>"+weixinInfoDTO.getSign()+"</sign>" + "</xml>";
		System.out.println(xml);
		return xml;
	}
	
	/**
	 * 处理xml请求信息
	 * @param request
	 * @return
	 */
	public static String getXmlRequest(HttpServletRequest request){
		java.io.BufferedReader bis = null;
		String result = "";
		try{
			bis = new java.io.BufferedReader(new java.io.InputStreamReader(request.getInputStream()));
			String line = null;
			while((line = bis.readLine()) != null){
				result += line;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(bis != null){
				try{
					bis.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
		return result;
	}
	
	/**
	 * 查询订单
	 * @param orderId
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	public static Map checkWxOrderPay(String orderId) throws Exception{
		String nonce_str = UUID.randomUUID().toString().replaceAll("-", "");
		//Boolean payFlag = false;
		String sign = "";
		SortedMap<String, String> storeMap = new TreeMap<String, String>();
		storeMap.put("out_trade_no", orderId); // 商户 后台的贸易单号
		storeMap.put("appid", WxPayConfig.appid); // appid
		storeMap.put("mch_id", WxPayConfig.partner); // 商户号
		storeMap.put("nonce_str", nonce_str); // 随机数
		System.out.println("nonce_str" + nonce_str );
		sign = createSign(storeMap);
		System.out.println(sign);
		String xml = "<xml><appid>" + WxPayConfig.appid + "</appid><mch_id>" + WxPayConfig.partner + "</mch_id>"+
					"<nonce_str>" + nonce_str + "</nonce_str>"+
                    "<out_trade_no>"+orderId+"</out_trade_no>"+
                    "<sign>"+sign+"</sign></xml>";
		String resultMsg = getTradeOrder("https://api.mch.weixin.qq.com/pay/orderquery", xml);
	    System.out.println("orderquery,result:" + resultMsg);
		if(StringUtils.isNotEmpty(resultMsg)){
	    	Map resultMap = WeixinPayUtil.doXMLParse(resultMsg);
	    	if(resultMap != null && resultMap.size() > 0){
	    		/*String result = (String)resultMap.get("trade_state");
	    		if(StringUtils.isNotEmpty(result)){
	    			if(result.equals("SUCCESS") || result.equals("success")){
	    				payFlag = true;
	    			}
	    		}*/
	    		return resultMap;
	    	}
	    }
	    return null;
	}

	public static String getTicket() {
        String ticket = null;
        try {
        	String access_token = WeixinPayUtil.getAccessToken(); //有效期为7200秒
        	String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+ access_token +"&type=jsapi";
        	System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
        	System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
        	String jsonString =	ResponseURLDataUtil.getReturnData(url) ;
   		 	JSONObject jsonObject = JSONObject.fromObject(jsonString);  
            ticket = jsonObject.getString("ticket");
            System.out.println("ticket:" + ticket);
        } catch (Exception e) {
                e.printStackTrace();
        }
         
        return ticket;
    }
	
    public static String getAccessToken() throws UnsupportedEncodingException{

        String appid=  WxPayConfig.appid;//应用ID

        String appSecret= WxPayConfig.appsecret;//(应用密钥)

        String url ="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appid+"&secret="+appSecret+"";

        String jsonString =	ResponseURLDataUtil.getReturnData(url) ;
	 	JSONObject jsonObject = JSONObject.fromObject(jsonString);  
 	    String accessToken  = jsonObject.getString("access_token");
        System.out.println("access_token:" + accessToken);


        return accessToken;

 }
    /**
     *	 获取 openId 列表 最多10000个
     * @return String
     */
    public static String getUserOpenIdList()
    {
    	String accessToken =null;
    	String openIds = null;
		try {
			accessToken = getAccessToken();
			//获取token 然后调用 拉取所有openId列表 （最多10000）
			String url ="https://api.weixin.qq.com/cgi-bin/user/get?access_token="+accessToken+"&next_openid=";
	    	String jsonString =	ResponseURLDataUtil.getReturnData(url) ;
			JSONObject jsonObject = JSONObject.fromObject(jsonString);  	
			
			JSONArray array = jsonObject.getJSONArray("data");
			
			openIds  = jsonObject.getString("data");
			System.out.println(openIds);
			System.out.println("jsonArray======="+array );
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
		return openIds;
    }
    
    
    /**
     * 企业付款
     * @throws IOException 
     */
    public static String enterprisePayment(SSLContext sslcontext,String form) throws IOException
    {    	
    	SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext
                , new String[]{"TLSv1"}    // supportedProtocols ,这里可以按需要设置
                , null    // supportedCipherSuites
                , SSLConnectionSocketFactory.getDefaultHostnameVerifier());
 
        CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        String result_code	= null;
        CloseableHttpResponse response = null;

        try {
            StringEntity reqEntity = new StringEntity(form, ContentType.create("text/xml", "UTF-8"));
 
            HttpPost httppost = new HttpPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers");
 
            httppost.setEntity(reqEntity);
 
            response = client.execute(httppost);

            String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
            Map map = doXMLParse(jsonStr);
    		result_code = (String)map.get("result_code");
            if (jsonStr.indexOf("FAIL") != -1) {
            	String err_code = (String)map.get("err_code");
            	String err_code_des = 	(String)map.get("err_code_des");	
    			return "error_code===="+err_code+"----err_code_des====="+err_code_des;
    		}   		
        } catch (Exception e) {
        	e.printStackTrace();
        } 
        finally {
        	 response.close();
        }        
        return result_code	;
    }      
	public static void main(String[] args) {
		

	}
}
