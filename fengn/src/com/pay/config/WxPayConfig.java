package com.pay.config;

public class WxPayConfig {
	
	/**
	 * 公众账号ID
	 */
	public static String appid = "wx6bc15833d2f20323";
	
	/**
	 * 
	 */
	public static String appsecret = "2c6d540bc1296a22c90be3510f3428f8";
		
	/**
	 * 商户号（mch_id）
	 */
	//public static String partner = "1448494802";
	public static String partner = "1567849821";
	//1567849821
	//public static String partnerkey = "zhangxinzhao1980zxcvzhangxinzhao";
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
