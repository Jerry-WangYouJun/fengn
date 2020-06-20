package com.pay.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cmoit.service.CMoitCardAgentService;
import com.common.StringUtils;
import com.model.History;
import com.model.InfoVo;
import com.model.Packages;
import com.model.Rebate;
import com.model.User;
import com.pay.config.WxPayConfig;
import com.pay.util.CommonUtil;
import com.pay.util.OrderUtils;
import com.pay.util.RequestHandler;
import com.pay.util.Sha1Util;
import com.pay.util.SignUtil;
import com.pay.util.WXAuthUtil;
import com.pay.util.WeixinPayUtil;
import com.service.CardInfoService;
import com.service.PackagesService;
import com.service.UserService;

/**
 * 微信支付Controller
 * @author Jeff Xu
 * @since 2016-08-11
 */

@Controller
@RequestMapping("/wx")
@SuppressWarnings("rawtypes")
public class WeixinPayController {
	
	@Autowired
	CardInfoService service ; 
	@Autowired
	UserService userService;
	@Autowired
	PackagesService packagesService;
	@Autowired
    CMoitCardAgentService  ccaService;
	
	private static String baseUrl = "http://iot.iot10.cn";
	Map<String,String>  excuteResultMap = new HashMap<>();
	
	/**
	 * 验证token
	 * @param response
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 */
	@RequestMapping("/token")
	public void getToken(HttpServletResponse response, String signature,
			String timestamp, String nonce, String echostr) {
		PrintWriter out;
		try {
			out = response.getWriter();
			// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
			if (SignUtil.checkSignature(signature, timestamp, nonce)) {
				out.print(echostr);
			}
			out.close();
			out = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 微信网页授权获取用户基本信息，先获取 code，跳转 url 通过 code 获取 openId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/userAuth")
	public String userAuth(HttpServletRequest request, HttpServletResponse response){
		try {
			String iccid = request.getParameter("iccid");
			String orderId = OrderUtils.genOrderNo(iccid);
			String totalFee = request.getParameter("totalFee");
			String pacid = request.getParameter("pacid");
			//String totalFee = "0.01";
			System.out.println("in userAuth,orderId:" + orderId);
			String table;
			try {
				table = ccaService.queryTableByICCID(iccid);
				Rebate  rebate = packagesService.getRebateByIccid(iccid ,table);
			} catch (Exception e) {
				InfoVo   wrongInfo = new InfoVo();
				wrongInfo.setUserStatus("套餐绑定错误，请联系管理员");
				request.setAttribute("info", wrongInfo);
				return "cmoit/cardInfo";
			}
			
			//授权后要跳转的链接
			String backUri = baseUrl + "/wx/toPay";
			backUri = backUri + "?pacid=" + pacid + "&orderId=" + orderId+"&totalFee="+totalFee;
			//URLEncoder.encode 后可以在backUri 的url里面获取传递的所有参数
			backUri = URLEncoder.encode(backUri);
			//scope 参数视各自需求而定，这里用scope=snsapi_base 不弹出授权页面直接授权目的只获取统一支付接口的openid
			String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
					"appid=" + WxPayConfig.appid +
					"&redirect_uri=" +
					 backUri+
					"&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
			System.out.println("url:" + url);
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/toPay")
	public String toPay(HttpServletRequest request, HttpServletResponse response, Model model){
		try {
			String orderId = request.getParameter("orderId");
			System.out.println("in toPay,orderId:" + orderId);
			String iccid = orderId.substring(2, orderId.length()- 8);
			String table  = ccaService.queryTableByICCID(iccid);
			Rebate  rebate = packagesService.getRebateByIccid(iccid ,table);
			Packages pac = packagesService.selectPackagesById(rebate.getPackageId());
			
			pac.setRenew(rebate.getPacrenew());
			Double totalFee = rebate.getPacrenew();
//			if(StringUtils.isNotEmpty(totalFeeStr)){
//				totalFee = new Float(totalFeeStr);
//			}
			//TODO 测试用代码 totalFee = 0.01f ;
			//网页授权后获取传递的参数
			//String userId = request.getParameter("userId"); 	
			String code = request.getParameter("code");
			System.out.println("code:"+code);
			if(code == null){
				  return null ;
			}
			//获取统一下单需要的openid
			String openId =getOpenId(code);
			
			
			//获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
			//随机数 
			//String nonce_str = "1add1a30ac87aa2db72f57a2375d8fec";
			String nonce_str = UUID.randomUUID().toString().replaceAll("-", "");
			//商品描述
			String body = orderId;
			//商户订单号
			String out_trade_no = orderId;
			//订单生成的机器 IP
			String spbill_create_ip = request.getRemoteAddr();
			//总金额
			//TODO
			Long total_fee = Math.round(totalFee*100);
			//Integer total_fee = 1;
			
			//商户号
			//String mch_id = partner;
			//子商户号  非必输
			//String sub_mch_id="";
			//设备号   非必输
			//String device_info="";
			//附加数据
			//String attach = userId;
			//总金额以分为单位，不带小数点
			//int total_fee = intMoney;
			//订 单 生 成 时 间   非必输
			//String time_start ="";
			//订单失效时间      非必输
			//String time_expire = "";
			//商品标记   非必输
			//String goods_tag = "";
			//非必输
			//String product_id = "";
					
			//这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
			String notify_url = baseUrl + "/wx/notifyUrl";
			
			SortedMap<String, String> packageParams = new TreeMap<String, String>();
			packageParams.put("appid", WxPayConfig.appid);
			packageParams.put("mch_id", WxPayConfig.partner);
			packageParams.put("nonce_str", nonce_str);
			packageParams.put("body", body);
			packageParams.put("out_trade_no", out_trade_no);
			packageParams.put("total_fee", total_fee+"");
			packageParams.put("spbill_create_ip", spbill_create_ip);
			packageParams.put("notify_url", notify_url);
			packageParams.put("trade_type", WxPayConfig.trade_type);  
			packageParams.put("openid", openId);  

			RequestHandler reqHandler = new RequestHandler(request, response);
			reqHandler.init(WxPayConfig.appid, WxPayConfig.appsecret, WxPayConfig.partnerkey);
			
			String sign = reqHandler.createSign(packageParams);
			System.out.println("sign:"+sign);
			String xml="<xml>"+
					"<appid>"+WxPayConfig.appid+"</appid>"+
					"<mch_id>"+WxPayConfig.partner+"</mch_id>"+
					"<nonce_str>"+nonce_str+"</nonce_str>"+
					"<sign>"+sign+"</sign>"+
					"<body><![CDATA["+body+"]]></body>"+
					"<out_trade_no>"+out_trade_no+"</out_trade_no>"+
					"<total_fee>"+total_fee+""+"</total_fee>"+
					"<spbill_create_ip>"+spbill_create_ip+"</spbill_create_ip>"+
					"<notify_url>"+notify_url+"</notify_url>"+
					"<trade_type>"+WxPayConfig.trade_type+"</trade_type>"+
					"<openid>"+openId+"</openid>"+
					"</xml>";
			System.out.println("xml："+xml);
			
			String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
			String prepay_id="";
			prepay_id = WeixinPayUtil.getPayNo(createOrderURL, xml);
			System.out.println("prepay_id:" + prepay_id);
			if(prepay_id.equals("")){
				InfoVo   wrongInfo = new InfoVo();
				wrongInfo.setUserStatus("套餐绑定或定价错误，请联系管理员");
				request.setAttribute("info", wrongInfo);
				return "cmoit/cardInfo";
			}
			SortedMap<String, String> finalpackage = new TreeMap<String, String>();
			String timestamp = Sha1Util.getTimeStamp();
			String packages = "prepay_id="+prepay_id;
			finalpackage.put("appId", WxPayConfig.appid);
			finalpackage.put("timeStamp", timestamp);
			finalpackage.put("nonceStr", nonce_str);
			finalpackage.put("package", packages);
			finalpackage.put("signType", WxPayConfig.signType);
			String finalsign = reqHandler.createSign(finalpackage);
			System.out.println("/jsapi?appid="+WxPayConfig.appid+"&timeStamp="+timestamp+"&nonceStr="+nonce_str+"&package="+packages+"&sign="+finalsign);
			
			model.addAttribute("appid", WxPayConfig.appid);
			model.addAttribute("timeStamp", timestamp);
			model.addAttribute("nonceStr", nonce_str);
			model.addAttribute("packageValue", packages);
			model.addAttribute("sign", finalsign);
			
			model.addAttribute("bizOrderId", orderId);
			model.addAttribute("orderId", orderId);
			model.addAttribute("payPrice", total_fee);
			model.addAttribute("iccid", iccid);
			model.addAttribute("pac", pac);
			return "cmoit/cardInfo_pay_third";
		}  catch (Exception e) {
			InfoVo   wrongInfo = new InfoVo();
			wrongInfo.setUserStatus("套餐绑定或定价错误，请联系管理员");
			request.setAttribute("info", wrongInfo);
			return "cmoit/cardInfo";
		}
	}
	
	/**
	 * 
	 * @param code 网页授权后获取传递的参数
	 * @return
	 */
	private String getOpenId(String code) {
		String openId = "";
		String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="
				+ WxPayConfig.appid + "&secret=" + WxPayConfig.appsecret + "&code=" + code + "&grant_type=authorization_code";
		System.out.println("URL:"+URL);
		JSONObject jsonObject = CommonUtil.httpsRequest(URL, "GET", null);
		if (null != jsonObject) {
			openId = jsonObject.getString("openid");
			System.out.println("openId:" + openId);
		}
		return openId ;
	}
	

	/**
	 * 微信异步回调，不会跳转页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/notifyUrl")
	public String weixinReceive(HttpServletRequest request,HttpServletResponse response, Model model){
		System.out.println("==开始进入h5支付回调方法==");
		String xml_review_result = WeixinPayUtil.getXmlRequest(request);
		System.out.println("微信支付结果:"+xml_review_result);
		Map resultMap = null;
		try {
			resultMap = WeixinPayUtil.doXMLParse(xml_review_result);
			System.out.println("resultMap:"+resultMap);
			if(resultMap != null && resultMap.size() > 0){
				String sign_receive = (String)resultMap.get("sign");
				System.out.println("sign_receive:"+sign_receive);
				resultMap.remove("sign");
				String checkSign = WeixinPayUtil.getSign(resultMap,WxPayConfig.partnerkey);
				System.out.println("checkSign:"+checkSign);

				//签名校验成功
				if(checkSign != null && sign_receive != null &&
						checkSign.equals(sign_receive.trim())){
					System.out.println("weixin receive check Sign sucess");
                    try{
                    	//获得返回结果
                    	String out_trade_no = (String)resultMap.get("out_trade_no");
                    	System.out.println("weixin pay sucess,out_trade_no:"+out_trade_no);
                    	String return_code = (String)resultMap.get("return_code");
						String resXml = "<xml>" + 
            		 				 "<return_code><![CDATA[SUCCESS]]></return_code>" + 
            		 				 "<return_msg><![CDATA[OK]]></return_msg>" + 
            		 				 "</xml>";
		            		 try {
		                         // 通过response 回复微信
		                         BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
		                         out.write(resXml.getBytes());
		                         out.flush();
		                         out.close();
		                     } catch (IOException e) {
		                         e.printStackTrace();
		                     }  
                    	if("SUCCESS".equals(return_code)){
                    		//处理支付成功以后的逻辑，这里是写入相关信息到文本文件里面，如果有订单的处理订单
                    		try{
                    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh24:mm:ss");
                    			//TxtUtil.writeToTxt(content, fileUrl);
                    			InfoVo info = new InfoVo();
                    			String iccid =out_trade_no.substring(2, out_trade_no.length()- 8) ;
                    			info.setICCID(iccid);
                    			info.setOrderNo(out_trade_no);
                    			service.queryHistoryList(info);
                    			String table  = ccaService.queryTableByICCID(iccid); 
                    			if(info.getHistory().size() == 0) {
                    				
                    				Rebate rebatePerson = packagesService.getRebateByIccid(iccid,table);
                    				History  history = new History();
                    				history.setOrderNo(out_trade_no);
                    				history.setIccid(iccid);
                    				history.setMoney(rebatePerson.getPacrenew());
                    				history.setUpdateDate(DateUtil.formatDate(new Date(), "yyyy-MM-dd"));
                    				history.setPackageId(rebatePerson.getPackageId());
                    				history.setAgentid(1);
                    				service.insertHistory(history);
                    				service.updateCardStatus(iccid);
                    			}
                    			////////返利  企业付款 20200424 start 
                    			
                    			//通过iccid 号码获取所有关联的返利人员
                    			
                    			List<Rebate> rebateList = this.getRebatePersonList(iccid , table);
                    			if(rebateList.size()!=0 || rebateList != null)
                    			{
                    				int u = 1 ;
                    				for (Rebate rebate : rebateList) {
                    					System.out.println("返利第" + u +"次");
                    					//根据返利比例计算 返利钱数                      				
                    					this.enterprisePayment(request, response, rebate, "返利");
                    					u++;
                    				}
                    			}
                    			///////返利 企业付款 20200424 end
                    			
                    		}catch(Exception e){
                    			e.printStackTrace();
                    		}
                    	}else{
                    	    model.addAttribute("payResult", "0");
                    	    model.addAttribute("err_code_des", "通信错误");
                    	}
                    }catch(Exception e){
                    	e.printStackTrace();
                    }
				}else{
					//签名校验失败
					System.out.println("weixin receive check Sign fail");
                    String checkXml = "<xml><return_code><![CDATA[FAIL]]></return_code>"+
                    					"<return_msg><![CDATA[check sign fail]]></return_msg></xml>";
                    WeixinPayUtil.getTradeOrder("http://weixin.xinfor.com/wx/notifyUrl", checkXml);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	/**
	 * 页面js返回支付成功后，查询微信后台是否支付成功，然后跳转结果页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/success")
	public String toWXPaySuccess(HttpServletRequest request,
			HttpServletResponse response, Model model) throws IOException{
		String id = request.getParameter("orderId");
		String iccid = "";
		if(StringUtils.isNotEmpty(id)){
			iccid =  id.substring(2, id.length()- 8) ;
		}
		System.out.println("toWXPaySuccess, orderId: " + id);
		try {
			Map resultMap = WeixinPayUtil.checkWxOrderPay(id);
			System.out.println("resultMap:" + resultMap);
			String return_code = (String)resultMap.get("return_code");
        	String result_code = (String)resultMap.get("result_code");
        	System.out.println("return_code:" + return_code + ",result_code:" + result_code);
        	if("SUCCESS".equals(return_code)){
        		if("SUCCESS".equals(result_code)){
            	    model.addAttribute("orderId", id);
        			model.addAttribute("payResult", "1");
        		}else{
        			String err_code = (String)resultMap.get("err_code");
            	    String err_code_des = (String)resultMap.get("err_code_des");
            	    System.out.println("weixin resultCode:"+result_code+",err_code:"+err_code+",err_code_des:"+err_code_des);
            	    model.addAttribute("err_code", err_code);
            	    model.addAttribute("err_code_des", err_code_des);
        			model.addAttribute("payResult", "0");
        		}
        	}else{
        	    model.addAttribute("payResult", "0");
        	    model.addAttribute("err_code_des", "通信错误");
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		String url = "http://iot.iot10.cn/cmoit/info/c?iccid=" +  iccid ;
		response.sendRedirect(url);
		return null;
	}
	
	
	
	/**
	 *  获取返利人员所有必要数据 
	 *  rebate 为单独添加模型
	 * @param iccId
	 * @return
	 */
//	@RequestMapping("/testRebatePerson")
//	@ResponseBody
	public List<Rebate> getRebatePersonList(String iccId , String table )
	{
		return packagesService.getRebatePersonList(iccId ,table );
	}
	
	
	
	
	@RequestMapping("/init")
	public String act(HttpServletRequest request, HttpServletResponse response,String userid){
		//授权后要跳转的链接
		//邀约传web   活动传act   文章传article
		String backUri = baseUrl + "/wx/checkact/"+userid  ;
		//URLEncoder.encode 后可以在backUri 的url里面获取传递的所有参数
		backUri = URLEncoder.encode(backUri);
		//scope 参数视各自需求而定，这里用scope=snsapi_base 不弹出授权页面直接授权目的只获取统一支付接口的openid
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" +
				"appid=" + WxPayConfig.appid +
				"&redirect_uri=" +
				 backUri+
				"&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
		System.out.println("url:" + url);
		return "redirect:"+url;
	}
	
	@RequestMapping("/checkact/{userid}")
	public  String getUserInfo(HttpServletRequest request, HttpServletResponse response , @PathVariable("userid") Integer userid ) throws IOException{
		ModelAndView mv = new ModelAndView();
		String code =request.getParameter("code");
		String user = request.getParameter("userid");
	      //第二步：通过code换取网页授权access_token
	         String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+WxPayConfig.appid
	                + "&secret="+WxPayConfig.appsecret
	                + "&code="+code
	                + "&grant_type=authorization_code";
	        System.out.println("url:"+url);
	        JSONObject jsonObject = WXAuthUtil.doGetJson(url);
	        System.out.println(jsonObject);
	        String openid = jsonObject.getString("openid");
	        String access_token = jsonObject.getString("access_token");
	        String infoUrl="https://api.weixin.qq.com/sns/userinfo?access_token="+access_token
	        		+ "&openid="+openid
	        		+ "&lang=zh_CN";
	        JSONObject userinfo = CommonUtil.httpsRequest(infoUrl, "GET", null);
	        System.out.println(userinfo);
	        if (null != userinfo) {
	        	System.out.println("userInfo:" + userinfo.toString());
	        }
	        String  nickname =  URLEncoder.encode(userinfo.getString("nickname"), "utf-8");
	        userService.updateOpenID(userid, openid , nickname, userinfo.getString("headimgurl"));
	        
	        return "cmoit/success";
	}
	
	private JSONObject getUserInfoByUnionID(String accessToken , String openId){
		String  userJson = "";
		/**
		 * 拉取用户信息
		 */
		return null ;
	}
	
	
	
	
	
	/**
	 * 获取 应该返利的人员list （废弃）
	 * @param iccid
	 */
	public List<User> getRebatePerson(String iccId)
	{
		List<User> userList = new ArrayList<User>();	
		userList = userService.getRebatePerson(iccId);
		return userList;
	}

	/**
	 * 通过wx接口获取 大量openId 
	 * @return
	 */
	@RequestMapping("/getAllUserOpenId")
	@ResponseBody
	public String getAllUserOpenId()
	{
		try {
			//获取所有人的openIdList 最多10000个
			String openIdList = WeixinPayUtil.getUserOpenIdList();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

	/**
	 * 企业付款功能  （测试用 固定写死openId 需要传入 金额 amount）
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws UnknownHostException 
	 */
	@RequestMapping("/enterprisePayment")
	@ResponseBody
	public String enterprisePayment1(HttpServletRequest request, HttpServletResponse response, Model model) throws UnknownHostException
	{
		//商户订单号  随机生成 32位 数字+字母		
		String partner_trade_no = UUID.randomUUID().toString().replaceAll("-","");		
		System.out.println("in enterprisePayment,partner_trade_no:" + partner_trade_no);
		
		//传入 返利金额
		String amountStr = request.getParameter("amount");

		int total_fee = BigDecimal.valueOf(Double.parseDouble(amountStr) * 100).setScale(0, BigDecimal.ROUND_UP).intValue();
		
		String openId= "oQTPQwVrWxwcU079m-87Vuh5sjSk";
		
		//获取 收款用户的 用户openid
		//String openId =getOpenId(code);
		//申请商户号的appid或商户号绑定的appid
//		String mch_id = "1448494802";
				
		//随机数 
		String nonce_str = UUID.randomUUID().toString().replaceAll("-", "");
		
		//企业付款备注	 
		String desc = "返利";	
		
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("mch_appid", WxPayConfig.appid);
		packageParams.put("mchid", WxPayConfig.partner);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("partner_trade_no", partner_trade_no);
		packageParams.put("openid", openId);
		packageParams.put("check_name", "NO_CHECK");
		packageParams.put("amount", String.valueOf(total_fee));
		packageParams.put("desc", desc);
		packageParams.put("spbill_create_ip", String.valueOf(InetAddress.getLocalHost().getHostAddress()));
		
		RequestHandler reqHandler = new RequestHandler(request, response);
		reqHandler.init(WxPayConfig.appid, WxPayConfig.appsecret, WxPayConfig.partnerkey);
		
		String sign = reqHandler.createSign(packageParams);
		System.out.println("sign:"+sign+"========packageParams===="+packageParams.toString());
		String xml="<xml>"+
						"<mch_appid>"+WxPayConfig.appid+"</mch_appid>"+
						"<mchid>"+WxPayConfig.partner+"</mchid>"+
						"<nonce_str>"+nonce_str+"</nonce_str>"+					
						"<partner_trade_no>"+partner_trade_no+"</partner_trade_no>"+
						"<openid>"+openId+"</openid>"+
						"<check_name>NO_CHECK</check_name>"+				
						"<amount>"+String.valueOf(total_fee)+"</amount>"+
						"<desc>"+desc+"</desc>"+
						"<spbill_create_ip>"+String.valueOf(InetAddress.getLocalHost().getHostAddress())+"</spbill_create_ip>"+
						"<sign>"+sign+"</sign>"+
				"</xml>";
		System.out.println("xml："+xml);
		//企业付款请求 url 
		String requestUrl= "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
		
		
		KeyStore keyStore = null;
        Resource resource = new ClassPathResource("apiclient_cert.p12");
        String Path = "";

        try {
            Path = resource.getFile().getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            keyStore = KeyStore.getInstance("PKCS12");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        InputStream instream = null;

        try {
            instream = new FileInputStream(new File(Path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try{
            keyStore.load(instream, WxPayConfig.partner.toCharArray());
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                instream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        SSLContext sslcontext = null;

        try {
            sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore,  WxPayConfig.partner.toCharArray()).build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        }
        
		String result="";
		try {
			result = WeixinPayUtil.enterprisePayment(sslcontext, xml);	
			System.out.println("result:" + result);
			return result;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	/**
	 *  企业付款   （内部调用）
	 * @param request
	 * @param response
	 * @param Rebate   返利对象
	 * @param desc     备注
	 * @return
	 * @throws UnknownHostException
	 */
	public String enterprisePayment(HttpServletRequest request, HttpServletResponse response,Rebate rebate,String desc) throws UnknownHostException
	{
		//商户订单号  随机生成 32位 数字+字母		
		String partner_trade_no = UUID.randomUUID().toString().replaceAll("-","");		
		System.out.println("in enterprisePayment,partner_trade_no:" + partner_trade_no);
		
		//将传入的金额转换成上传的格式
		int total_fee = BigDecimal.valueOf(rebate.getAmount() * 100).setScale(0, BigDecimal.ROUND_UP).intValue();			
		//随机数 
		String nonce_str = UUID.randomUUID().toString().replaceAll("-", "");
		
		//企业付款备注	 		
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("mch_appid", WxPayConfig.appid);
		packageParams.put("mchid", WxPayConfig.partner);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("partner_trade_no", partner_trade_no);
		packageParams.put("openid", rebate.getOpenId());
		packageParams.put("check_name", "NO_CHECK");
		packageParams.put("amount", String.valueOf(total_fee));
		packageParams.put("desc", desc);
		packageParams.put("spbill_create_ip", String.valueOf(InetAddress.getLocalHost().getHostAddress()));
		
		RequestHandler reqHandler = new RequestHandler(request,response);
		reqHandler.init(WxPayConfig.appid, WxPayConfig.appsecret, WxPayConfig.partnerkey);
		
		String sign = reqHandler.createSign(packageParams);
		
		String xml="<xml>"+
						"<mch_appid>"+WxPayConfig.appid+"</mch_appid>"+
						"<mchid>"+WxPayConfig.partner+"</mchid>"+
						"<nonce_str>"+nonce_str+"</nonce_str>"+					
						"<partner_trade_no>"+partner_trade_no+"</partner_trade_no>"+
						"<openid>"+rebate.getOpenId()+"</openid>"+
						"<check_name>NO_CHECK</check_name>"+				
						"<amount>"+String.valueOf(total_fee)+"</amount>"+
						"<desc>"+desc+"</desc>"+
						"<spbill_create_ip>"+String.valueOf(InetAddress.getLocalHost().getHostAddress())+"</spbill_create_ip>"+
						"<sign>"+sign+"</sign>"+
				"</xml>";
		System.out.println("xml："+xml);
		//上传证书 
		KeyStore keyStore = null;
        Resource resource = new ClassPathResource("apiclient_cert.p12");
        String Path = "";

        try {
            Path = resource.getFile().getPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            keyStore = KeyStore.getInstance("PKCS12");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        InputStream instream = null;

        try {
            instream = new FileInputStream(new File(Path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try{
            keyStore.load(instream, WxPayConfig.partner.toCharArray());
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                instream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        SSLContext sslcontext = null;

        try {
            sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore,  WxPayConfig.partner.toCharArray()).build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        }
        
		String payment_no="";
		try {
			payment_no = WeixinPayUtil.enterprisePayment(sslcontext, xml);	
			System.out.println("payment_no=========" + payment_no);
			
			/////////将企业付款记录 存入数据库  start
				
				History  history = new History();
				history.setAgentid(rebate.getAgentId());
				history.setOrderNo(payment_no);					//微信支付返回的微信付款单号
				history.setIccid(rebate.getIccId());			//支付的iccId
				history.setMoney(rebate.getAmount());			//支付的钱数
				history.setUpdateDate(DateUtil.formatDate(new Date(), "yyyy-MM-dd"));	//时间
				history.setPackageId(rebate.getPackageId());	//套餐的id 
				
				service.insertHistory(history);	
				
			////////将企业付款记录 存入数据库 end 
			
			
			return payment_no;
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
}
