package com.model;

public class MlbUnicomCard {
    private Integer id;

    private String guid; //iccid

    private String packagename; //packageType

    private String simstate;//cardStatus

    private String expiretime; //

    private String oddtime; //deadline

    private Double dayusagedata;//

    private Double amountusagedata;//剩余流量gprsRest

    private Double totalmonthusageflow;//历史用量gprsUsed

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid == null ? null : guid.trim();
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename == null ? null : packagename.trim();
    }

    public String getSimstate() {
        return simstate;
    }

    public void setSimstate(String simstate) {
        this.simstate = simstate == null ? null : simstate.trim();
    }

    public String getExpiretime() {
        return expiretime;
    }

    public void setExpiretime(String expiretime) {
        this.expiretime = expiretime == null ? null : expiretime.trim();
    }

    public String getOddtime() {
        return oddtime;
    }

    public void setOddtime(String oddtime) {
        this.oddtime = oddtime == null ? null : oddtime.trim();
    }

    public Double getDayusagedata() {
        return dayusagedata;
    }

    public void setDayusagedata(Double dayusagedata) {
        this.dayusagedata = dayusagedata;
    }

    public Double getAmountusagedata() {
        return amountusagedata;
    }

    public void setAmountusagedata(Double amountusagedata) {
        this.amountusagedata = amountusagedata;
    }

    public Double getTotalmonthusageflow() {
        return totalmonthusageflow;
    }

    public void setTotalmonthusageflow(Double totalmonthusageflow) {
        this.totalmonthusageflow = totalmonthusageflow;
    }
}