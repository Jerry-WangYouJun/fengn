/**
 * 
 */
package com.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.common.DateUtils;
import com.common.Dialect;
import com.common.ImportExcelUtil;
import com.common.StringUtils;
import com.dao.DataMoveDao;
import com.model.InfoVo;
import com.model.Pagination;

/**
 * @author lx g
 *
 */
@Service("dataMoveService")
public class DataMoveServiceImpl implements DataMoveService {
	public Properties pro = new Properties();
	@Autowired
	public DataMoveDao dataMoveDao;
	
	StringBuffer message;


	public Properties initParam() throws IOException {
		// 获取properties配置文件
		InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream("tableList.properties");
		// 初始化properties对象
		Properties p = new Properties();
		// 根据配置文件获取properties对象
		p.load(inputStream);
		return p;
	}

	@Override
	public void deleteDataTemp(String table) {
		dataMoveDao.deleteDataTemp(table);
	}
	
	@Override
	public void insertDataToTemp(List<List<Object>> listob , String apiCode) {
		dataMoveDao.insertDataTemp(listob , apiCode);
	}

	@Override
	public int updateExistData(String apiCode) {
		String updateSql = "select t.* from cmtp c , cmtp_temp t where c.iccid = t.iccid and  "
				+ "  ( c.cardcode != t.cardcode or  c.remark !=t.remark or  "
				+ "  c.IMSI !=t.IMSI or c.ICCID !=t.ICCID or  c.cardStatus !=t.cardStatus or "
				+ "	 c.gprsUsed !=t.gprsUsed or  c.messageUsed !=t.messageUsed or "
				+ "	 c.openDate !=t.openDate or c.withMessageService !=t.withMessageService or "
				+ "  c.withGPRSService !=t.withGPRSService or  c.packageType !=t.packageType OR "
				+ "  c.monthTotalStream !=t.monthTotalStream  or c.updateTime != t.updateTime or "
				+ "  c.userStatus != t.userStatus or c.flag != t.flag ) ";
		System.out.println(updateSql);
		List<InfoVo>  list =  dataMoveDao.queryDataList(updateSql);
		return dataMoveDao.updateTables(list);
	}


	/**
	 * @param tableName
	 *            需要进行转移而表名
	 */
	public int dataMoveSql2Oracle(String apiCode) {
		int num = 0 ;
		try {
			String insertNewDataSql = "select *  from cmtp_temp where  iccid not in (select iccid from cmtp ) " ;
			System.out.println(insertNewDataSql);
			List<InfoVo>  list =  dataMoveDao.queryDataList(insertNewDataSql);
			num = dataMoveDao.insertTables(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  num ;
	}


	@Override
	public List<InfoVo> queryDataList(String dateBegin, String dateEnd, String status , Pagination pagination  , String iccid ) {
		String wheresql = "" ; 
		if(StringUtils.isNotEmpty(dateBegin)){
			 wheresql += " and openDate >= '" + dateBegin + "' " ;
		}
		if(StringUtils.isNotEmpty(dateEnd)){
			 wheresql += " and  openDate <= '" + dateEnd + "' " ;
		}
		if(StringUtils.isNotEmpty(iccid)){
			 wheresql += " and  iccid  like  '%" + iccid + "%' " ;
		}
		if(StringUtils.isNotEmpty(status)){
			 if("1".equals(status)){
				 wheresql += " and status is not null "  ;
			 }else{
				 wheresql += " and  status is null "  ;
			 }
		}
		String selectSql = "select * from cmtp where 1=1 " + wheresql ; 
		String finalSql = Dialect.getLimitString(selectSql, pagination.getPageNo(), pagination.getPageSize(), "MYSQL");
		return  dataMoveDao.queryDataList(finalSql );
	}
	
	@Override
	public int queryDataSize(String dateBegin, String dateEnd, String status ,String iccid ) {
		String wheresql = "" ; 
		if(StringUtils.isNotEmpty(dateBegin)){
			 wheresql += " and openDate >= '" + dateBegin  + "' ";
		}
		if(StringUtils.isNotEmpty(dateEnd)){
			 wheresql += " and  openDate <= '" + dateEnd  + "' ";
		}
		if(StringUtils.isNotEmpty(iccid)){
			 wheresql += " and  iccid  like  '%" + iccid + "%' " ;
		}
		if(StringUtils.isNotEmpty(status)){
			 if("1".equals(status)){
				 wheresql += " and status is not null "  ;
			 }else{
				 wheresql += " and  status is null "  ;
			 }
		}
		String selectSql = "select count(1) total from cmtp where 1=1 " + wheresql ; 
		return  dataMoveDao.queryTotal(selectSql );
	}

	@Override
	public void updateDataStatus(String id , String color) {
		String updateDataSql = "UPDATE cmtp SET status =  '" + color + "'"
				+ " WHERE id = " + id  ;
		dataMoveDao.update(updateDataSql);
	}

	@Override
	public void insertAgentCard(List<List<Object>> listob , String apiCode  , String pacId) {
		try {
			List<String>  list = new ArrayList<>();
			for (int i = 0; i < listob.size(); i++) {
				list.add(listob.get(i).get("cmcc".equals(apiCode)?0:8).toString());
			}
			dataMoveDao.insertAgentCard(list ,apiCode , pacId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void insertPacageRef(List<List<Object>> listob , String apiCode  , String pacId) {
		try {
			List<String>  list = new ArrayList<>();
			for (int i = 0; i < listob.size(); i++) {
				list.add(listob.get(i).get("cmcc".equals(apiCode)?0:8).toString());
			}
			dataMoveDao.insertPacageRef(list ,apiCode , pacId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<List<Object>>  getDataList(MultipartHttpServletRequest multipartRequest,
			HttpServletResponse response ) throws Exception {
		PrintWriter out = null;
		response.setCharacterEncoding("utf-8");
		out = response.getWriter();
		InputStream in = null;
		List<List<Object>> listob = null;
		MultipartFile file = multipartRequest.getFile("upfile");
		if (file == null  || file.isEmpty()) {
			out.print("未添加上传文件或者文件中内容为空！");
			out.flush();
			out.close();
			return  null;
		}
		in = file.getInputStream();
		System.out.println("导入表数据开始：" + DateUtils.formatDate("MM-dd:HH-mm-ss"));
		listob = new ImportExcelUtil().getBankListByExcel(in,
				file.getOriginalFilename());
		in.close();
		return listob ;
	}

	@Override
	public void insertDataToCmoitCart(List<List<Object>> listob) {
		dataMoveDao.insertDataTemp(listob , "");
	}

	@Override
	public void uploadData(List<List<Object>> listob, String apiCode , String pacId) {
		if("cmcc".equals(apiCode)) {
			System.out.println("插入代理商卡数据开始：" + System.currentTimeMillis());
			insertDataToMlbIFixedCart(listob );
		}else {
			System.out.println("插入代理商卡数据开始：" + System.currentTimeMillis());
			insertDataToCmoitCart(listob );
		}
		System.out.println("插入新数据开始：" + System.currentTimeMillis());
		insertAgentCard(listob  , apiCode , pacId);
//		insertPacageRef(listob  , apiCode , pacId);
		
	}

	@Override
	public void insertDataToMlbIFixedCart(List<List<Object>> listob) {
		dataMoveDao.insertDataMlbFixed(listob );
		
	}
}
