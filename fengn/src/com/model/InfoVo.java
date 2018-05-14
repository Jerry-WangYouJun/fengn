package com.model;

import java.util.List;

public class InfoVo {
	
	private String code;
	private String name;
	private String date;
	private String money;
	
	private String id ;
	private String cardCode;
	private String remark;
	private String  IMSI ; 
	private String ICCID ;
	private String userStatus  ; 
	private String cardStatus ;
	private String gprsUsed;
	private String messageUsed;
	private String openDate;
	private String withMessageService ;
	private String withGPRSService ;
	private String packageType ;
	private String monthTotalStream;
	private String updateTime ;
	private String status ;
	private String dateBegin;
	private String dateEnd ;
	private CardDetail  detail;
	private List<InfoPackage> packageList; 
	private List<History> history ;
	private Long restDay ;
	private String apiCode;
	private String flag ;
	private String orderNo;
	
	
	public Long getRestDay() {
		return restDay;
	}
	public void setRestDay(Long restDay) {
		this.restDay = restDay;
	}
	public List<InfoPackage> getPackageList() {
		return packageList;
	}
	public void setPackageList(List<InfoPackage> packageList) {
		this.packageList = packageList;
	}
	public List<History> getHistory() {
		return history;
	}
	public void setHistory(List<History> history) {
		this.history = history;
	}
	public CardDetail getDetail() {
		return detail;
	}
	public void setDetail(CardDetail detail) {
		this.detail = detail;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
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
	public String getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
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
	public String getMessageUsed() {
		return messageUsed;
	}
	public void setMessageUsed(String messageUsed) {
		this.messageUsed = messageUsed;
	}
	public String getOpenDate() {
		return openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public String getWithMessageService() {
		return withMessageService;
	}
	public void setWithMessageService(String withMessageService) {
		this.withMessageService = withMessageService;
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
	public String getMonthTotalStream() {
		return monthTotalStream;
	}
	public void setMonthTotalStream(String monthTotalStream) {
		this.monthTotalStream = monthTotalStream;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDateBegin() {
		return dateBegin;
	}
	public void setDateBegin(String dateBegin) {
		this.dateBegin = dateBegin;
	}
	public String getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}
	public String getApiCode() {
		return apiCode;
	}
	public void setApiCode(String apiCode) {
		this.apiCode = apiCode;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
}
