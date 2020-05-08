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
import com.model.Rebate;


@Repository
public class PackageDao {
	  
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Packages> queryList(Packages qo, Pagination page ) {
		String sql = "";
		if("1".equals(qo.getAgentId() )) {
			qo.setAgentId(null);
			sql = "SELECT * FROM   t_package where 1=1  " ;
		}else {
			sql = "SELECT id , typename ,discrip , r.paccost cost , r.pacrenew renew ,remark  FROM t_package_ref r  join t_package p ON r.pacid=p.id where 1=1  " + whereSQL(qo) ;
		}
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
					//vo.setAgentId(rs.getString("agentid"));
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
		String sql = "";
		if("1".equals(qo.getAgentId() )) {
			qo.setAgentId(null);
			sql = "SELECT count(*) total FROM   t_package where 1=1  " + whereSQL(qo) ;
		}else {
			sql = "SELECT count(*) total FROM t_package_ref r  join t_package p ON r.pacid=p.id where 1=1  " + whereSQL(qo) ;
		}
		jdbcTemplate.query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				 total[0] = rs.getInt("total");
				 return null ;
			}
		});
		return total[0];
	}


	public void insertPacRef(String pacids, String agentid ,String parentAgentId , Double childcost) {
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
		sql = "insert into t_package_ref(pacid,paccost,agentid,parentagentid, pacrenew , pacchildcost ) "
				+ "values( "+pacids+",  " + childcost + ",'" +agentid + "' , '"+parentAgentId+"'  , 0 , 0  )" ;

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
	
	public Rebate queryByIccId(String iccId) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("select t_package_ref.*,a_user.openId,cmoit_card_agent.iccid,(t_package_ref.pacrenew - t_package_ref.paccost) as amount from cmoit_card_agent ");
		sb.append(" left join t_package_ref on cmoit_card_agent.pacid = t_package_ref.pacid ");
		sb.append(" left join a_user  on a_user.agentid = cmoit_card_agent.agentid ");
		sb.append(" where  t_package_ref.agentid = cmoit_card_agent.agentid ");
		sb.append("and iccid =? ");
		String sql =sb.toString();
		System.out.println("sql========"+sql); 
		Rebate rebate = jdbcTemplate.queryForObject(sql, new RowMapper<Rebate>() {
			@Override
			public Rebate mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				Rebate rebate = new Rebate();
				rebate.setAgentId(rs.getInt("agentid"));
				rebate.setAmount(rs.getDouble("amount"));
				rebate.setIccId(rs.getString("iccId"));
				rebate.setParentAgentId(rs.getInt("parentagentId"));
				rebate.setOpenId(rs.getString("openId"));
				rebate.setPackageId(rs.getInt("pacid"));
				rebate.setPaccost(rs.getDouble("paccost"));
				rebate.setPacchildcost(rs.getDouble("pacchildcost"));
				rebate.setPacrenew(rs.getDouble("pacrenew"));				
				return rebate;
			}			
		},iccId);
		return rebate;
	}





	public Rebate queryByAgentIdAndPacId(Rebate rebatePerson) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("select t_package_ref.*,a_user.openId,("+rebatePerson.getPaccost()+" - t_package_ref.paccost) as amount from t_package_ref ");
		sb.append(" left join a_user  on a_user.agentid = t_package_ref.agentid ");
		sb.append(" where  a_user.agentid ="+rebatePerson.getParentAgentId());
		sb.append(" and pacid ="+rebatePerson.getPackageId());
		String sql = sb.toString();
		
		Rebate rebate = jdbcTemplate.queryForObject(sql, new RowMapper<Rebate>() {
			@Override
			public Rebate mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				Rebate rebate = new Rebate();
				rebate.setAgentId(rs.getInt("agentid"));
				rebate.setAmount(rs.getDouble("amount"));
				rebate.setIccId(rebatePerson.getIccId());
				rebate.setParentAgentId(rs.getInt("parentagentId"));
				rebate.setOpenId(rs.getString("openId"));
				rebate.setPackageId(rs.getInt("pacid"));
				rebate.setPaccost(rs.getDouble("paccost"));
				rebate.setPacchildcost(rs.getDouble("pacchildcost"));
				rebate.setPacrenew(rs.getDouble("pacrenew"));				
				return rebate;
			}			
		});
		return rebate;
	}





	public int queryByPacIdAndAgentId(String pacids, String agentid) {
		// TODO Auto-generated method stub
		String sql = "select count(*) from t_package_ref where agentid = '"+agentid+"' and pacid ='"+pacids+"'";
		System.out.println(sql+"&&&&&&&&&");
		int count = jdbcTemplate.queryForInt(sql);
		return count;
	}




	/**
	 * 查询麦联宝 
	 * @param iccId
	 * @return
	 */
	public Rebate queryMlbByIccId(String iccId) {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("select t_package_ref.*,a_user.openId,cmcc_card_agent.iccid,t_package.typename, ");
		sb.append(" (t_package_ref.pacrenew - t_package_ref.paccost) as amount from cmcc_card_agent ");
		sb.append(" left join t_package_ref on cmcc_card_agent.pacid = t_package_ref.pacid ");
		sb.append(" left join a_user  on a_user.agentid = cmcc_card_agent.agentid ");
		sb.append(" left join t_package on t_package.id = cmcc_card_agent.pacid ");
		sb.append(" where  t_package_ref.agentid = cmcc_card_agent.agentid ");
		sb.append("and iccid =? ");
		String sql =sb.toString();
		System.out.println("sql========"+sql); 
		Rebate rebate = jdbcTemplate.queryForObject(sql, new RowMapper<Rebate>() {
			@Override
			public Rebate mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				Rebate rebate = new Rebate();
				rebate.setAgentId(rs.getInt("agentid"));
				rebate.setAmount(rs.getDouble("amount"));
				rebate.setIccId(rs.getString("iccId"));
				rebate.setParentAgentId(rs.getInt("parentagentId"));
				rebate.setOpenId(rs.getString("openId"));
				rebate.setPackageId(rs.getInt("pacid"));
				rebate.setPaccost(rs.getDouble("paccost"));
				rebate.setPacchildcost(rs.getDouble("pacchildcost"));
				rebate.setPacrenew(rs.getDouble("pacrenew"));	
				rebate.setPacTypeName(rs.getString("typename"));			
				return rebate;
			}			
		},iccId);
		return rebate;
	}



}
