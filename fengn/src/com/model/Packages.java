package com.model;

public class Packages {
	 private Integer id ;
	 private String typename;
	 private String discrip;
	 private Double cost ;
	 private Double renew;
	 private String remark;
	 private Double childcost;

	 //额外属性
	 private String agentId;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public Double getRenew() {
		return renew;
	}
	public void setRenew(Double renew) {
		this.renew = renew;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getChildcost() {
		return childcost;
	}

	public void setChildcost(Double childcost) {
		this.childcost = childcost;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
}
