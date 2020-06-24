package com.common;

public class ContextString {
	 static public final String ROLE_ADMIN = "1";
	 static  public final String ROLE_AGENT = "2";
	 static public final String ROLE_ADMIN_UNICOM = "3";
	 static public final String ROLE_AGENT_UNICOM = "4";
	 
	 /**
	  * 以下为较早版本的请求参数，不必修改；若删除 请删除关联的代码并测试
	  */
	 static public final String
	 URL_UNICOM_SEARCH = "https://www.m-m10086.com/api/SimListFire/Search";
	 static public final String 
	 URL_UNICOM_BIND = "https://www.m-m10086.com/api/SimListFire/Binding";
	 static public final String 
	 URL_CMCC_SEARCH = "https://www.m-m10086.com/api/YDSimListFire/Search";
	 static public final String 
	 URL_CMCC_BIND ="https://www.m-m10086.com/api/YDSimListFire/Binding";
	 static public final String 
	 URL_RENEW_C = "https://www.m-m10086.com/api/NewReport/RenewalsOrder?holdId=12896&p=1&psize=sum&payee=&source=&PayState=1,2&RenewalsState=&packageType=&oldPackageType=&timeType=2&stime=timeStart+00%3A00%3A00&etime=timeEnd+23%3A59%3A59&order=&id=&minamonth=12&access=&simState=-1&commercialTenant=&batchType=iccid&groupHoldId=0";
	 static public final String 
	 URL_RENEW_B = "https://www.m-m10086.com/api/NewReport/WebRenewalsOrder?p=1&timeType=2&serviceState=0&holdid=12896&sdate=2018-10-28+00%3A00%3A00&edate=2018-11-25+00%3A00%3A00&order=&id=&psize=25&payee=&comeFrom=&licenseHoldId=0&renewalsState=&sourceType=&packageType=&batchType=iccid&batchValues=";

	 
	 /**
	  * 当前使用麦联宝移动卡的接口信息  , 
	  */
	static public final String  MLB_URL_NEW_CHECK = "http://open.iot1860.com/open/Help/CheckTerminal";
	static public final String  MLB_URL_NEW_RENEW ="http://open.iot1860.com/open/cmcc/RenewalsPackageList";
	static public final String  MLB_URL_NEW_QUERY ="http://open.iot1860.com/open/cmcc/TerminalDetail";
	//TODO
	/**
	 * 麦联宝接口文档中，验证用户信息的账户ID ，请于麦联宝方联系或者查找对方提供的接口文档中是否有 
	 */
	static public final String MLB_USER_ID = "131416124";
	//TODO
	/**
	 * 麦联宝接口验证信息中的 KEY 
	 */
	static public final String MLB_KEY = "DF459BBED5D2E9B024B7AC6C5D80DC5B";
	
	
	/**
	 * 平度移动接口 , 用户验证用的 APPID
	 */
	public static final String CMOIT_APPID = "QIQNEFAB7K1QDX";
	public static final String CMOIT_PASSWORD = "9jYT2le2T";
	
	
	
	/**
	 * 公众账号ID
	 */
	public static String appid = "wx6bc15833d2f20323";
	
	/**
	 * 公众号appsecret
	 */
	public static String appsecret = "2c6d540bc1296a22c90be3510f3428f8";
		
	/**
	 * 商户号（mch_id）
	 */
	//public static String partner = "1448494802";
	public static String partner = "1567849821";
	/**
	 * 商户key
	 */
	public static String partnerkey = "7Jz41J4G1T6OuExYm89uTY41GoJ6aj8o";
	/**
	 * 交易类型
	 */
	public static String trade_type = "JSAPI";
	
	public static String signType = "MD5"; 
	
	/**
	 * 固定套餐金额
	 * @param args
	 */
	public static  Integer money = 18 ;
	
	/**
	 * 固定套餐包id
	 * @param args
	 */
	public  static  int packageId = 1 ;
}
