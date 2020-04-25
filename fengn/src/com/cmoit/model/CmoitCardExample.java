package com.cmoit.model;

import java.util.ArrayList;
import java.util.List;

public class CmoitCardExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CmoitCardExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andMsisdnIsNull() {
            addCriterion("msisdn is null");
            return (Criteria) this;
        }

        public Criteria andMsisdnIsNotNull() {
            addCriterion("msisdn is not null");
            return (Criteria) this;
        }

        public Criteria andMsisdnEqualTo(String value) {
            addCriterion("msisdn =", value, "msisdn");
            return (Criteria) this;
        }

        public Criteria andMsisdnNotEqualTo(String value) {
            addCriterion("msisdn <>", value, "msisdn");
            return (Criteria) this;
        }

        public Criteria andMsisdnGreaterThan(String value) {
            addCriterion("msisdn >", value, "msisdn");
            return (Criteria) this;
        }

        public Criteria andMsisdnGreaterThanOrEqualTo(String value) {
            addCriterion("msisdn >=", value, "msisdn");
            return (Criteria) this;
        }

        public Criteria andMsisdnLessThan(String value) {
            addCriterion("msisdn <", value, "msisdn");
            return (Criteria) this;
        }

        public Criteria andMsisdnLessThanOrEqualTo(String value) {
            addCriterion("msisdn <=", value, "msisdn");
            return (Criteria) this;
        }

        public Criteria andMsisdnLike(String value) {
            addCriterion("msisdn like", value, "msisdn");
            return (Criteria) this;
        }

        public Criteria andMsisdnNotLike(String value) {
            addCriterion("msisdn not like", value, "msisdn");
            return (Criteria) this;
        }

        public Criteria andMsisdnIn(List<String> values) {
            addCriterion("msisdn in", values, "msisdn");
            return (Criteria) this;
        }

        public Criteria andMsisdnNotIn(List<String> values) {
            addCriterion("msisdn not in", values, "msisdn");
            return (Criteria) this;
        }

        public Criteria andMsisdnBetween(String value1, String value2) {
            addCriterion("msisdn between", value1, value2, "msisdn");
            return (Criteria) this;
        }

        public Criteria andMsisdnNotBetween(String value1, String value2) {
            addCriterion("msisdn not between", value1, value2, "msisdn");
            return (Criteria) this;
        }

        public Criteria andIccidIsNull() {
            addCriterion("iccid is null");
            return (Criteria) this;
        }

        public Criteria andIccidIsNotNull() {
            addCriterion("iccid is not null");
            return (Criteria) this;
        }

        public Criteria andIccidEqualTo(String value) {
            addCriterion("iccid =", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidNotEqualTo(String value) {
            addCriterion("iccid <>", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidGreaterThan(String value) {
            addCriterion("iccid >", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidGreaterThanOrEqualTo(String value) {
            addCriterion("iccid >=", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidLessThan(String value) {
            addCriterion("iccid <", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidLessThanOrEqualTo(String value) {
            addCriterion("iccid <=", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidLike(String value) {
            addCriterion("iccid like", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidNotLike(String value) {
            addCriterion("iccid not like", value, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidIn(List<String> values) {
            addCriterion("iccid in", values, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidNotIn(List<String> values) {
            addCriterion("iccid not in", values, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidBetween(String value1, String value2) {
            addCriterion("iccid between", value1, value2, "iccid");
            return (Criteria) this;
        }

        public Criteria andIccidNotBetween(String value1, String value2) {
            addCriterion("iccid not between", value1, value2, "iccid");
            return (Criteria) this;
        }

        public Criteria andImsiIsNull() {
            addCriterion("imsi is null");
            return (Criteria) this;
        }

        public Criteria andImsiIsNotNull() {
            addCriterion("imsi is not null");
            return (Criteria) this;
        }

        public Criteria andImsiEqualTo(String value) {
            addCriterion("imsi =", value, "imsi");
            return (Criteria) this;
        }

        public Criteria andImsiNotEqualTo(String value) {
            addCriterion("imsi <>", value, "imsi");
            return (Criteria) this;
        }

        public Criteria andImsiGreaterThan(String value) {
            addCriterion("imsi >", value, "imsi");
            return (Criteria) this;
        }

        public Criteria andImsiGreaterThanOrEqualTo(String value) {
            addCriterion("imsi >=", value, "imsi");
            return (Criteria) this;
        }

        public Criteria andImsiLessThan(String value) {
            addCriterion("imsi <", value, "imsi");
            return (Criteria) this;
        }

        public Criteria andImsiLessThanOrEqualTo(String value) {
            addCriterion("imsi <=", value, "imsi");
            return (Criteria) this;
        }

        public Criteria andImsiLike(String value) {
            addCriterion("imsi like", value, "imsi");
            return (Criteria) this;
        }

        public Criteria andImsiNotLike(String value) {
            addCriterion("imsi not like", value, "imsi");
            return (Criteria) this;
        }

        public Criteria andImsiIn(List<String> values) {
            addCriterion("imsi in", values, "imsi");
            return (Criteria) this;
        }

        public Criteria andImsiNotIn(List<String> values) {
            addCriterion("imsi not in", values, "imsi");
            return (Criteria) this;
        }

        public Criteria andImsiBetween(String value1, String value2) {
            addCriterion("imsi between", value1, value2, "imsi");
            return (Criteria) this;
        }

        public Criteria andImsiNotBetween(String value1, String value2) {
            addCriterion("imsi not between", value1, value2, "imsi");
            return (Criteria) this;
        }

        public Criteria andUserstatusIsNull() {
            addCriterion("userstatus is null");
            return (Criteria) this;
        }

        public Criteria andUserstatusIsNotNull() {
            addCriterion("userstatus is not null");
            return (Criteria) this;
        }

        public Criteria andUserstatusEqualTo(String value) {
            addCriterion("userstatus =", value, "userstatus");
            return (Criteria) this;
        }

        public Criteria andUserstatusNotEqualTo(String value) {
            addCriterion("userstatus <>", value, "userstatus");
            return (Criteria) this;
        }

        public Criteria andUserstatusGreaterThan(String value) {
            addCriterion("userstatus >", value, "userstatus");
            return (Criteria) this;
        }

        public Criteria andUserstatusGreaterThanOrEqualTo(String value) {
            addCriterion("userstatus >=", value, "userstatus");
            return (Criteria) this;
        }

        public Criteria andUserstatusLessThan(String value) {
            addCriterion("userstatus <", value, "userstatus");
            return (Criteria) this;
        }

        public Criteria andUserstatusLessThanOrEqualTo(String value) {
            addCriterion("userstatus <=", value, "userstatus");
            return (Criteria) this;
        }

        public Criteria andUserstatusLike(String value) {
            addCriterion("userstatus like", value, "userstatus");
            return (Criteria) this;
        }

        public Criteria andUserstatusNotLike(String value) {
            addCriterion("userstatus not like", value, "userstatus");
            return (Criteria) this;
        }

        public Criteria andUserstatusIn(List<String> values) {
            addCriterion("userstatus in", values, "userstatus");
            return (Criteria) this;
        }

        public Criteria andUserstatusNotIn(List<String> values) {
            addCriterion("userstatus not in", values, "userstatus");
            return (Criteria) this;
        }

        public Criteria andUserstatusBetween(String value1, String value2) {
            addCriterion("userstatus between", value1, value2, "userstatus");
            return (Criteria) this;
        }

        public Criteria andUserstatusNotBetween(String value1, String value2) {
            addCriterion("userstatus not between", value1, value2, "userstatus");
            return (Criteria) this;
        }

        public Criteria andCardstatusIsNull() {
            addCriterion("cardstatus is null");
            return (Criteria) this;
        }

        public Criteria andCardstatusIsNotNull() {
            addCriterion("cardstatus is not null");
            return (Criteria) this;
        }

        public Criteria andCardstatusEqualTo(String value) {
            addCriterion("cardstatus =", value, "cardstatus");
            return (Criteria) this;
        }

        public Criteria andCardstatusNotEqualTo(String value) {
            addCriterion("cardstatus <>", value, "cardstatus");
            return (Criteria) this;
        }

        public Criteria andCardstatusGreaterThan(String value) {
            addCriterion("cardstatus >", value, "cardstatus");
            return (Criteria) this;
        }

        public Criteria andCardstatusGreaterThanOrEqualTo(String value) {
            addCriterion("cardstatus >=", value, "cardstatus");
            return (Criteria) this;
        }

        public Criteria andCardstatusLessThan(String value) {
            addCriterion("cardstatus <", value, "cardstatus");
            return (Criteria) this;
        }

        public Criteria andCardstatusLessThanOrEqualTo(String value) {
            addCriterion("cardstatus <=", value, "cardstatus");
            return (Criteria) this;
        }

        public Criteria andCardstatusLike(String value) {
            addCriterion("cardstatus like", value, "cardstatus");
            return (Criteria) this;
        }

        public Criteria andCardstatusNotLike(String value) {
            addCriterion("cardstatus not like", value, "cardstatus");
            return (Criteria) this;
        }

        public Criteria andCardstatusIn(List<String> values) {
            addCriterion("cardstatus in", values, "cardstatus");
            return (Criteria) this;
        }

        public Criteria andCardstatusNotIn(List<String> values) {
            addCriterion("cardstatus not in", values, "cardstatus");
            return (Criteria) this;
        }

        public Criteria andCardstatusBetween(String value1, String value2) {
            addCriterion("cardstatus between", value1, value2, "cardstatus");
            return (Criteria) this;
        }

        public Criteria andCardstatusNotBetween(String value1, String value2) {
            addCriterion("cardstatus not between", value1, value2, "cardstatus");
            return (Criteria) this;
        }

        public Criteria andBalanceIsNull() {
            addCriterion("balance is null");
            return (Criteria) this;
        }

        public Criteria andBalanceIsNotNull() {
            addCriterion("balance is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceEqualTo(String value) {
            addCriterion("balance =", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotEqualTo(String value) {
            addCriterion("balance <>", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceGreaterThan(String value) {
            addCriterion("balance >", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceGreaterThanOrEqualTo(String value) {
            addCriterion("balance >=", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceLessThan(String value) {
            addCriterion("balance <", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceLessThanOrEqualTo(String value) {
            addCriterion("balance <=", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceLike(String value) {
            addCriterion("balance like", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotLike(String value) {
            addCriterion("balance not like", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceIn(List<String> values) {
            addCriterion("balance in", values, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotIn(List<String> values) {
            addCriterion("balance not in", values, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceBetween(String value1, String value2) {
            addCriterion("balance between", value1, value2, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotBetween(String value1, String value2) {
            addCriterion("balance not between", value1, value2, "balance");
            return (Criteria) this;
        }

        public Criteria andCallusedIsNull() {
            addCriterion("callused is null");
            return (Criteria) this;
        }

        public Criteria andCallusedIsNotNull() {
            addCriterion("callused is not null");
            return (Criteria) this;
        }

        public Criteria andCallusedEqualTo(String value) {
            addCriterion("callused =", value, "callused");
            return (Criteria) this;
        }

        public Criteria andCallusedNotEqualTo(String value) {
            addCriterion("callused <>", value, "callused");
            return (Criteria) this;
        }

        public Criteria andCallusedGreaterThan(String value) {
            addCriterion("callused >", value, "callused");
            return (Criteria) this;
        }

        public Criteria andCallusedGreaterThanOrEqualTo(String value) {
            addCriterion("callused >=", value, "callused");
            return (Criteria) this;
        }

        public Criteria andCallusedLessThan(String value) {
            addCriterion("callused <", value, "callused");
            return (Criteria) this;
        }

        public Criteria andCallusedLessThanOrEqualTo(String value) {
            addCriterion("callused <=", value, "callused");
            return (Criteria) this;
        }

        public Criteria andCallusedLike(String value) {
            addCriterion("callused like", value, "callused");
            return (Criteria) this;
        }

        public Criteria andCallusedNotLike(String value) {
            addCriterion("callused not like", value, "callused");
            return (Criteria) this;
        }

        public Criteria andCallusedIn(List<String> values) {
            addCriterion("callused in", values, "callused");
            return (Criteria) this;
        }

        public Criteria andCallusedNotIn(List<String> values) {
            addCriterion("callused not in", values, "callused");
            return (Criteria) this;
        }

        public Criteria andCallusedBetween(String value1, String value2) {
            addCriterion("callused between", value1, value2, "callused");
            return (Criteria) this;
        }

        public Criteria andCallusedNotBetween(String value1, String value2) {
            addCriterion("callused not between", value1, value2, "callused");
            return (Criteria) this;
        }

        public Criteria andCallsumIsNull() {
            addCriterion("callsum is null");
            return (Criteria) this;
        }

        public Criteria andCallsumIsNotNull() {
            addCriterion("callsum is not null");
            return (Criteria) this;
        }

        public Criteria andCallsumEqualTo(String value) {
            addCriterion("callsum =", value, "callsum");
            return (Criteria) this;
        }

        public Criteria andCallsumNotEqualTo(String value) {
            addCriterion("callsum <>", value, "callsum");
            return (Criteria) this;
        }

        public Criteria andCallsumGreaterThan(String value) {
            addCriterion("callsum >", value, "callsum");
            return (Criteria) this;
        }

        public Criteria andCallsumGreaterThanOrEqualTo(String value) {
            addCriterion("callsum >=", value, "callsum");
            return (Criteria) this;
        }

        public Criteria andCallsumLessThan(String value) {
            addCriterion("callsum <", value, "callsum");
            return (Criteria) this;
        }

        public Criteria andCallsumLessThanOrEqualTo(String value) {
            addCriterion("callsum <=", value, "callsum");
            return (Criteria) this;
        }

        public Criteria andCallsumLike(String value) {
            addCriterion("callsum like", value, "callsum");
            return (Criteria) this;
        }

        public Criteria andCallsumNotLike(String value) {
            addCriterion("callsum not like", value, "callsum");
            return (Criteria) this;
        }

        public Criteria andCallsumIn(List<String> values) {
            addCriterion("callsum in", values, "callsum");
            return (Criteria) this;
        }

        public Criteria andCallsumNotIn(List<String> values) {
            addCriterion("callsum not in", values, "callsum");
            return (Criteria) this;
        }

        public Criteria andCallsumBetween(String value1, String value2) {
            addCriterion("callsum between", value1, value2, "callsum");
            return (Criteria) this;
        }

        public Criteria andCallsumNotBetween(String value1, String value2) {
            addCriterion("callsum not between", value1, value2, "callsum");
            return (Criteria) this;
        }

        public Criteria andMsgusedIsNull() {
            addCriterion("msgused is null");
            return (Criteria) this;
        }

        public Criteria andMsgusedIsNotNull() {
            addCriterion("msgused is not null");
            return (Criteria) this;
        }

        public Criteria andMsgusedEqualTo(String value) {
            addCriterion("msgused =", value, "msgused");
            return (Criteria) this;
        }

        public Criteria andMsgusedNotEqualTo(String value) {
            addCriterion("msgused <>", value, "msgused");
            return (Criteria) this;
        }

        public Criteria andMsgusedGreaterThan(String value) {
            addCriterion("msgused >", value, "msgused");
            return (Criteria) this;
        }

        public Criteria andMsgusedGreaterThanOrEqualTo(String value) {
            addCriterion("msgused >=", value, "msgused");
            return (Criteria) this;
        }

        public Criteria andMsgusedLessThan(String value) {
            addCriterion("msgused <", value, "msgused");
            return (Criteria) this;
        }

        public Criteria andMsgusedLessThanOrEqualTo(String value) {
            addCriterion("msgused <=", value, "msgused");
            return (Criteria) this;
        }

        public Criteria andMsgusedLike(String value) {
            addCriterion("msgused like", value, "msgused");
            return (Criteria) this;
        }

        public Criteria andMsgusedNotLike(String value) {
            addCriterion("msgused not like", value, "msgused");
            return (Criteria) this;
        }

        public Criteria andMsgusedIn(List<String> values) {
            addCriterion("msgused in", values, "msgused");
            return (Criteria) this;
        }

        public Criteria andMsgusedNotIn(List<String> values) {
            addCriterion("msgused not in", values, "msgused");
            return (Criteria) this;
        }

        public Criteria andMsgusedBetween(String value1, String value2) {
            addCriterion("msgused between", value1, value2, "msgused");
            return (Criteria) this;
        }

        public Criteria andMsgusedNotBetween(String value1, String value2) {
            addCriterion("msgused not between", value1, value2, "msgused");
            return (Criteria) this;
        }

        public Criteria andMsgsumIsNull() {
            addCriterion("msgsum is null");
            return (Criteria) this;
        }

        public Criteria andMsgsumIsNotNull() {
            addCriterion("msgsum is not null");
            return (Criteria) this;
        }

        public Criteria andMsgsumEqualTo(String value) {
            addCriterion("msgsum =", value, "msgsum");
            return (Criteria) this;
        }

        public Criteria andMsgsumNotEqualTo(String value) {
            addCriterion("msgsum <>", value, "msgsum");
            return (Criteria) this;
        }

        public Criteria andMsgsumGreaterThan(String value) {
            addCriterion("msgsum >", value, "msgsum");
            return (Criteria) this;
        }

        public Criteria andMsgsumGreaterThanOrEqualTo(String value) {
            addCriterion("msgsum >=", value, "msgsum");
            return (Criteria) this;
        }

        public Criteria andMsgsumLessThan(String value) {
            addCriterion("msgsum <", value, "msgsum");
            return (Criteria) this;
        }

        public Criteria andMsgsumLessThanOrEqualTo(String value) {
            addCriterion("msgsum <=", value, "msgsum");
            return (Criteria) this;
        }

        public Criteria andMsgsumLike(String value) {
            addCriterion("msgsum like", value, "msgsum");
            return (Criteria) this;
        }

        public Criteria andMsgsumNotLike(String value) {
            addCriterion("msgsum not like", value, "msgsum");
            return (Criteria) this;
        }

        public Criteria andMsgsumIn(List<String> values) {
            addCriterion("msgsum in", values, "msgsum");
            return (Criteria) this;
        }

        public Criteria andMsgsumNotIn(List<String> values) {
            addCriterion("msgsum not in", values, "msgsum");
            return (Criteria) this;
        }

        public Criteria andMsgsumBetween(String value1, String value2) {
            addCriterion("msgsum between", value1, value2, "msgsum");
            return (Criteria) this;
        }

        public Criteria andMsgsumNotBetween(String value1, String value2) {
            addCriterion("msgsum not between", value1, value2, "msgsum");
            return (Criteria) this;
        }

        public Criteria andGprsusedIsNull() {
            addCriterion("gprsused is null");
            return (Criteria) this;
        }

        public Criteria andGprsusedIsNotNull() {
            addCriterion("gprsused is not null");
            return (Criteria) this;
        }

        public Criteria andGprsusedEqualTo(String value) {
            addCriterion("gprsused =", value, "gprsused");
            return (Criteria) this;
        }

        public Criteria andGprsusedNotEqualTo(String value) {
            addCriterion("gprsused <>", value, "gprsused");
            return (Criteria) this;
        }

        public Criteria andGprsusedGreaterThan(String value) {
            addCriterion("gprsused >", value, "gprsused");
            return (Criteria) this;
        }

        public Criteria andGprsusedGreaterThanOrEqualTo(String value) {
            addCriterion("gprsused >=", value, "gprsused");
            return (Criteria) this;
        }

        public Criteria andGprsusedLessThan(String value) {
            addCriterion("gprsused <", value, "gprsused");
            return (Criteria) this;
        }

        public Criteria andGprsusedLessThanOrEqualTo(String value) {
            addCriterion("gprsused <=", value, "gprsused");
            return (Criteria) this;
        }

        public Criteria andGprsusedLike(String value) {
            addCriterion("gprsused like", value, "gprsused");
            return (Criteria) this;
        }

        public Criteria andGprsusedNotLike(String value) {
            addCriterion("gprsused not like", value, "gprsused");
            return (Criteria) this;
        }

        public Criteria andGprsusedIn(List<String> values) {
            addCriterion("gprsused in", values, "gprsused");
            return (Criteria) this;
        }

        public Criteria andGprsusedNotIn(List<String> values) {
            addCriterion("gprsused not in", values, "gprsused");
            return (Criteria) this;
        }

        public Criteria andGprsusedBetween(String value1, String value2) {
            addCriterion("gprsused between", value1, value2, "gprsused");
            return (Criteria) this;
        }

        public Criteria andGprsusedNotBetween(String value1, String value2) {
            addCriterion("gprsused not between", value1, value2, "gprsused");
            return (Criteria) this;
        }

        public Criteria andGprssumIsNull() {
            addCriterion("gprssum is null");
            return (Criteria) this;
        }

        public Criteria andGprssumIsNotNull() {
            addCriterion("gprssum is not null");
            return (Criteria) this;
        }

        public Criteria andGprssumEqualTo(String value) {
            addCriterion("gprssum =", value, "gprssum");
            return (Criteria) this;
        }

        public Criteria andGprssumNotEqualTo(String value) {
            addCriterion("gprssum <>", value, "gprssum");
            return (Criteria) this;
        }

        public Criteria andGprssumGreaterThan(String value) {
            addCriterion("gprssum >", value, "gprssum");
            return (Criteria) this;
        }

        public Criteria andGprssumGreaterThanOrEqualTo(String value) {
            addCriterion("gprssum >=", value, "gprssum");
            return (Criteria) this;
        }

        public Criteria andGprssumLessThan(String value) {
            addCriterion("gprssum <", value, "gprssum");
            return (Criteria) this;
        }

        public Criteria andGprssumLessThanOrEqualTo(String value) {
            addCriterion("gprssum <=", value, "gprssum");
            return (Criteria) this;
        }

        public Criteria andGprssumLike(String value) {
            addCriterion("gprssum like", value, "gprssum");
            return (Criteria) this;
        }

        public Criteria andGprssumNotLike(String value) {
            addCriterion("gprssum not like", value, "gprssum");
            return (Criteria) this;
        }

        public Criteria andGprssumIn(List<String> values) {
            addCriterion("gprssum in", values, "gprssum");
            return (Criteria) this;
        }

        public Criteria andGprssumNotIn(List<String> values) {
            addCriterion("gprssum not in", values, "gprssum");
            return (Criteria) this;
        }

        public Criteria andGprssumBetween(String value1, String value2) {
            addCriterion("gprssum between", value1, value2, "gprssum");
            return (Criteria) this;
        }

        public Criteria andGprssumNotBetween(String value1, String value2) {
            addCriterion("gprssum not between", value1, value2, "gprssum");
            return (Criteria) this;
        }

        public Criteria andOpentimeIsNull() {
            addCriterion("opentime is null");
            return (Criteria) this;
        }

        public Criteria andOpentimeIsNotNull() {
            addCriterion("opentime is not null");
            return (Criteria) this;
        }

        public Criteria andOpentimeEqualTo(String value) {
            addCriterion("opentime =", value, "opentime");
            return (Criteria) this;
        }

        public Criteria andOpentimeNotEqualTo(String value) {
            addCriterion("opentime <>", value, "opentime");
            return (Criteria) this;
        }

        public Criteria andOpentimeGreaterThan(String value) {
            addCriterion("opentime >", value, "opentime");
            return (Criteria) this;
        }

        public Criteria andOpentimeGreaterThanOrEqualTo(String value) {
            addCriterion("opentime >=", value, "opentime");
            return (Criteria) this;
        }

        public Criteria andOpentimeLessThan(String value) {
            addCriterion("opentime <", value, "opentime");
            return (Criteria) this;
        }

        public Criteria andOpentimeLessThanOrEqualTo(String value) {
            addCriterion("opentime <=", value, "opentime");
            return (Criteria) this;
        }

        public Criteria andOpentimeLike(String value) {
            addCriterion("opentime like", value, "opentime");
            return (Criteria) this;
        }

        public Criteria andOpentimeNotLike(String value) {
            addCriterion("opentime not like", value, "opentime");
            return (Criteria) this;
        }

        public Criteria andOpentimeIn(List<String> values) {
            addCriterion("opentime in", values, "opentime");
            return (Criteria) this;
        }

        public Criteria andOpentimeNotIn(List<String> values) {
            addCriterion("opentime not in", values, "opentime");
            return (Criteria) this;
        }

        public Criteria andOpentimeBetween(String value1, String value2) {
            addCriterion("opentime between", value1, value2, "opentime");
            return (Criteria) this;
        }

        public Criteria andOpentimeNotBetween(String value1, String value2) {
            addCriterion("opentime not between", value1, value2, "opentime");
            return (Criteria) this;
        }

        public Criteria andActivetimeIsNull() {
            addCriterion("activetime is null");
            return (Criteria) this;
        }

        public Criteria andActivetimeIsNotNull() {
            addCriterion("activetime is not null");
            return (Criteria) this;
        }

        public Criteria andActivetimeEqualTo(String value) {
            addCriterion("activetime =", value, "activetime");
            return (Criteria) this;
        }

        public Criteria andActivetimeNotEqualTo(String value) {
            addCriterion("activetime <>", value, "activetime");
            return (Criteria) this;
        }

        public Criteria andActivetimeGreaterThan(String value) {
            addCriterion("activetime >", value, "activetime");
            return (Criteria) this;
        }

        public Criteria andActivetimeGreaterThanOrEqualTo(String value) {
            addCriterion("activetime >=", value, "activetime");
            return (Criteria) this;
        }

        public Criteria andActivetimeLessThan(String value) {
            addCriterion("activetime <", value, "activetime");
            return (Criteria) this;
        }

        public Criteria andActivetimeLessThanOrEqualTo(String value) {
            addCriterion("activetime <=", value, "activetime");
            return (Criteria) this;
        }

        public Criteria andActivetimeLike(String value) {
            addCriterion("activetime like", value, "activetime");
            return (Criteria) this;
        }

        public Criteria andActivetimeNotLike(String value) {
            addCriterion("activetime not like", value, "activetime");
            return (Criteria) this;
        }

        public Criteria andActivetimeIn(List<String> values) {
            addCriterion("activetime in", values, "activetime");
            return (Criteria) this;
        }

        public Criteria andActivetimeNotIn(List<String> values) {
            addCriterion("activetime not in", values, "activetime");
            return (Criteria) this;
        }

        public Criteria andActivetimeBetween(String value1, String value2) {
            addCriterion("activetime between", value1, value2, "activetime");
            return (Criteria) this;
        }

        public Criteria andActivetimeNotBetween(String value1, String value2) {
            addCriterion("activetime not between", value1, value2, "activetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNull() {
            addCriterion("updatetime is null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIsNotNull() {
            addCriterion("updatetime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeEqualTo(String value) {
            addCriterion("updatetime =", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotEqualTo(String value) {
            addCriterion("updatetime <>", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThan(String value) {
            addCriterion("updatetime >", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeGreaterThanOrEqualTo(String value) {
            addCriterion("updatetime >=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThan(String value) {
            addCriterion("updatetime <", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLessThanOrEqualTo(String value) {
            addCriterion("updatetime <=", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeLike(String value) {
            addCriterion("updatetime like", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotLike(String value) {
            addCriterion("updatetime not like", value, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeIn(List<String> values) {
            addCriterion("updatetime in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotIn(List<String> values) {
            addCriterion("updatetime not in", values, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeBetween(String value1, String value2) {
            addCriterion("updatetime between", value1, value2, "updatetime");
            return (Criteria) this;
        }

        public Criteria andUpdatetimeNotBetween(String value1, String value2) {
            addCriterion("updatetime not between", value1, value2, "updatetime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}