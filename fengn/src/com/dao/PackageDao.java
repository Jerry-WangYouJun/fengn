package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.model.User;
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
		String sql = "SELECT * FROM t_package_ref r left join t_package p ON r.pacid=p.id where 1=1  " + whereSQL(qo) ;
		String finalSql = Dialect.getLimitString(sql, page.getPageNo(), page.getPageSize(), "MYSQL");
         final  List<Packages> list =   new ArrayList<>();
         jdbcTemplate.query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					Packages  vo = new Packages(); 
					vo.setId(rs.getInt("id"));
					vo.setTypename(rs.getString("typename"));
					vo.setDiscrip(rs.getString("discrip"));
					vo.setCost(rs.getDouble("paccost"));
					vo.setRenew(rs.getDouble("pacrenew"));
					vo.setChildcost(rs.getDouble("pacchildcost"));
					vo.setRemark(rs.getString("remark"));
					vo.setAgentId(rs.getString("agentid"));
					list.add(vo);
				 return null ;
			}
		});
		return list;
	}

	


	
	public String whereSQL(Packages qo){
		String whereSql = "";
		if(StringUtils.isNotEmpty(qo.getTypename())){
			whereSql += " and   p.typename  like  '%" + qo.getTypename() + "%' ";
		}
		if(StringUtils.isNotEmpty(qo.getDiscrip())){
			whereSql += " and   p.discrip  like   '" + qo.getDiscrip() + "-__' ";
			
		}
		if( qo.getId()!=null && qo.getId() > 0){
			whereSql += " and   p.id =  '" + qo.getId() + "' ";
		}
		if(qo.getAgentId()!=null ){
			whereSql += " and   r.agentid = '"+qo.getAgentId() +"'";
		}
		return whereSql ;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int queryTotal(Packages qo) {
		final Integer[] total =  {0} ;
		String  sql  = "SELECT count(*) total FROM t_package_ref r left join t_package p ON r.pacid=p.id where 1=1 " + whereSQL(qo) ;
		jdbcTemplate.query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				 total[0] = rs.getInt("total");
				 return null ;
			}
		});
		return total[0];
	}


	public void insertPacRef(String pacids, String agentid ,String parentAgentId) {
		String sql = "";
//		if("1".equals(agentid)){
////			sql = "insert into t_package_ref(pacid,paccost,agentid,parentagentid) select id,childcost,'" +
////					agentid + "' , '"+parentAgentId+"' from t_package where id in (" + pacids  + ")" ;
//			sql = "insert into t_package_ref(pacid,paccost,agentid,parentagentid) select pacid,pacchildcost,'" +
//					agentid + "' , '"+parentAgentId+"' from t_package_ref where pacid in (" + pacids  + ") and parentagentid='0'" ;
//		}else{
//			sql = "insert into t_package_ref(pacid,paccost,agentid,parentagentid) select pacid,pacchildcost,'" +
//					agentid + "' , '"+parentAgentId+"' from t_package_ref where pacid in (" + pacids  + ") and parentagentid='"+parentAgentId+"'" ;
//		}
		sql = "insert into t_package_ref(pacid,paccost,agentid,parentagentid) select pacid,pacchildcost,'" +
				agentid + "' , '"+parentAgentId+"' from t_package_ref where pacid in (" + pacids  + ") and agentid='"+parentAgentId+"'" ;

		jdbcTemplate.update(sql);
	}

	public void updateChildsPacRef(Packages pac) {
		String sql = "";
		if("1".equals(pac.getAgentId())){
			sql = "update t_package_ref SET paccost='"+ pac.getCost() + "' ,pacchildcost='"+ pac.getChildcost()+"'"+
					",pacrenew='"+pac.getRenew() + "' where agentid='"+pac.getAgentId()+"' and pacid='"+pac.getId()+"'";
			jdbcTemplate.update(sql);
		}else{
			sql = "update t_package_ref SET pacchildcost='"+ pac.getChildcost()+"'"+
					",pacrenew='"+pac.getRenew() + "' where agentid='"+pac.getAgentId()+"' and pacid='"+pac.getId()+"'";
			jdbcTemplate.update(sql);
		}
		sql = "update t_package_ref SET paccost='"+ pac.getChildcost()+"'"+
				",pacrenew='"+pac.getRenew() + "' where parentagentid='"+pac.getAgentId()+"' and pacid='"+pac.getId()+"'";
		jdbcTemplate.update(sql);

	}

	public void insertChildsPacRef(Packages pac) {
		String sql = "insert into t_package_ref(pacid,paccost,agentid,parentagentid,pacchildcost,pacrenew) select max(id),"+
				" '"+pac.getCost()+"','"+pac.getAgentId()+"','0','"+pac.getChildcost()+"','"+pac.getRenew()+"' from t_package";
		jdbcTemplate.update(sql);
	}

	public List<Packages> getPacListByAgentId(String agentId) {
		String sql = "SELECT * FROM t_package_ref r left join t_package p ON r.pacid=p.id where 1=1 and r.agentid ='"+agentId+"' " ;
		final  List<Packages> list =   new ArrayList<>();
		jdbcTemplate.query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				Packages  vo = new Packages();
				vo.setId(rs.getInt("id"));
				vo.setTypename(rs.getString("typename"));
				vo.setDiscrip(rs.getString("discrip"));
				vo.setCost(rs.getDouble("paccost"));
				vo.setRenew(rs.getDouble("pacrenew"));
				vo.setChildcost(rs.getDouble("pacchildcost"));
				vo.setRemark(rs.getString("remark"));
				vo.setAgentId(rs.getString("agentid"));
				list.add(vo);
				return null ;
			}
		});
		return list;
	}
}
