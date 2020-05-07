package com.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.common.DateUtils;
import com.model.CardDetail;
import com.model.History;
import com.model.InfoPackage;
import com.model.InfoVo;


/**
 * @author lx g
 *
 */
@Repository
public class DataMoveDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	// 存储数据数组List
	List<List<Object>> objectList = new ArrayList<List<Object>>();
	// 存储list
	List<InfoVo> voList = new ArrayList<InfoVo>();
	List<String> iccidList = new ArrayList<String>();
	// 存储当前表的插入语句
	String INSERTSQL = "";
	StringBuffer mailMessage = new StringBuffer("");
	String columuns = "cardcode,	remark,	IMSI,	ICCID,	userStatus,	cardStatus,	gprsUsed,"
			+ "	messageUsed,	openDate,	withMessageService,	withGPRSService,	"
			+ "packageType , apiCode , monthTotalStream , flag ";
	String updateColumns = "cardcode,	remark,	IMSI,	ICCID,	cardStatus,	gprsUsed,"
			+ "	messageUsed,	openDate,	withMessageService,	withGPRSService,	"
			+ "packageType , apiCode , monthTotalStream";

	public void deleteDataTemp(String  table ) {
		String insertNewDataSql = "delete from " +  table;
		try {
			jdbcTemplate.update(insertNewDataSql);
		} catch (Exception e) {
			mailMessage.append("执行中出错：" + e.getMessage());
		}
	}

	public int insertTables(List<InfoVo> list) {
		String insertsqlTemp = "INSERT INTO cmtp ( " + columuns + "  ) "
				+ "VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ? , ?  , 10 , ?)";
		voList = list;
		if (voList != null && voList.size() > 0) {
			jdbcTemplate.batchUpdate(insertsqlTemp,
					new BatchPreparedStatementSetter() {
						public void setValues(PreparedStatement ps, int i) {
							try {
								// 并根据数据类型对Statement 中的占位符进行赋值
								InfoVo info = voList.get(i);
								ps.setString(1,
										String.valueOf(info.getCardCode()).trim());
								ps.setString(2,
										String.valueOf(info.getRemark()));
								ps.setString(3, String.valueOf(info.getIMSI()).trim());
								ps.setString(4, String.valueOf(info.getICCID()).trim());
								ps.setString(5,
										String.valueOf(info.getUserStatus()));
								ps.setString(6,
										String.valueOf(info.getCardStatus()));
								ps.setString(7,
										String.valueOf(info.getGprsUsed()));
								ps.setString(8,
										String.valueOf(info.getMessageUsed()));
								ps.setString(9,
										String.valueOf(info.getOpenDate()));
								ps.setString(10, String.valueOf(info
										.getWithMessageService()));
								ps.setString(11, String.valueOf(info
										.getWithGPRSService()));
								ps.setString(12,
										String.valueOf(info.getPackageType()));
								ps.setString(13,
										String.valueOf(info.getApiCode()));
								ps.setString(14,  info.getFlag());
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

						public int getBatchSize() {
							return voList.size();
						}
					});
			System.out.println("插入数据：" + voList.size() + "条");
		}
		return voList.size();
	}

	public int updateTables(List<InfoVo> list) {
		String updatesqlTemp = "update cmtp set cardcode = ? , remark = ? ,IMSI = ? ,ICCID =? ,"
				+ " cardStatus = ? ,gprsUsed=? ,"
				+ " messageUsed=? ,openDate = ? ,withMessageService=? ,withGPRSService =? ,"
				+ " packageType = ? , apiCode = ? , monthTotalStream = ? , userStatus = ? , flag = ? where iccid = ? ";
		voList = list;
		if (voList != null && voList.size() > 0) {
			jdbcTemplate.batchUpdate(updatesqlTemp,
					new BatchPreparedStatementSetter() {
						public void setValues(PreparedStatement ps, int i) {
							try {
								// 并根据数据类型对Statement 中的占位符进行赋值
								InfoVo info = voList.get(i);
								ps.setString(1,
										String.valueOf(info.getCardCode()));
								ps.setString(2,
										String.valueOf(info.getRemark()));
								ps.setString(3, String.valueOf(info.getIMSI()));
								ps.setString(4, String.valueOf(info.getICCID()));
								// ps.setString(5,
								// String.valueOf(info.getUserStatus()));
								ps.setString(5,
										String.valueOf(info.getCardStatus()));
								ps.setString(6,
										String.valueOf(info.getGprsUsed()));
								ps.setString(7,
										String.valueOf(info.getMessageUsed()));
								ps.setString(8,
										String.valueOf(info.getOpenDate()));
								ps.setString(9, String.valueOf(info
										.getWithMessageService()));
								ps.setString(10, String.valueOf(info
										.getWithGPRSService()));
								ps.setString(11,
										String.valueOf(info.getPackageType()));
								ps.setString(12,
										String.valueOf(info.getApiCode()));
								ps.setString(13, String.valueOf(info
										.getMonthTotalStream()));
								ps.setString(14, String.valueOf(info
										.getUserStatus()));
								ps.setString(15, String.valueOf(info
										.getFlag()));
								ps.setString(16,
										String.valueOf(info.getICCID()));
							} catch (Exception e) {
								System.out.println("问题行是：" + i);
							}
						}

						public int getBatchSize() {
							return voList.size();
						}
					});
			System.out.println("更新数据：" + voList.size() + "条");
		}
		return voList.size();
	}

	public int insertDataTemp(List<List<Object>> listob, String apiCode) {
		String insertsqlTemp = "INSERT INTO  " + apiCode + "_card ( msisdn ,iccid,imsi,opentime,activetime ) "
				+ "VALUES (?,?, ?, ?, ? )";
		objectList = listob;
		// batchUpdate可以高效进行批量插入操作
		try {
			if (objectList != null && objectList.size() > 0) {
				jdbcTemplate.batchUpdate(insertsqlTemp,
						new BatchPreparedStatementSetter() {
							public void setValues(PreparedStatement ps, int i) {
								try {
									System.out.println(i);
									// 并根据数据类型对Statement 中的占位符进行赋值
									List<Object> valueList = objectList.get(i);
										ps.setString(
												1,
												String.valueOf(valueList.get(0))
														.trim());
										ps.setString(2, String
												.valueOf(valueList.get(8)).trim());
										ps.setString(3, String
												.valueOf(valueList.get(9)).trim());
										ps.setString(4, String
												.valueOf(valueList.get(3)).trim());
										ps.setString(5, String
												.valueOf(valueList.get(4)));
								} catch (Exception e) {
									e.printStackTrace();
									System.out.println("出错的" + i + e.getMessage());
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<InfoVo> queryDataList(String selectSql) {
		final List<InfoVo> list = new ArrayList<InfoVo>();
		// Map<String, String> map = new HashMap<String, String>();
		jdbcTemplate.query(selectSql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				InfoVo vo = new InfoVo();
				vo.setId(rs.getString("id"));
				vo.setCardCode(rs.getString("CardCode"));
				vo.setIMSI(rs.getString("IMSI"));
				vo.setICCID(rs.getString("ICCID"));
				vo.setUserStatus(rs.getString("userStatus"));
				vo.setCardStatus(rs.getString("cardStatus"));
				vo.setGprsUsed(rs.getString("gprsUsed"));
				vo.setMessageUsed(rs.getString("messageUsed"));
				vo.setOpenDate(rs.getString("openDate"));
				vo.setWithMessageService(rs.getString("withMessageService"));
				vo.setWithGPRSService(rs.getString("withGPRSService"));
				vo.setPackageType(rs.getString("packageType"));
				vo.setMonthTotalStream(rs.getString("monthTotalStream"));
				vo.setRemark(rs.getString("remark"));
				vo.setStatus(rs.getString("status"));
				vo.setUpdateTime(rs.getString("updateTime"));
				vo.setFlag(rs.getString("flag"));
				vo.setApiCode(rs.getString("apicode"));
				vo.setName(rs.getString("name"));
				Long restDays = 0L;
				String deadLine;
				if (vo.getUpdateTime() == null) {
					int nextYear = Integer.valueOf(DateUtils.formatDateYear(
							"yyyy", vo.getOpenDate())) + 1;
					int curMonth = Integer
							.valueOf(vo.getOpenDate().split("-")[1]);
					int lastMonth = curMonth - 1;
					if (curMonth == 1) {
						lastMonth = 12;
					}
					if (lastMonth > 9) {
						deadLine = DateUtils.getEndDate(String.valueOf(nextYear) + String.valueOf(lastMonth)
								+ "");
					} else {
						deadLine = DateUtils.getEndDate(nextYear + "0"
								+ lastMonth);
					}
					restDays = DateUtils.betweenDays(
							DateUtils.formatDate("yyyyMMdd"), deadLine);

				} else {
					int nextYear = Integer.valueOf(DateUtils.formatDateYear(
							"yyyy", vo.getUpdateTime())) + 1;
					int curMonth = Integer.valueOf(vo.getUpdateTime()
							.split("-")[1]);
					int lastMonth = curMonth - 1;
					if (curMonth == 1) {
						nextYear -= 1 ;
						lastMonth = 12;
					}
					if (lastMonth > 9) {
						deadLine = DateUtils.getEndDate(String.valueOf(nextYear) + String.valueOf(lastMonth)
								+ "");
					} else {
						deadLine = DateUtils.getEndDate(nextYear + "0"
								+ lastMonth);
					}
					restDays = DateUtils.betweenDays(
							DateUtils.formatDate("yyyyMMdd"), deadLine);
				}
				vo.setDateEnd(deadLine.substring(0, 4) + "-"
						+ deadLine.substring(4, 6) + "-"
						+ deadLine.substring(6));
				vo.setRestDay(restDays);
				list.add(vo);
				return null;
			}
		});
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void queryDetail(InfoVo info) {
		String sql = "select id, gprs ,gprsused ,gprsrest  from card_detail where id = "
				+ info.getId();
		/*
		 * List<CardDetail> cardDetailList = jdbcTemplate.queryForList(sql,
		 * CardDetail.class); info.setDetailList(cardDetailList);
		 */
		final List<CardDetail> list = new ArrayList<CardDetail>();
		// Map<String, String> map = new HashMap<String, String>();
		jdbcTemplate.query(sql, new RowMapper() {
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				CardDetail vo = new CardDetail();
				vo.setId(rs.getInt("id"));
				vo.setGprs(rs.getInt("gprs"));
				vo.setGprsused(rs.getDouble("gprsused"));
				vo.setGprsrest(rs.getDouble("gprsrest"));
				list.add(vo);
				return null;
			}
		});
		info.setDetail(list.get(0));
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
		String sql = "select  h.imsi ,  p.package_name pname ,  h.update_date utime  , money  ,p.remark  premark , h.orderNo  orderNo "
				+ " from history  h,  package p    where h.package_id= p.id ";
		if(StringUtils.isNotEmpty(info.getICCID())){
			sql += " and h.iccid = '" + info.getICCID() +  "'";
		}
		if(StringUtils.isNotEmpty(info.getOrderNo())){
			sql += " and h.orderNo = '" + info.getOrderNo() +  "'";
		}
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
				vo.setOrderNo(rs.getString("orderNo"));
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String queryTelphone(String selectSql) {
		 String  tel = jdbcTemplate.queryForObject(selectSql, String.class);
		return tel;
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

	public void insertAgentCard(List<String> list , String apiCode , String pacId) {
		String insertsqlTemp = "INSERT INTO " + apiCode + "_card_agent (  iccid , agentid ,pacid  ) "
				+ "VALUES (?, 1  , " + pacId + " )";
		iccidList = list;
		if (iccidList != null && iccidList.size() > 0) {
			jdbcTemplate.batchUpdate(insertsqlTemp,
					new BatchPreparedStatementSetter() {
						public void setValues(PreparedStatement ps, int i) {
							try {
								System.out.println("ac"+i);
								// 并根据数据类型对Statement 中的占位符进行赋值
								ps.setString(1,
										String.valueOf(iccidList.get(i)));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

						public int getBatchSize() {
							return iccidList.size();
						}
					});
			System.out.println("插入数据：" + iccidList.size() + "条");
		}

	}
	
	public void insertPacageRef(List<String> list , String apiCode , String pacId) {
		String insertsqlTemp = "INSERT INTO  t_package_ref (   agentid ,pacid ,paccost ,pacchildcost,pacrenew,parentagentid) "
				+ "VALUES ( 1  , " + pacId + " , 0  , 0,0,0)";
		iccidList = list;
		if (iccidList != null && iccidList.size() > 0) {
			jdbcTemplate.batchUpdate(insertsqlTemp);
			System.out.println("插入数据：" + iccidList.size() + "条");
		}

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
				map.put("orderNo", rs.getString("orderNo"));
				map.put("name", rs.getString("name"));
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

	public int insertDataMlbFixed(List<List<Object>> listob) {
		String insertsqlTemp = "INSERT INTO  mlb_cmcc_card ( sim ,guid ,createtime,activetime ) "
				+ "VALUES (?,?,  ?, ? )";
		objectList = listob;
		// batchUpdate可以高效进行批量插入操作
		try {
			if (objectList != null && objectList.size() > 0) {
				jdbcTemplate.batchUpdate(insertsqlTemp,
						new BatchPreparedStatementSetter() {
							public void setValues(PreparedStatement ps, int i) {
								try {
									// 并根据数据类型对Statement 中的占位符进行赋值
									List<Object> valueList = objectList.get(i);
										ps.setString(
												1,
												String.valueOf(valueList.get(1))
														.trim());
										ps.setString(2, String
												.valueOf(valueList.get(0)).trim());
										ps.setString(3, String
												.valueOf(valueList.get(12)).trim());
										ps.setString(4, String
												.valueOf(valueList.get(11)).trim());
								} catch (Exception e) {
									e.printStackTrace();
									System.out.println("出错的" + i + e.getMessage());
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
}
