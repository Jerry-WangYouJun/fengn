package com.cmoit.model;

public class CmoitCardAgent {
    private Integer id;

    private String iccid;

    private Integer agentid;

    private Integer pacid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIccid() {
		return iccid;
	}

	public void setIccid(String iccid) {
		this.iccid = iccid;
	}

	public Integer getAgentid() {
        return agentid;
    }

    public void setAgentid(Integer agentid) {
        this.agentid = agentid;
    }

    public Integer getPacid() {
        return pacid;
    }

    public void setPacid(Integer pacid) {
        this.pacid = pacid;
    }
}