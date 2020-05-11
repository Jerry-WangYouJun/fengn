package com.service;

import java.io.UnsupportedEncodingException;
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
		 
		 public List<UnicomInfoVo> queryCardInfo(Integer agentid , Pagination page, QueryData qo ){
			 String sql = "select c.* , ag.name from u_card_agent a , u_cmtp c , a_agent ag "
				 		+ "where a.iccid = c.ICCID   and ag.id=a.agentid " + 
				 		"and ag.code like  CONCAT((select code from a_agent where id ="
				 		+ agentid + "),'%' ) ";
			if(StringUtils.isNotEmpty(qo.getIccidStart())){
				 sql += " and c.ICCID >= '" + qo.getIccidStart() + "'" ;
			}
			if(StringUtils.isNotEmpty(qo.getIccidEnd())){
				 sql += " and  c.ICCID <= '" + qo.getIccidEnd() + "'" ;
			}
			if(StringUtils.isNotEmpty(qo.getType())){
				String packageType  = "";
				try {
					 packageType =  new String(qo.getType().getBytes("ISO-8859-1"),"UTF-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				sql += " and  packageType  like '%" + packageType + "%'" ;
			}
			 String finalSql = Dialect.getLimitString(sql, page.getPageNo(), page.getPageSize(), "MYSQL");
			 return dao.queryDataList(finalSql);
		 }
		 
		 public int queryCardTotal(Integer agentid ,  QueryData qo){
			 String sql = "select count(*) total from u_card_agent a , u_cmtp c , a_agent ag "
				 		+ "where a.iccid = c.ICCID  and ag.id=a.agentid  " 
				 		+  "and ag.code like  CONCAT((select code from a_agent where id ="
				 		+ agentid + "),'%' ) ";
			if(StringUtils.isNotEmpty(qo.getIccidStart())){
				 sql += " and c.ICCID >= '" + qo.getIccidStart() + "'" ;
			}
			if(StringUtils.isNotEmpty(qo.getIccidEnd())){
				 sql += " and  c.ICCID <= '" + qo.getIccidEnd() + "'" ;
			}
			
			 return dao.queryDataTotal(sql);
		 }
		 
		 public List<UnicomInfoVo> queryCardInfo(Integer agentid , Pagination page, QueryData qo  , String table ){
		 	String simstate = qo.getSimstate();
		 	switch (simstate){
				case "0":{
					if("unicom".equals(table)){
						simstate="and c.simstate = '可激活' ";
					}else if("cmcc".equals(table)){
						simstate="and c.bootstate = '待激活' ";
					}

					break;
				}
				case "1":{
					if("unicom".equals(table)){
						simstate="and c.simstate = '已激活' ";
					}else if("cmcc".equals(table)){
						simstate="and c.bootstate = '正常' ";
					}

					break;
				}
				case "2":{
					if("unicom".equals(table)){
						simstate="and c.simstate = '已停用' ";
					}else if("cmcc".equals(table)){
						simstate="and c.bootstate = '停机' ";
					}
					break;
				}
				default:
					simstate="";
					break;
			}
			 String sql = "select c.* , ag.name from "+table+"_card_agent a , mlb_"+table+"_card c , a_agent ag "
				 		+ "where a.iccid = c.iccid   and ag.id=a.agentid " ;
			 if(StringUtils.isNotEmpty(qo.getPackageId())){
				 sql += "and a.pacid='"+qo.getPackageId()+"' ";
			 }
			 if(StringUtils.isNotEmpty(simstate)){
				 sql += simstate;
			 }
			 if(StringUtils.isNotEmpty(qo.getAgentName())){
				 sql += "and ag.name like '%"+qo.getAgentName().trim()+"%' ";
			 }
			 if(StringUtils.isNotEmpty(qo.getSimNum())){
				 sql += "and c.sim='"+qo.getSimNum().trim()+"' ";
			 }
			 if(StringUtils.isNotEmpty(qo.getActiveStartTime())){
			 	if("unicom".equals(table)){
					sql += "and c.lastActiveTime>='"+qo.getActiveStartTime().trim()+"' ";
				}else if("cmcc".equals(table)){
					sql += "and c.activeTime>='"+qo.getActiveStartTime().trim()+"' ";
				}
			 }
			 if(StringUtils.isNotEmpty(qo.getActiveEndTime())){
				 if("unicom".equals(table)){
					 sql += "and c.lastActiveTime<='"+qo.getActiveEndTime().trim()+"' ";
				 }else if("cmcc".equals(table)){
					 sql += "and c.activeTime<='"+qo.getActiveEndTime().trim()+"' ";
				 }
			 }
			sql +="and ag.code like  CONCAT((select code from a_agent where id = "
				+ agentid + "),'%' ) ";
			if(StringUtils.isNotEmpty(qo.getIccidStart())){
				 sql += " and c.iccid >= '" + qo.getIccidStart() + "' " ;
			}
			if(StringUtils.isNotEmpty(qo.getIccidEnd())){
				 sql += " and  c.iccid <= '" + qo.getIccidEnd() + "' " ;
			}
			
			sql+= " and c.iccid != ''   ORDER BY  c.iccid  " ;
			 String finalSql = Dialect.getLimitString(sql, page.getPageNo(), page.getPageSize(), "MYSQL");
			 if("cmcc".equals(table)) {
					return dao.queryCmccDataList(finalSql);
				}else {
					return dao.queryDataList(finalSql);
				}
		 }
		 
		 public int queryCardTotal(Integer agentid ,  QueryData qo, String table ){
			 String simstate = qo.getSimstate();
			 switch (simstate){
				 case "0":{
					 if("unicom".equals(table)){
						 simstate="and c.simstate = '可激活' ";
					 }else if("cmcc".equals(table)){
						 simstate="and c.bootstate = '待激活' ";
					 }

					 break;
				 }
				 case "1":{
					 if("unicom".equals(table)){
						 simstate="and c.simstate = '已激活' ";
					 }else if("cmcc".equals(table)){
						 simstate="and c.bootstate = '正常' ";
					 }

					 break;
				 }
				 case "2":{
					 if("unicom".equals(table)){
						 simstate="and c.simstate = '已停用' ";
					 }else if("cmcc".equals(table)){
						 simstate="and c.bootstate = '停机' ";
					 }
					 break;
				 }
				 default:
					 simstate="";
					 break;
			 }
			 String sql = "select count(*) total from "+table+"_card_agent a , mlb_"+table+"_card c , a_agent ag "
				 		+ "where a.iccid = c.iccid  and ag.id=a.agentid  ";
			 if(StringUtils.isNotEmpty(qo.getPackageId())){
				 sql += "and a.pacid='"+qo.getPackageId()+"' ";
			 }
			 if(StringUtils.isNotEmpty(simstate)){
				 sql += simstate;
			 }
			 if(StringUtils.isNotEmpty(qo.getAgentName())){
				 sql += "and ag.name like '%"+qo.getAgentName().trim()+"%' ";
			 }
			 if(StringUtils.isNotEmpty(qo.getSimNum())){
				 sql += "and c.sim='"+qo.getSimNum().trim()+"' ";
			 }
			 if(StringUtils.isNotEmpty(qo.getActiveStartTime())){
				 if("unicom".equals(table)){
					 sql += "and c.lastActiveTime>='"+qo.getActiveStartTime().trim()+"' ";
				 }else if("cmcc".equals(table)){
					 sql += "and c.activeTime>='"+qo.getActiveStartTime().trim()+"' ";
				 }
			 }
			 if(StringUtils.isNotEmpty(qo.getActiveEndTime())){
				 if("unicom".equals(table)){
					 sql += "and c.lastActiveTime<='"+qo.getActiveEndTime().trim()+"' ";
				 }else if("cmcc".equals(table)){
					 sql += "and c.activeTime<='"+qo.getActiveEndTime().trim()+"' ";
				 }
			 }
			 sql +="and ag.code like  CONCAT((select code from a_agent where id ="
				 		+ agentid + "),'%' ) ";;
			if(StringUtils.isNotEmpty(qo.getIccidStart())){
				 sql += " and c.iccid >= '" + qo.getIccidStart().trim() + "' " ;
			}
			if(StringUtils.isNotEmpty(qo.getIccidEnd())){
				 sql += " and  c.iccid <= '" + qo.getIccidEnd().trim() + "' " ;
			}
			sql+= " and c.iccid != ''  ";
			 return dao.queryDataTotal(sql);
		 }


	public List<Map<String,String>> queryAllKickbackInfo(Integer id , QueryData qo ,  Pagination page , int timeType , String table) {
		List<Map<String,String>>  list = new ArrayList<>();
		String[] tableList = table.split(",");
		String sql = "select z.iccid, z.money, z.packageType, z.update_date, z.pacid,r.paccost,z.money - IFNULL(r.paccost, z.cost) kickback from (";
		sql += "select * from (";
		for(int i=0;i<tableList.length;i++){
			sql += "select h.iccid , h.money , h.packageType , h.update_date , a.pacid, u.cost "
					+ "from u_history h , "+tableList[i]+"_card_agent a , mlb_"+tableList[i]+"_card c , a_agent u "
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
			if(StringUtils.isNotEmpty(qo.getIccidStart())){
				sql += " and c.iccid >= '" + qo.getIccidStart() + "' " ;
			}
			if(StringUtils.isNotEmpty(qo.getIccidEnd())){
				sql += " and  c.iccid <= '" + qo.getIccidEnd() + "' " ;
			}
			if(StringUtils.isNotEmpty(qo.getActiveStartTime())){
				sql += "  and h.update_date>= '" + qo.getActiveStartTime() + "' " ;
			}
			if(StringUtils.isNotEmpty(qo.getActiveEndTime())){
				sql += "  and h.update_date<= '" + qo.getActiveEndTime() + "' " ;
			}
			if(StringUtils.isNotEmpty(qo.getPackageId())){
				sql += "  and A.pacid = '" + qo.getPackageId() + "' " ;
			}
			if(tableList.length > (i+1)){
				sql+=" union all ";
			}
		}
		sql += " ) m ) z LEFT JOIN t_package p ON p.id = z.pacid left join t_package_ref r on p.id=r.pacid where r.agentid='"+id+"' order by z.update_date  desc ";
		String finalSql = Dialect.getLimitString(sql, page.getPageNo(), page.getPageSize(), "MYSQL");
		list = dao.queryKickbackList(finalSql);
		return  list;
	}

	public Map<String , Double > queryAllKickbackTotal(Integer agentid ,  QueryData qo  , int timeType,String table){
		String[] tableList = table.split(",");
		String sql = "select z.iccid, z.money, z.packageType, z.update_date, z.pacid,r.paccost,z.money - IFNULL(r.paccost, z.cost) kickback from (";
		sql = "select count(*) total,sum(x.kickback) sumKick from (";
		sql += "select z.iccid, z.money, z.packageType, z.update_date, z.pacid,z.money - IFNULL(r.paccost, z.cost) kickback from (";
		sql += "select * from (";
		for(int i=0;i<tableList.length;i++){
			sql += "select h.iccid , h.money , h.packageType , h.update_date ,  a.pacid, u.cost "
					+ "from u_history h , "+tableList[i]+"_card_agent a , mlb_"+tableList[i]+"_card c , a_agent u "
//					+ " left join t_package p on p.id=u.type "
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
			if(StringUtils.isNotEmpty(qo.getIccidStart())){
				sql += " and c.iccid >= '" + qo.getIccidStart() + "' " ;
			}
			if(StringUtils.isNotEmpty(qo.getIccidEnd())){
				sql += " and  c.iccid <= '" + qo.getIccidEnd() + "' " ;
			}
			if(StringUtils.isNotEmpty(qo.getActiveStartTime())){
				sql += "  and  h.update_date>= '" + qo.getActiveStartTime() + "' " ;
			}
			if(StringUtils.isNotEmpty(qo.getActiveEndTime())){
				sql += "  and  h.update_date<= '" + qo.getActiveEndTime() + "' " ;
			}
			if(StringUtils.isNotEmpty(qo.getPackageId())){
				sql += "  and  A.pacid = '" + qo.getPackageId() + "' " ;
			}
			if(tableList.length > (i+1)){
				sql+=" union all ";
			}
		}
		sql +=" ) m ) z LEFT JOIN t_package p ON p.id = z.pacid left join t_package_ref r on p.id=r.pacid where r.agentid='"+agentid+"') x";

		return dao.querySumKick(sql);
	}


		public List<Map<String,String>> queryKickbackInfo(Integer id , QueryData qo ,  Pagination page , int timeType , String table) {
			List<Map<String,String>>  list = new ArrayList<>();
			String sql = "select z.iccid, z.money, z.packageType, z.update_date, z.pacid,r.paccost,z.money - IFNULL(r.paccost, z.cost) kickback from (";
			sql += "select h.iccid , h.money , h.packageType , h.update_date ,  a.pacid, u.cost "
					+ "from u_history h , "+table+"_card_agent a , mlb_"+table+"_card c , a_agent u "
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
			sql += ") z LEFT JOIN t_package p ON p.id = z.pacid left join t_package_ref r on p.id=r.pacid where r.agentid='"+id+"'";

			sql += " order by z.update_date  desc ";
			String finalSql = Dialect.getLimitString(sql, page.getPageNo(), page.getPageSize(), "MYSQL");
			list = dao.queryKickbackList(finalSql);
			return list;
		}
		
		 public Map<String , Double > queryKickbackTotal(Integer agentid ,  QueryData qo  , int timeType,String table){

//			 String sql = "select   sum(h.money) - sum(IFNULL(p.cost,u.cost)) sumKick , count(*) total  "
//					 	+ "from u_history h , "+table+"_card_agent a , mlb_"+table+"_card c , a_agent u "
//						+ " left join t_package p on p.id=u.type "
//						+ " where h.iccid = c.iccid and c.iccid = a.iccid "
//						+ " and  u.id = a.agentid  and  u.id = " + agentid  ;
			 String sql = "select count(*) total,sum(x.kickback) sumKick from (";
			 sql += "select z.iccid, z.money, z.packageType, z.update_date, z.pacid,z.money - IFNULL(r.paccost, z.cost) kickback from (";
			 sql += "select h.iccid , h.money , h.packageType , h.update_date ,  a.pacid, u.cost "
					 + "from u_history h , "+table+"_card_agent a , mlb_"+table+"_card c , a_agent u "
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
			 sql += ") z LEFT JOIN t_package p ON p.id = z.pacid left join t_package_ref r on p.id=r.pacid where r.agentid='"+agentid+"') x";
			 return dao.querySumKick(sql);
		 }
}
