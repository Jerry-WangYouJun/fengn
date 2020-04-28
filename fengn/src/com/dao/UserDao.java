package com.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.common.Dialect;
import com.model.Agent;
import com.model.Pagination;
import com.model.User;


@Repository
public class UserDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public User checkUser(final User user) {
		String sql = "select u.* , a.code ,a.groupId gid from a_user u , a_agent a where u.agentid = a.id"
				+ "  and  u.userNo = '" + user.getUserNo() + "' and u.pwd = '" + user.getPwd() + "' " ;
		jdbcTemplate.query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					user.setId(rs.getInt("id"));
					user.setUserNo(rs.getString("userno"));
					user.setUserName(rs.getString("username"));
					user.setRoleId(rs.getString("roleid"));
					user.setAgentId(rs.getInt("agentid"));
					user.setAgentCode(rs.getString("code"));
					user.setGroupId(rs.getInt("gid"));
				 return null ;
			}
		});
		return user;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Agent> queryList(User user, Pagination page) {
		String sql = "select u.id , u.userno , u.username ,u.pwd , u.roleid , u.agentid , u.agentid "
				+ " , a.name  ,a.code ,a.groupid ,  p.renew , p.typename ,a.type,p.cost , a.telphone,a.rebate from a_user u , a_agent a "
				+ " left join t_package p on  p.id = a.type where u.agentid = a.id   " + whereSql(user);
		String finalSql = Dialect.getLimitString(sql, page.getPageNo(), page.getPageSize(), "MYSQL");
         final  List<Agent> list =   new ArrayList<>();
         jdbcTemplate.query(finalSql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					Agent  vo = new Agent(); 
					vo.setId(rs.getInt("agentid"));
					vo.setUserNo(rs.getString("userno"));
					vo.setName(rs.getString("name"));
//					vo.setAgentName(rs.getString("name"));
//					vo.setRoleId(rs.getString("roleid"));
//					vo.setAgentId(rs.getInt("agentid"));
					vo.setCode(rs.getString("code"));
					vo.setGroupId(rs.getInt("groupid"));
//					String typename = rs.getString("typename");
//					if(StringUtils.isNotEmpty(typename)) {
//						vo.setType(typename);
//					}else {
//						vo.setType(rs.getString("type"));
//					}
//					vo.setRenew(rs.getDouble("renew"));
//					vo.setCost(rs.getDouble("cost"));
					vo.setTelphone(rs.getString("telphone"));
					vo.setRebate(rs.getDouble("rebate"));
					list.add(vo);
				 return null ;
			}
		});
		return list;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int queryTotal(User user) {
		String sql = "select count(*) total from a_user u , a_agent a where u.agentid = a.id  " + whereSql(user);
         final  Integer[] arr =  {0};
         jdbcTemplate.query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					arr[0] = rs.getInt("total");
				 return null ;
			}
		});
		return arr[0];
	}
	
	public void insert(final User user) {
		jdbcTemplate.update("insert into a_user (userno ,username , pwd , roleid , agentid  ) values(?,?,?,?,?)",   
                new PreparedStatementSetter(){  
              
                    @Override  
                    public void setValues(PreparedStatement ps) throws SQLException {  
                        ps.setString(1,  user.getUserNo());  
                        ps.setString(2, user.getUserName()); 
                        ps.setString(3, user.getPwd());
                        ps.setString(4 , user.getRoleId());
                        ps.setInt(5, user.getAgentId());
                    }  
        });  
	}

	public void update(final User user) {
		jdbcTemplate.update("update a_user set  pwd = ?  where id = ? ",   
                new PreparedStatementSetter(){  
                    @Override  
                    public void setValues(PreparedStatement ps) throws SQLException {  
                        ps.setString(1, user.getPwd()); 
                        ps.setInt(2, user.getId());
                    }  
        });
	}
	
	public void updateOpenID(Integer userid , String openid) {
		 jdbcTemplate.update(  
	                "update a_user set  openid = ?  where id = ?",   
	                new Object[]{openid,userid},   
	                new int[]{java.sql.Types.VARCHAR , java.sql.Types.INTEGER});  
	}
	
	public void delete(Integer id) {
		 jdbcTemplate.update(  
	                "delete from a_user where agentid = ?",   
	                new Object[]{id},   
	                new int[]{java.sql.Types.INTEGER});  
	}
	
	public String whereSql(User user){
		String sql = "";
		if(StringUtils.isNotEmpty(user.getUserNo())){
			sql += " and   userno  like  '%" + user.getUserNo() + "%' ";
		}
		if(StringUtils.isNotEmpty(user.getUserName())){
			sql += " and   a.name  like  '%" + user.getUserName() + "%' ";
		}
		if(StringUtils.isNotEmpty(user.getRoleId())){
			sql += " and   u.roleid =  '" + user.getRoleId() + "' ";
		}
		if(StringUtils.isNotEmpty(user.getAgentName())){
			sql += " and   a.name  like  '%" + user.getAgentName() + "%' ";
		}
		if(user.getId()!=null && StringUtils.isNotEmpty(user.getId()+"")){
			sql += " and   u.id =  '" + user.getId() + "' ";
		}
		if(StringUtils.isNotEmpty(user.getAgentCode())){
			sql += " and   code  like   '" + user.getAgentCode() + "%' ";
		}
		return sql;
	}

	public User queryUserByIccId(String iccId) {
		// TODO Auto-generated method stub
		String sql = "select user.* from a_user user  "+
					 "left join a_agent agent on agent.id = user.agentid "+
					 "left join card_agent card on agent.id = card.agentid "+
					 "where card.iccid ="+iccId+"";
		User user = jdbcTemplate.queryForObject(sql, User.class);
		return user;
	}

	public List<User> getRebatePerson(List<String> idList) {
		// TODO Auto-generated method stub
		
		String ids = StringUtils.strip(idList.toString(),"[]");
		List <User> list = new ArrayList<User>();
		String sql = "select user.* from a_user user "+
					 " left join a_agent agent on agent.id = user.agentid"+
					 " where agent.id in ("+ids+")";
		
		list = jdbcTemplate.query(sql,new Object[]{},new BeanPropertyRowMapper<User>(User.class));
		 return list;
	}
	
	public User queryUserByAgentId(int agentId)
	{
		String sql = "select * from a_user user where user.agentid = "+agentId;
		User user = null;
		List <User> list = new ArrayList<User>();  
		list = jdbcTemplate.query(sql, new Object[]{},new BeanPropertyRowMapper<User>(User.class));		
		if(null!=list&&list.size()>0){
			 user = list.get(0);
		}
		return user;
	}
	
}
