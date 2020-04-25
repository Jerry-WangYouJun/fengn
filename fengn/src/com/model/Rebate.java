package com.model;

import java.io.Serializable;

public class Rebate  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String openId;			//用户的openId
	private String iccId;			//卡号
	private double amount;			//返利金额 (通过sql    售价- 自己的成本价)
	private int packageId;			//套餐id
	private int agentId;			//代理商id
	private int parentAgentId;		//父级代理商id
	private double paccost;			//成本价
	private double pacchildcost;	//子级代理商成本价
	private double pacrenew;		//售价
	
	public int getAgentId() {
		return agentId;
	}
	public void setAgentId(int agentId) {
		this.agentId = agentId;
	}
	public int getParentAgentId() {
		return parentAgentId;
	}
	public void setParentAgentId(int parentAgentId) {
		this.parentAgentId = parentAgentId;
	}
	public double getPaccost() {
		return paccost;
	}
	public void setPaccost(double paccost) {
		this.paccost = paccost;
	}
	public double getPacchildcost() {
		return pacchildcost;
	}
	public void setPacchildcost(double pacchildcost) {
		this.pacchildcost = pacchildcost;
	}
	public double getPacrenew() {
		return pacrenew;
	}
	public void setPacrenew(double pacrenew) {
		this.pacrenew = pacrenew;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getIccId() {
		return iccId;
	}
	public void setIccId(String iccId) {
		this.iccId = iccId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public int getPackageId() {
		return packageId;
	}
	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}
	
	

}
