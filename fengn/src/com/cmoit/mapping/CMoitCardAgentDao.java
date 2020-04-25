package com.cmoit.mapping;

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

import com.cmoit.model.CmoitCard;

@Repository
public class CMoitCardAgentDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<CmoitCard> queryDataList(String selectSql) {
		final List<CmoitCard> list = new ArrayList<CmoitCard>();
		jdbcTemplate.query(selectSql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				CmoitCard vo = new CmoitCard();
				vo.setActivetime(rs.getString("activetime"));
				vo.setBalance(rs.getString("balance"));
				vo.setCallsum(rs.getString("callsum"));
				vo.setCardstatus(rs.getString("cardstatus") );
				vo.setGprssum(rs.getString("gprssum") );
				vo.setGprsused(rs.getString("gprsused") );
				vo.setIccid(rs.getString("iccid"));
				vo.setId(rs.getInt("id"));
				vo.setImsi(rs.getString("iccid"));
				vo.setMsgsum(rs.getString("msgsum"));
				vo.setMsgused(rs.getString("msgused"));
				vo.setMsisdn(rs.getString("msisdn"));
				vo.setOpentime(rs.getString("opentime"));
				vo.setUpdatetime(rs.getString("updatetime"));
				vo.setUserstatus(rs.getString("userstatus"));
				list.add(vo);
				return null;
			}
		});
		return list;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<CmoitCard> queryCmccDataList(String selectSql) {
		final List<CmoitCard> list = new ArrayList<CmoitCard>();
		jdbcTemplate.query(selectSql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				CmoitCard vo = new CmoitCard();
				vo.setActivetime(rs.getString("activetime"));
				vo.setBalance(rs.getString("balance"));
				vo.setCallused(rs.getString("callused"));
				vo.setCallsum(rs.getString("callsum"));
				vo.setCardstatus(rs.getString("cardstatus") );
				vo.setDiscrip(rs.getString("discrip"));
				vo.setGprssum(rs.getString("gprssum") );
				vo.setGprsused(rs.getString("gprsused") );
				vo.setIccid(rs.getString("iccid"));
				vo.setId(rs.getInt("id"));
				vo.setImsi(rs.getString("imsi"));
				vo.setMsgsum(rs.getString("msgsum"));
				vo.setMsgused(rs.getString("msgused"));
				vo.setMsisdn(rs.getString("msisdn"));
				vo.setName(rs.getString("name"));
				vo.setOpentime(rs.getString("opentime"));
				vo.setUpdatetime(rs.getString("updatetime"));
				vo.setUserstatus(rs.getString("userstatus"));
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
				map.put("paccost", rs.getString("paccost"));
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String queryTelphone(String selectSql) {
		 String  tel = jdbcTemplate.queryForObject(selectSql, String.class);
		return tel;
	}
	
	
}
