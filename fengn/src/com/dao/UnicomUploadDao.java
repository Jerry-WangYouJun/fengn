package com.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.model.History;
import com.model.InfoPackage;
import com.model.InfoVo;
import com.model.MlbUnicomCard;

/**
 * @author lx g
 *
 */
@Repository
public class UnicomUploadDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	// 存储数据数组List
	List<List<Object>> objectList = new ArrayList<List<Object>>();
	List<MlbUnicomCard> mlbunicomList = new ArrayList<>();
	// 存储list
	List<InfoVo> voList = new ArrayList<InfoVo>();
	List<String> iccidList = new ArrayList<String>();
	// 存储当前表的插入语句
	String INSERTSQL = "";
	StringBuffer mailMessage = new StringBuffer("");
	String columuns = "ICCID, packageType ,	monthTotalStream,  	gprsUsed, gprsRest , deadline , "
			+ "	cardStatus  ,updateTime  , remark	";

	public int insertDataTemp(List<MlbUnicomCard> listob ,String table ) {
		String insertsqlTemp = "INSERT INTO  mlb_" + table + "_card_copy (  guid ) "
				+ "VALUES (? )";
		mlbunicomList = listob;
		// batchUpdate可以高效进行批量插入操作
		try {
			if (mlbunicomList != null && objectList.size() > 0) {
				jdbcTemplate.batchUpdate(insertsqlTemp,
						new BatchPreparedStatementSetter() {
							public void setValues(PreparedStatement ps, int i) {
								try {
									// 并根据数据类型对Statement 中的占位符进行赋值
									List<Object> valueList = objectList.get(i);
									ps.setString(1, String.valueOf(valueList.get(0)).trim());//ICCID
								} catch (Exception e) {
									e.printStackTrace();
									System.out.println("出错的" + i);
								}
							}

							public int getBatchSize() {
								return objectList.size();
							}
						});
			}
		} catch (Exception e) {
			mailMessage.append("执行" + INSERTSQL + "时发生错误：" + e.getMessage()
					+ "<br>");
			e.printStackTrace();
		}
		return objectList.size();
	}

	public void deleteDataTemp(String  table ) {
		String insertNewDataSql = "delete from " +  table;
		try {
			jdbcTemplate.update(insertNewDataSql);
		} catch (Exception e) {
			mailMessage.append("执行中出错：" + e.getMessage());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void queryPackage(InfoVo info) {
		String sql = "select *   from package  where imsi = '" + info.getIMSI()
				+ "'";
		final List<InfoPackage> list = new ArrayList<InfoPackage>();
		// Map<String, String> map = new HashMap<String, String>();
		jdbcTemplate.query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				InfoPackage vo = new InfoPackage();
				vo.setId(rs.getInt("id"));
				vo.setPackageName(rs.getString("package_name"));
				vo.setRemark(rs.getString("remark"));
				list.add(vo);
				return null;
			}
		});
		info.setPackageList(list);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void queryHistory(InfoVo info) {
		String sql = "select  h.imsi ,  p.package_name pname ,  h.update_date utime  , money  ,p.remark  premark "
				+ " from history  h,  package p    where h.package_id= p.id and h.iccid = '"
				+ info.getICCID() + "'";
		final List<History> list = new ArrayList<History>();
		// Map<String, String> map = new HashMap<String, String>();
		jdbcTemplate.query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				History vo = new History();
				vo.setImsi(rs.getString("imsi"));
				vo.setPname(rs.getString("pname"));
				vo.setUpdateDate(rs.getString("utime"));
				vo.setMoney(rs.getDouble("money"));
				vo.setPremark(rs.getString("premark"));
				list.add(vo);
				return null;
			}
		});
		info.setHistory(list);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int queryTotal(String selectSql) {
		final Integer[] total = new Integer[1];
		jdbcTemplate.query(selectSql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				total[0] = new Integer(rs.getInt("total"));
				return rs.getInt("total");
			}
		});
		return total[0];
	}

	public void update(String sql) {

		try {
			jdbcTemplate.update(sql);
		} catch (Exception e) {
			mailMessage.append("执行中出错：" + e.getMessage());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<String> queryIccidList(String selectSql) {
		final List<String> list = new ArrayList<String>();
		jdbcTemplate.query(selectSql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				list.add(rs.getString("ICCID"));
				return null;
			}
		});
		return list;
	}

	public int insertAgentCard(String agentId , String table) {
		String insertsqlTemp = "insert " + table +"_card_agent (iccid , agentid ) select t.guid , '" + agentId+ "'"
				+ " from  mlb_"+ table +"_card_copy  t  "
				+ " where  t.guid not in (select guid  from  " + table+ "_card_agent ) ";
		return jdbcTemplate.update(insertsqlTemp);
	}
	
	
	public int insertMlbHistory( String  cmtpTable , String historyTable) {
		String insertsqlTemp = "insert "+historyTable +" "
				+ "(iccid , imsi ,packageType ,money , update_date , packagedetail , orderno  ) " + 
				" select  distinct  m.iccid ,  m.imsi , m.oldPackageName  ,"
				+ "  m.Amount , m.PayTime , m.PackageName , m.OrderSign"
				+ "  from t_mlb_temp m  join  "+cmtpTable +" c  where m.iccid = c.iccid " + 
				"";
		return jdbcTemplate.update(insertsqlTemp);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map<String, String>> queryKickbackList(String sql) {
		final List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		// Map<String, String> map = new HashMap<String, String>();
		jdbcTemplate.query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				Map<String, String>  map  =  new HashMap<>() ; 
				map.put("iccid", rs.getString("iccid"));
				map.put("money", rs.getString("money"));
				map.put("packageType", rs.getString("packageType"));
				map.put("update_date", rs.getString("update_date"));
				map.put("kickback", rs.getString("kickback"));
				list.add(map);
				return null;
			}
		});
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int queryDataTotal(String sql) {
	         final  Integer[] arr =  {0};
	         jdbcTemplate.query(sql, new RowMapper() {
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
						arr[0] = rs.getInt("total");
					 return null ;
				}
			});
			return arr[0];
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String , Double > querySumKick(String sql) {
	         final  Map<String, Double>  map  =  new HashMap<>() ; 
	         jdbcTemplate.query(sql, new RowMapper() {
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					map.put("total", rs.getDouble("total"));
					map.put("sumKick", rs.getDouble("sumKick"));
					return null;
				}
			});
			return map;
	}

	public int insertData(String table) {
		String insertsqlTemp = "insert into  mlb_" + table +  "_card  (  guid, packageName, " + 
				"      simState, expireTime, oddTime, " + 
				"      dayUsageData, amountUsageData, totalMonthUsageFlow	) "
				+ " select   guid, packageName, " + 
				"      simState, expireTime, oddTime, " + 
				"	   dayUsageData, amountUsageData, totalMonthUsageFlow  from mlb_"+table+"_card_copy "
				+ " where guid not in (select guid  from  mlb_"+table+"_card )";
		return jdbcTemplate.update(insertsqlTemp);
	}
	
	public int insertCmccData(String table) {
		String insertsqlTemp = "insert into  mlb_" + table +  "_card  ( guid, simId, " + 
				"      sim, packageName, bootState, " + 
				"      expireTime, oddTime, amountUsageData, " + 
				"      flowLeftValue, monthUsageData, totalMonthUsageFlow 	) "
				+ " select  guid, simId, " + 
				"     sim, packageName, bootState,  " + 
				"	  expireTime, oddTime, amountUsageData,"
				+ "  flowLeftValue, monthUsageData, totalMonthUsageFlow  "
				+ "  from mlb_"+table+"_card_copy "
				+ " where guid not in (select guid  from  mlb_"+table+"_card )";
		return jdbcTemplate.update(insertsqlTemp);
	}

	public int updateData() {
		String updateSqlTemp = "UPDATE u_cmtp c , u_cmtp_temp t SET c.cardStatus=t.cardStatus , "
				+ "c.company_level=t.company_level , c.deadline = t.deadline , c.gprsRest = t.gprsRest , "
				+ "c.gprsUsed=t.gprsUsed  , c.monthTotalStream = t.monthTotalStream  , c.packageDetail=t.packageDetail , "
				+ "c.packageType =t.packageType , c.remark=t.remark , c.updateTime = t.updateTime WHERE c.ICCID = t.ICCID ";
		return jdbcTemplate.update(updateSqlTemp);
	}
}
