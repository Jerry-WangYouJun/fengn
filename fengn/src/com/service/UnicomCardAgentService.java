package com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.Dialect;
import com.dao.CardInfoMapper;
import com.dao.UnicomCardAgentDao;
import com.model.Pagination;
import com.model.QueryData;
import com.model.UnicomInfoVo;

@Service
public class UnicomCardAgentService {
	   
		 @Autowired
		 UnicomCardAgentDao dao ;
		 
		 @Autowired
		 CardInfoMapper cardInfoDao;
		 public List<UnicomInfoVo> queryCardInfo(Integer agentid , Pagination page, QueryData qo  , String table ){
			 String sql = "select c.* , ag.name from "+table+"_card_agent a , mlb_"+table+"_card c , a_agent ag "
				 		+ "where a.iccid = c.guid   and ag.id=a.agentid " + 
				 		"and ag.code like  CONCAT((select code from a_agent where id ="
				 		+ agentid + "),'%' ) ";
			if(StringUtils.isNotEmpty(qo.getIccidStart())){
				 sql += " and c.guid >= '" + qo.getIccidStart() + "'" ;
			}
			if(StringUtils.isNotEmpty(qo.getIccidEnd())){
				 sql += " and  c.guid <= '" + qo.getIccidEnd() + "'" ;
			}
			 String finalSql = Dialect.getLimitString(sql, page.getPageNo(), page.getPageSize(), "MYSQL");
			 if("cmcc".equals(table)) {
					return dao.queryCmccDataList(finalSql);
				}else {
					return dao.queryDataList(finalSql);
				}
		 }
		 
		 public int queryCardTotal(Integer agentid ,  QueryData qo, String table ){
			 String sql = "select count(*) total from "+table+"_card_agent a , mlb_"+table+"_card c , a_agent ag "
				 		+ "where a.iccid = c.guid  and ag.id=a.agentid  " 
				 		+  "and ag.code like  CONCAT((select code from a_agent where id ="
				 		+ agentid + "),'%' ) ";;
			if(StringUtils.isNotEmpty(qo.getIccidStart())){
				 sql += " and c.guid >= '" + qo.getIccidStart() + "'" ;
			}
			if(StringUtils.isNotEmpty(qo.getIccidEnd())){
				 sql += " and  c.guid <= '" + qo.getIccidEnd() + "'" ;
			}
			
			 return dao.queryDataTotal(sql);
		 }

		public List<Map<String,String>> queryKickbackInfo(Integer id , QueryData qo ,  Pagination page , int timeType) {
			List<Map<String,String>>  list = new ArrayList<>();
			String sql = "select h.iccid , h.money , c.packageType , h.update_date , h.money - IFNULL(p.cost,u.cost)  kickback "
					+ "from u_history h , u_cmtp c , u_card_agent a , a_agent u "
					+ " left join t_package p on p.id=u.type "
					+ " where h.iccid = c.iccid and c.iccid = a.iccid "
					+ " and  u.id = a.agentid  and  u.id = " + id  ;
			if(StringUtils.isNotEmpty(qo.getDateStart())){
				 sql += " and h.update_date >= '" + qo.getDateStart() + "'" ;
			}
			if(StringUtils.isNotEmpty(qo.getDateEnd())){
				 sql += " and  h.update_date <= '" + qo.getDateEnd() + "'" ;
			}
			if(timeType == 7 ) {
				sql += " and  DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(h.update_date)";
			}else if(timeType == 30) {
				sql += " and DATE_FORMAT( h.update_date, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' )";
			}else if(timeType == 60) {
				sql +=" and PERIOD_DIFF( date_format( now( ) , '%Y%m' ) , date_format( h.update_date, '%Y%m' ) ) =1";
			}
			sql += " order by h.update_date  desc ";
			String finalSql = Dialect.getLimitString(sql, page.getPageNo(), page.getPageSize(), "MYSQL");
			list = dao.queryKickbackList(finalSql);
			return list;
		}
		
		 public Map<String , Double > queryKickbackTotal(Integer agentid ,  QueryData qo  , int timeType){
			 String sql = "select   sum(h.money) - sum(IFNULL(p.cost,u.cost)) sumKick , count(*) total  "
						+ "from u_history h , u_cmtp c , u_card_agent a , a_agent u"
						+ " left join t_package p on p.id=u.type "
						+ " where h.iccid = c.iccid and c.iccid = a.iccid "
						+ " and  u.id = a.agentid  and  u.id = " + agentid  ;
				if(StringUtils.isNotEmpty(qo.getDateStart())){
					 sql += " and h.update_date >= '" + qo.getDateStart() + "'" ;
				}
				if(StringUtils.isNotEmpty(qo.getDateEnd())){
					 sql += " and  h.update_date <= '" + qo.getDateEnd() + "'" ;
				}
				if(timeType == 7 ) {
					sql += " and  DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(h.update_date)";
				}else if(timeType == 30) {
					sql += " and DATE_FORMAT( h.update_date, '%Y%m' ) = DATE_FORMAT( CURDATE( ) , '%Y%m' )";
				}else if(timeType == 60) {
					sql +=" and PERIOD_DIFF( date_format( now( ) , '%Y%m' ) , date_format( h.update_date, '%Y%m' ) ) =1";
				}
			 return dao.querySumKick(sql);
		 }
}
