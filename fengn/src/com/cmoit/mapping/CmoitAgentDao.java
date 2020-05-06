package com.cmoit.mapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.common.Dialect;
import com.model.Agent;
import com.model.Pagination;
import com.model.QueryData;


@Repository
public class CmoitAgentDao {
	  
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Agent> queryList(QueryData qo, Pagination page) {
		String sql = "select a.* , u.userno from a_agent a , a_user u   where a.id = u.agentid " + whereSQL(qo) ;
		String finalSql = Dialect.getLimitString(sql, page.getPageNo(), page.getPageSize(), "MYSQL");
         final  List<Agent> list =   new ArrayList<>();
         jdbcTemplate.query(finalSql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					Agent  vo = new Agent(); 
					vo.setId(rs.getInt("id"));
					vo.setCode(rs.getString("code"));
					vo.setName(rs.getString("name"));
					vo.setType(rs.getString("type"));
					vo.setCost(rs.getDouble("cost"));
					vo.setRenew(rs.getDouble("renew"));
					vo.setParentId(rs.getInt("parentid"));
					vo.setCreater(rs.getString("creater"));
					vo.setUserNo(rs.getString("userNo"));
					list.add(vo);
				 return null ;
			}
		});
		return list;
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Integer queryPrentIdByCode(String code) {
		String sql = "select id  from a_agent  where code = '" + code + "'" ;
		final  List<Integer> list = new ArrayList<>();
		jdbcTemplate.query(sql, new RowMapper() {
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					list.add(rs.getInt("id"));
					 return null ;
				}
		});
		if(list.size()>0){
			  return  list.get(0);
		}else{
			return 0 ;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Agent> queryTreeData() {
		String sql = "select a.id pid , a.name pname , b.id sid ,  b.name sname  "
				+ " from a_agent a LEFT JOIN  a_agent b on  a.id  = b.parentId " ;
		final  List<Agent> list =   new ArrayList<>();
        jdbcTemplate.query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					Agent  vo = new Agent(); 
					vo.setId(rs.getInt("id"));
					vo.setCode(rs.getString("code"));
					vo.setName(rs.getString("name"));
					vo.setType(rs.getString("type"));
					vo.setCost(rs.getDouble("cost"));
					vo.setRenew(rs.getDouble("renew"));
					vo.setCreater(rs.getString("creater"));
					list.add(vo);
				 return null ;
			}
		});
		return list;
	}

	public void updateCardAgent(String iccids, String agentid , String table ) {
		String sql = "update "+table +"_card_agent set  agentid = " +
				agentid + "  where iccid in ("
				+ " select guid from mlb_"+table+"_card  where id in (" + iccids  + "  0 ) )" ;
		jdbcTemplate.update(sql);
	}
	
	public void updateCardAgent(String iccids, String agentid) {
		String sql = "update unicom_card_agent set  agentid = " +
				agentid + "  where iccid in ("
				+ " select guid from mlb_unicom_card  where id in (" + iccids  + "  0 ) )" ;
		jdbcTemplate.update(sql);
	}

	public String whereSQL(QueryData qo){
		String whereSql = "";
		if(StringUtils.isNotEmpty(qo.getAgentName())){
			whereSql += " and   a.name  like  '%" + qo.getAgentName() + "%' ";
		}
		if(StringUtils.isNotEmpty(qo.getAgentCode())){
			whereSql += " and   a.code  like   '" + qo.getAgentCode() + "-__' ";
			
		}
		if(StringUtils.isNotEmpty(qo.getAgentid())){
			whereSql += " and   a.id =  '" + qo.getAgentid() + "' ";
		}
		if(StringUtils.isNotEmpty(qo.getMoveFlag()) ) {
			String group = "1,2,3";
			if("1".equals(qo.getMoveFlag())) {
				 group = "1,3";
			}else if("2".equals(qo.getMoveFlag())) {
				group = "2,3";
			}
			whereSql += " and a.groupId in (" + group + " )";
		}
		return whereSql ;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int queryTotal(QueryData qo) {
		final Integer[] total =  {0} ;
		String  sql  = "select count(*) total from a_agent a , a_user u where  a.id = u.agentid  " + whereSQL(qo) ;
		jdbcTemplate.query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				 total[0] = rs.getInt("total");
				 return null ;
			}
		});
		return total[0];
	}

	public void updateOrderStatus(Integer id) {
			 jdbcTemplate.update(  
		                "update u_cmtp set orderStatus = '1'  where id = ?",   
		                new Object[]{id},   
		                new int[]{java.sql.Types.INTEGER});  
	}


	public void updateCardAgent(String iccids, String agentid, String table, String pacId) {
		String sql = "update "+table +"_card_agent set  agentid = " +
				agentid +( pacId ==null?"": ",pacid='"+pacId+"' " ) +"  where iccid in ("
				+ " select iccid from "+table+"_card  where id in (" + iccids  + "  0 ) )" ;
		jdbcTemplate.update(sql);
	}
}
