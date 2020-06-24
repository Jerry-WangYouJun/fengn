package com.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.ContextString;
import com.common.DateUtils;
import com.dao.HistoryMapper;
import com.model.History;
import com.model.Rebate;
import com.model.UnicomHistory;
import com.pay.util.RequestHandler;
import com.pay.util.WeixinPayUtil;
import com.service.CardInfoService;
import com.service.PackagesService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/mlb")
public class MlbBack {
	
	 @Autowired
	 HistoryMapper  historyDao;
	 @Autowired
	 PackagesService packagesService;
	 @Autowired
	 CardInfoService service ; 
	 
	 
	 @ResponseBody
	 @RequestMapping(value = "order", method = {RequestMethod.POST,RequestMethod.GET })
	 public String  getOrder(HttpServletRequest request, HttpServletResponse response , String data){
		 System.out.println("获取时间" + DateUtils.getDate12()+ ",data=" +  data);
		 String param= null; 
	        try {
	        	UnicomHistory  history = new UnicomHistory();
	            JSONObject jsonObject = JSONObject.fromObject(data);
	            String iccid = jsonObject.getString("iccid");
	            history.setIccid(iccid);
	            history.setImsi(jsonObject.getString("imsi"));
	            history.setMoney(jsonObject.getDouble("total_fee"));
	            history.setUpdateDate(jsonObject.getString("pay_time"));
	            historyDao.insertGdData(history);
	                        
	            //////////////// 企业付款  20200501  新增 
	            
	            //获取所有麦联宝返利人员 
	            List<Rebate> rebateList = this.getMlbRebatePersonList(iccid);
    			if(rebateList.size()!=0 || rebateList != null)
    			{
    				int u = 1 ;
    				for (Rebate rebate : rebateList) {
    					System.out.println("返利第" + u +"次");
    					//根据返利比例计算 返利钱数                      				
    					this.enterprisePayment(request, response, rebate, "麦联宝返利");
    					u++;
    				}
    			}
    			else
	            {
	            	System.out.println("getOrder    rebateList==null  ");
	            }
	            ///////////////
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return param;
	 }
	 
	 	/**
	 	 * 获取麦联宝返利人员
	 	 * @param iccId
	 	 * @return
	 	 */
		public List<Rebate> getMlbRebatePersonList(String iccId)
		{
			return packagesService.getMlbRebatePersonList(iccId);
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
			packageParams.put("mch_appid", ContextString.appid);
			packageParams.put("mchid", ContextString.partner);
			packageParams.put("nonce_str", nonce_str);
			packageParams.put("partner_trade_no", partner_trade_no);
			packageParams.put("openid", rebate.getOpenId());
			packageParams.put("check_name", "NO_CHECK");
			packageParams.put("amount", String.valueOf(total_fee));
			packageParams.put("desc", desc);
			packageParams.put("spbill_create_ip", String.valueOf(InetAddress.getLocalHost().getHostAddress()));
			
			RequestHandler reqHandler = new RequestHandler(request,response);
			reqHandler.init(ContextString.appid, ContextString.appsecret, ContextString.partnerkey);
			
			String sign = reqHandler.createSign(packageParams);
			
			String xml="<xml>"+
							"<mch_appid>"+ContextString.appid+"</mch_appid>"+
							"<mchid>"+ContextString.partner+"</mchid>"+
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
	            keyStore.load(instream, ContextString.partner.toCharArray());
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
	            sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore,  ContextString.partner.toCharArray()).build();
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
		
		 @ResponseBody
		 @RequestMapping("/orderTest")
		 public String  getOrderTest(HttpServletRequest request, HttpServletResponse response , String data){
			 String param= null; 
		        try {
//		        	UnicomHistory  history = new UnicomHistory();
//		            JSONObject jsonObject = JSONObject.fromObject(data);
//		            String iccid = jsonObject.getString("iccid");
//		            history.setIccid(iccid);
//		            history.setImsi(jsonObject.getString("imsi"));
//		            history.setMoney(jsonObject.getDouble("total_fee"));
//		            history.setUpdateDate(jsonObject.getString("pay_time"));
//		            historyDao.insert(history);
		            String iccid = request.getParameter("iccid");
		            System.out.println("iccid =========="+iccid);
		            //////////////// 企业付款  20200501  新增 
		            
		            //获取所有麦联宝返利人员 
		            List<Rebate> rebateList = this.getMlbRebatePersonList(iccid);
	    			if(rebateList.size()!=0 || rebateList != null)
	    			{
	    				int u = 1 ;
	    				for (Rebate rebate : rebateList) {
	    					System.out.println("返利第" + u +"次");
	    					//根据返利比例计算 返利钱数                      				
	    					this.enterprisePayment(request, response, rebate, "麦联宝测试");
	    					u++;
	    				}
	    			}
		            ///////////////
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        return param;
		 }
		

}
