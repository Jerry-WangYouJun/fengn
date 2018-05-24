package com.dao;

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
import com.model.Packages;
import com.model.Pagination;


@Repository
public class PackageDao {
	  
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Packages> queryList(Packages qo, Pagination page ) {
		String sql = "select  *  from t_package where 1=1  " + whereSQL(qo) ;
		String finalSql = Dialect.getLimitString(sql, page.getPageNo(), page.getPageSize(), "MYSQL");
         final  List<Packages> list =   new ArrayList<>();
         jdbcTemplate.query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					Packages  vo = new Packages(); 
					vo.setId(rs.getInt("id"));
					vo.setTypename(rs.getString("typename"));
					vo.setDiscrip(rs.getString("discrip"));
					vo.setCost(rs.getDouble("cost"));
					vo.setRenew(rs.getDouble("renew"));
					vo.setRemark(rs.getString("remark"));
					list.add(vo);
				 return null ;
			}
		});
		return list;
	}
	


	
	public String whereSQL(Packages qo){
		String whereSql = "";
		if(StringUtils.isNotEmpty(qo.getTypename())){
			whereSql += " and   typename  like  '%" + qo.getTypename() + "%' ";
		}
		if(StringUtils.isNotEmpty(qo.getDiscrip())){
			whereSql += " and   discrip  like   '" + qo.getDiscrip() + "-__' ";
			
		}
		if( qo.getId()!=null && qo.getId() > 0){
			whereSql += " and   id =  '" + qo.getId() + "' ";
		}
		return whereSql ;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int queryTotal(Packages qo) {
		final Integer[] total =  {0} ;
		String  sql  = "select count(*) total from t_package a where 1=1 " + whereSQL(qo) ;
		jdbcTemplate.query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				 total[0] = rs.getInt("total");
				 return null ;
			}
		});
		return total[0];
	}

	
}
