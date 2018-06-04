package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.common.CodeUtil;
import com.common.Dialect;
import com.model.Agent;
import com.model.Pagination;
import com.model.QueryData;


@Repository
public class AgentDao {
	  
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Agent> queryList(QueryData qo, Pagination page ) {
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
					vo.setParengId(rs.getInt("parentid"));
					vo.setCreater(rs.getString("creater"));
					vo.setUserNo(rs.getString("userNo"));
					vo.setGroupId(rs.getInt("groupId"));
					list.add(vo);
				 return null ;
			}
		});
		return list;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Agent> queryTreeList(String urlType) {
		String sql = "select a.* , u.userno from a_agent a , a_user u   where a.id = u.agentid " ;
		if(urlType.startsWith("unicom")) {
			sql += " and a.groupId in (2 , 3 )";
		}else {
			sql += " and a.groupId in (1 , 3)";
		}
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
					vo.setParengId(rs.getInt("parentid"));
					vo.setCreater(rs.getString("creater"));
					vo.setUserNo(rs.getString("userNo"));
					vo.setGroupId(rs.getInt("groupId"));
					list.add(vo);
				 return null ;
			}
		});
		return list;
	}

	public int insert(final Agent agent) {
		//KeyHolder keyHolder = new GeneratedKeyHolder();
		 final String sql = "insert into a_agent (code,name,creater,parentid,groupId) values(?,?,?,?,?)";
		 KeyHolder keyHolder = new GeneratedKeyHolder();
		 jdbcTemplate.update(new PreparedStatementCreator() {
	        @Override
	        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
	        	int parentId = queryPrentIdByCode(agent.getCode()) ;
	            PreparedStatement ps  = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,  getMaxCode(agent.getCode(),parentId));  
                ps.setString(2, agent.getName()); 
                ps.setString(3, agent.getCreater());
                ps.setInt(4, parentId);
                ps.setInt(5, agent.getGroupId());
	            return ps;
	        }
	    }, keyHolder);
	    return keyHolder.getKey().intValue();
	}


//	private int addAndGetId(final Agent agent) {
//	    final String sql = "insert into orders(name, address,createtime,totalprice,status) values(?,?,?,?,?)";
//	    KeyHolder keyHolder = new GeneratedKeyHolder();
//	    jdbcTemplate.update(new PreparedStatementCreator() {
//	        @Override
//	        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
//	        	int parentId = queryPrentIdByCode(agent.getCode()) ;
//	            PreparedStatement ps  = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//	           
//	            return ps;
//	        }
//	    }, keyHolder);
//	    return keyHolder.getKey().intValue();
//	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getMaxCode(String createrCode , int parentId){
		String sql = "select code  from a_agent  where code   like   '" + createrCode + "%' and parentid =  " + parentId ;
		final List<String> list = new ArrayList<>();
		jdbcTemplate.query(sql, new RowMapper() {
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					list.add(rs.getString("code"));
					 return null ;
				}
		});
		int temp = 0;
		if(list.size()==0){
			 return createrCode + "-01"  ;  
		} 
		 for(int i = 0   ;  i < list.size() ; i ++){
			 String a = list.get(i).replaceFirst( createrCode + "-" , "");
			  int t = Integer.valueOf(a);
			  if(t > temp){
				    temp = t ;
			  }
		 }
		return createrCode + CodeUtil.getFixCode(temp+1);
	}
	
	/*public static void main(String[] args) {
		String a = "aaabvvv";
		 System.out.println(a.replaceFirst("a", ""));
	}*/
	public void update(final Agent agent) {
		String sql = "update a_agent set  cost=?, renew = ? " ;
		if(agent.getType()!= null && StringUtils.isNotEmpty(agent.getType())){
			 sql += ", type = ?  " ;
		}
		sql += " , groupId = ? where id = ? ";
		jdbcTemplate.update(sql,   
                new PreparedStatementSetter(){  
                    @Override  
                    public void setValues(PreparedStatement ps) throws SQLException {  
                        ps.setDouble(1, agent.getCost());
                        ps.setDouble(2 , agent.getRenew());
                        if(agent.getType()!= null && StringUtils.isNotEmpty(agent.getType())){
                        	ps.setString(3, agent.getType());
                        	ps.setInt(4, agent.getGroupId());
                        	ps.setString(5, agent.getId()+ "");
                        }else{
                        	ps.setInt(3, agent.getGroupId());
                        	ps.setString(4, agent.getId()+ "");
                        }
                    }  
        });
	}

	public void delete(Integer id) {
		 jdbcTemplate.update(  
	                "delete from a_agent where id = ?",   
	                new Object[]{id},   
	                new int[]{java.sql.Types.INTEGER});  
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

	public void updateCardAgent(String iccids, String agentid) {
		String sql = "update card_agent set  agentid = " +
				agentid + "  where iccid in ("
				+ " select iccid from cmtp  where id in (" + iccids  + "  0 ) )" ;
		jdbcTemplate.update(sql);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<String> queryTypeList() {
		String sql = "select distinct(packageType)  from  cmtp " ;
		final List<String> list = new ArrayList<>();
		jdbcTemplate.query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				list.add(rs.getString("packageType"));
				 return null ;
			}
	});
		return list;
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

	@SuppressWarnings("unchecked")
	public int checkUser(String userNo) {
		final Integer[] total =  {0} ;
		String  sql  = "select count(*) total from a_user a where  userNo =  '" + userNo + "'" ;
		jdbcTemplate.query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				 total[0] = rs.getInt("total");
				 return null ;
			}
		});
		return total[0];
	}
	
	
}
