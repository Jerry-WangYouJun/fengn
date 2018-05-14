package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.HistoryMapper;
import com.dao.UnicomUploadDao;
import com.model.UnicomHistory;

@Service
public class UnicomUploadService extends DataMoveServiceImpl {
	
	@Autowired
	UnicomUploadDao uploadDao;
	
	@Autowired
	HistoryMapper historyDao ;

	public String insertUnicomList(List<List<Object>> listObject, String agentId) {
	  	int total =  uploadDao.insertDataTemp(listObject);
	  	int actual = uploadDao.insertData();
	  	int aaa  =  uploadDao.insertAgentCard(agentId);
	  	return  "共"+ total + "条数据, 插入成功 " + actual + "条" ;
	}

	public String updateUnicomList(List<List<Object>> listObject) {
		int total =  uploadDao.insertDataTemp(listObject);
	  	int actual = uploadDao.updateData();
	  	return  "共"+ total + "条数据, 更新成功 " + actual + "条" ;
	}
	
	public void  insertMlbRenewData(String  cmtpTable , String historyTable) {
		uploadDao.insertMlbHistory(  cmtpTable ,  historyTable);
	}

	public void insertHistoryList(List<UnicomHistory> historyList) {
		historyDao.deleteAll();
		historyDao.insertBatch(historyList);
		historyDao.insertData();
	}
}
