package com.model;

public class MlbUnicomCard {
    private Integer id;

    private String guid; //iccid
    
    private String simid;

    private String packagename; //套餐类型

    private String simstate;// 卡状态

    private String expiretime; //到期日期

    private String oddtime; //剩余服务

    private Double dayusagedata;//

    private Double amountusagedata;//剩余流量gprsRest

    private Double totalmonthusageflow;//历史用量gprsUsed
    
    private String sim ;//SIM卡号
    
    private String holdname;//所属用户
    
    private  String lastactivetime;//最近激活
    
    private String flowlefttime;//最近同步

    private String outWarehouseDate;//出库日期,暂留
    
    private String imsi ; 
    
    private String remark;

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

	public String getSim() {
		return sim;
	}

	public void setSim(String sim) {
		this.sim = sim;
	}

	public String getHoldname() {
		return holdname;
	}

	public void setHoldname(String holdname) {
		this.holdname = holdname;
	}

	public String getLastactivetime() {
		return lastactivetime;
	}

	public void setLastactivetime(String lastactivetime) {
		this.lastactivetime = lastactivetime;
	}

	public String getFlowlefttime() {
		return flowlefttime;
	}

	public void setFlowlefttime(String flowlefttime) {
		this.flowlefttime = flowlefttime;
	}

	public String getOutWarehouseDate() {
		return outWarehouseDate;
	}

	public void setOutWarehouseDate(String outWarehouseDate) {
		this.outWarehouseDate = outWarehouseDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSimid() {
		return simid;
	}

	public void setSimid(String simid) {
		this.simid = simid;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
    
    
}