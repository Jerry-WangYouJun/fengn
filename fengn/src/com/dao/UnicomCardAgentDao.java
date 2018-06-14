package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.model.UnicomInfoVo;

@Repository
public class UnicomCardAgentDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<UnicomInfoVo> queryDataList(String selectSql) {
		final List<UnicomInfoVo> list = new ArrayList<UnicomInfoVo>();
		jdbcTemplate.query(selectSql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				UnicomInfoVo vo = new UnicomInfoVo();
				vo.setId(rs.getString("id"));
				vo.setIMSI(rs.getString("IMSI"));
				vo.setICCID(rs.getString("ICCID"));
				vo.setCardStatus(rs.getString("cardStatus"));
				vo.setGprsUsed(rs.getString("gprsUsed"));
				vo.setGprsRest(rs.getString("gprsrest"));
				vo.setCompanyLevel(rs.getString("company_level"));
				vo.setWithGPRSService(rs.getString("withGPRSService"));
				vo.setPackageType(rs.getString("packageType"));
				vo.setPackageDetail(rs.getString("packageDetail"));
				vo.setMonthTotalStream(rs.getString("monthTotalStream"));
				vo.setUpdateTime(rs.getString("updateTime"));
				vo.setDeadline(rs.getString("deadline"));
				vo.setOrderStatus(rs.getString("orderStatus"));
				vo.setRemark(rs.getString("remark"));
				vo.setName(rs.getString("name"));
				list.add(vo);
				return null;
			}
		});
		return list;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<UnicomInfoVo> queryCmccDataList(String selectSql) {
		final List<UnicomInfoVo> list = new ArrayList<UnicomInfoVo>();
		jdbcTemplate.query(selectSql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				UnicomInfoVo vo = new UnicomInfoVo();
				vo.setId(rs.getString("id"));
				vo.setSim(rs.getString("sim"));
				vo.setICCID(rs.getString("guid"));
				vo.setCardStatus(rs.getString("bootstate"));
				vo.setGprsUsed(rs.getString("monthusagedata"));
				vo.setGprsRest(rs.getString("flowleftvalue"));
				//vo.setCompanyLevel(rs.getString("company_level"));
				//vo.setWithGPRSService(rs.getString("withGPRSService"));
				vo.setPackageType(rs.getString("packagename"));
				//vo.setPackageDetail(rs.getString("packageDetail"));
				//vo.setMonthTotalStream(rs.getString("monthTotalStream"));
				//vo.setUpdateTime(rs.getString("updateTime"));
				vo.setDeadline(rs.getString("oddtime"));
				vo.setExpireTime(rs.getString("expiretime"));
				//vo.setOrderStatus(rs.getString("orderStatus"));
				vo.setActiveTime(rs.getString("activeTime"));
				//vo.setRemark(rs.getString(""));//截止日期暂用remark
				vo.setName(rs.getString("name"));
				list.add(vo);
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
	
	
}
