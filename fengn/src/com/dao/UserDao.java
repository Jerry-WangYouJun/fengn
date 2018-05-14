package com.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.common.Dialect;
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
	public List<User> queryList(User user, Pagination page) {
		String sql = "select u.id , u.userno , u.username ,u.pwd , u.roleid , u.agentid , u.agentid "
				+ " , a.name  ,a.code ,  p.renew , p.typename ,a.type,p.cost from a_user u , a_agent a "
				+ " left join t_package p on  p.id = a.type where u.agentid = a.id   " + whereSql(user);
		String finalSql = Dialect.getLimitString(sql, page.getPageNo(), page.getPageSize(), "MYSQL");
         final  List<User> list =   new ArrayList<>();
         jdbcTemplate.query(finalSql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					User  vo = new User(); 
					vo.setId(rs.getInt("id"));
					vo.setUserNo(rs.getString("userno"));
					vo.setUserName(rs.getString("username"));
					vo.setAgentName(rs.getString("name"));
					vo.setRoleId(rs.getString("roleid"));
					vo.setAgentId(rs.getInt("agentid"));
					vo.setAgentCode(rs.getString("code"));
					String typename = rs.getString("typename");
					if(StringUtils.isNotEmpty(typename)) {
						vo.setType(typename);
					}else {
						vo.setType(rs.getString("type"));
					}
					vo.setRenew(rs.getDouble("renew"));
					vo.setCost(rs.getDouble("cost"));
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
}
