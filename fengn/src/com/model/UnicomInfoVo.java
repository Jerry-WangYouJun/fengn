package com.model;


public class UnicomInfoVo {
	
	private String id ;
	private String remark; // 备注
	private String  IMSI ; 
	private String sim ;//
	private String ICCID ;
	private String cardStatus ; // 卡状态
	private String gprsUsed; //使用流量
	private String gprsRest;//剩余流量
	private String monthTotalStream; //月总流量
	private String company ; //公司名	
	private String companyLevel;//公司等级
	private String withGPRSService ;
	private String packageType ; //套餐名
	private String packageDetail;// 套餐详情
	private String updateTime ; //开卡时间、修改时间
	private String orderStatus ; //充值状态（根据操作修改订单状态，判断跳转链接）
	private String deadline; // 剩余时间
	private String activeTime;
	private String expireTime;
	private String name ;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getIMSI() {
		return IMSI;
	}
	public void setIMSI(String iMSI) {
		IMSI = iMSI;
	}
	public String getICCID() {
		return ICCID;
	}
	public void setICCID(String iCCID) {
		ICCID = iCCID;
	}
	public String getCardStatus() {
		return cardStatus;
	}
	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}
	public String getGprsUsed() {
		return gprsUsed;
	}
	public void setGprsUsed(String gprsUsed) {
		this.gprsUsed = gprsUsed;
	}
	public String getGprsRest() {
		return gprsRest;
	}
	public void setGprsRest(String gprsRest) {
		this.gprsRest = gprsRest;
	}
	public String getMonthTotalStream() {
		return monthTotalStream;
	}
	public void setMonthTotalStream(String monthTotalStream) {
		this.monthTotalStream = monthTotalStream;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCompanyLevel() {
		return companyLevel;
	}
	public void setCompanyLevel(String companyLevel) {
		this.companyLevel = companyLevel;
	}
	public String getWithGPRSService() {
		return withGPRSService;
	}
	public void setWithGPRSService(String withGPRSService) {
		this.withGPRSService = withGPRSService;
	}
	public String getPackageType() {
		return packageType;
	}
	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}
	public String getPackageDetail() {
		return packageDetail;
	}
	public void setPackageDetail(String packageDetail) {
		this.packageDetail = packageDetail;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public String getSim() {
		return sim;
	}
	public void setSim(String sim) {
		this.sim = sim;
	}
	public String getActiveTime() {
		return activeTime;
	}
	public void setActiveTime(String activeTime) {
		this.activeTime = activeTime;
	}
	public String getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}
	
}
