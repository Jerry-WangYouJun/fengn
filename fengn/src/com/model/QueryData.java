package com.model;


public class QueryData {
	//代理商套餐类型
	  private String type ;
	 //代理商iccid开始卡号
	  private String iccidStart ; 
	  //代理商iccid结束卡号
	  private String iccidEnd ;
	  //
	  private  String agentid;
	  //代理商名称
	  private String agentName ;
	  //代理商编号
	  private String agentCode;
	  //用户名
	  private String userNo ; 
	  //用户名称
	  private String userName ;
	  //角色
	  private String roleId;
	  private String userId ;
	  private String groupId;
	  
	  private String dateStart ; 
	  private String dateEnd ;
	  private Integer timeType;
	  
	  private String typename;
	  private String discrip;
	  private String pacId;
	  private String moveFlag;//卡信息移动时对联通、移动代理商的判断标志
	  private String moveAgent;//卡信息移动时选中的agentID
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getIccidStart() {
		return iccidStart;
	}
	public void setIccidStart(String iccidStart) {
		this.iccidStart = iccidStart;
	}
	public String getIccidEnd() {
		return iccidEnd;
	}
	public void setIccidEnd(String iccidEnd) {
		this.iccidEnd = iccidEnd;
	}
	public String getAgentid() {
		return agentid;
	}
	public void setAgentid(String agentid) {
		this.agentid = agentid;
	}
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public String getDateStart() {
		return dateStart;
	}
	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}
	public String getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}
	public Integer getTimeType() {
		if(timeType == null ) {
			  return 0 ;
		}
		return timeType;
	}
	public void setTimeType(Integer timeType) {
		this.timeType = timeType;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getDiscrip() {
		return discrip;
	}
	public void setDiscrip(String discrip) {
		this.discrip = discrip;
	}
	public String getPacId() {
		return pacId;
	}
	public void setPacId(String pacId) {
		this.pacId = pacId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getMoveFlag() {
		return moveFlag;
	}
	public void setMoveFlag(String moveFlag) {
		this.moveFlag = moveFlag;
	}
	public String getMoveAgent() {
		return moveAgent;
	}
	public void setMoveAgent(String moveAgent) {
		this.moveAgent = moveAgent;
	} 
	
}
