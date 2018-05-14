package com.pay.config;

public class WxPayConfig {
	
	/**
	 * 公众账号ID
	 */
	public static String appid = "wx6bc15833d2f20323";
	
	/**
	 * 
	 */
	public static String appsecret = "a85b9eb571de303f6e2bae33c1c608e9";
	
	/**
	 * 商户号（mch_id）
	 */
	public static String partner = "1448494802";
	
	public static String partnerkey = "zhangxinzhao1980zxcvzhangxinzhao";
	
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
