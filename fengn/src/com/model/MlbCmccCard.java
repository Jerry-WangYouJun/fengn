package com.model;

public class MlbCmccCard {
    private Integer id;

    private String guid;

    private Integer simid;

    private String sim;

    private String packagename;

    private String bootstate;

    private String expiretime;

    private String oddtime;

    private Double amountusagedata;

    private Double flowleftvalue;

    private Double monthusagedata;

    private Double totalmonthusageflow;

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

    public Integer getSimid() {
        return simid;
    }

    public void setSimid(Integer simid) {
        this.simid = simid;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim == null ? null : sim.trim();
    }

    public String getPackagename() {
        return packagename;
    }

    public void setPackagename(String packagename) {
        this.packagename = packagename == null ? null : packagename.trim();
    }

    public String getBootstate() {
        return bootstate;
    }

    public void setBootstate(String bootstate) {
        this.bootstate = bootstate == null ? null : bootstate.trim();
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

    public Double getAmountusagedata() {
        return amountusagedata;
    }

    public void setAmountusagedata(Double amountusagedata) {
        this.amountusagedata = amountusagedata;
    }

    public Double getFlowleftvalue() {
        return flowleftvalue;
    }

    public void setFlowleftvalue(Double flowleftvalue) {
        this.flowleftvalue = flowleftvalue;
    }

    public Double getMonthusagedata() {
        return monthusagedata;
    }

    public void setMonthusagedata(Double monthusagedata) {
        this.monthusagedata = monthusagedata;
    }

    public Double getTotalmonthusageflow() {
        return totalmonthusageflow;
    }

    public void setTotalmonthusageflow(Double totalmonthusageflow) {
        this.totalmonthusageflow = totalmonthusageflow;
    }
}