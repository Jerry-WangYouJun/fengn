package com.cmoit.model;

public class CmoitCard {
    private Integer id;

    private String msisdn;

    private String iccid;

    private String imsi;

    private String userstatus;

    private String cardstatus;

    private String balance;

    private String callused;

    private String callsum;

    private String msgused;

    private String msgsum;

    private String gprsused;

    private String gprssum;

    private String opentime;

    private String activetime;

    private String updatetime;
    
    private String name ;
    
    private String discrip ;
    
    private Integer pacid ;
    
    private String endtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn == null ? null : msisdn.trim();
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid == null ? null : iccid.trim();
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi == null ? null : imsi.trim();
    }

    public String getUserstatus() {
        return userstatus;
    }

    public void setUserstatus(String userstatus) {
        this.userstatus = userstatus == null ? null : userstatus.trim();
    }

    public String getCardstatus() {
        return cardstatus;
    }

    public void setCardstatus(String cardstatus) {
        this.cardstatus = cardstatus == null ? null : cardstatus.trim();
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance == null ? null : balance.trim();
    }

    public String getCallused() {
        return callused;
    }

    public void setCallused(String callused) {
        this.callused = callused == null ? null : callused.trim();
    }

    public String getCallsum() {
        return callsum;
    }

    public void setCallsum(String callsum) {
        this.callsum = callsum == null ? null : callsum.trim();
    }

    public String getMsgused() {
        return msgused;
    }

    public void setMsgused(String msgused) {
        this.msgused = msgused == null ? null : msgused.trim();
    }

    public String getMsgsum() {
        return msgsum;
    }

    public void setMsgsum(String msgsum) {
        this.msgsum = msgsum == null ? null : msgsum.trim();
    }

    public String getGprsused() {
        return gprsused;
    }

    public void setGprsused(String gprsused) {
        this.gprsused = gprsused == null ? null : gprsused.trim();
    }

    public String getGprssum() {
        return gprssum;
    }

    public void setGprssum(String gprssum) {
        this.gprssum = gprssum == null ? null : gprssum.trim();
    }

    public String getOpentime() {
        return opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime == null ? null : opentime.trim();
    }

    public String getActivetime() {
        return activetime;
    }

    public void setActivetime(String activetime) {
        this.activetime = activetime == null ? null : activetime.trim();
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime == null ? null : updatetime.trim();
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDiscrip() {
		return discrip;
	}

	public void setDiscrip(String discrip) {
		this.discrip = discrip;
	}

	public Integer getPacid() {
		return pacid;
	}

	public void setPacid(Integer pacid) {
		this.pacid = pacid;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
    
}