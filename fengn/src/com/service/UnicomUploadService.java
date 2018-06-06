package com.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.ResponseURLDataUtil;
import com.dao.HistoryMapper;
import com.dao.MlbCmccCardMapper;
import com.dao.MlbUnicomCardMapper;
import com.dao.UnicomUploadDao;
import com.model.MlbCmccCard;
import com.model.MlbUnicomCard;
import com.model.UnicomHistory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class UnicomUploadService extends DataMoveServiceImpl {
	
	public static void main(String[] args) {
		int count = 30000 ;
		int index = count%10000  == 0 ?  count /10000 : count/10000 + 1 ;
		 System.out.println(index);
	}
	@Autowired
	UnicomUploadDao uploadDao;
	
	@Autowired
	HistoryMapper historyDao ;
	
	@Autowired
	MlbUnicomCardMapper unicomMapper;
	@Autowired
	MlbCmccCardMapper  cmccMapper;

	public String insertUnicomList(List<List<Object>> listObject, String agentId ,String table) throws UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer();
		List<MlbUnicomCard> mucList = new ArrayList<>();
		JSONObject jsonForCount = ResponseURLDataUtil.getUnicomCard(1,1, "", "2,3,4");
		int count = ((JSONArray)jsonForCount.get("result")).getJSONObject(0).getInt("records");
		int index = count%10000  == 0 ?  count /10000 : count/10000 + 1 ;
		for (int i = 1; i <= index ; i++) {
			JSONObject json = ResponseURLDataUtil.getUnicomCard(i,10000, "", "2,3,4");
			mucList.addAll(getResultUnicomFromMlb(json)) ;
			//break;
		}
		List<MlbUnicomCard> mucInsertList = new ArrayList<>();
		for(List<Object> iccid : listObject) {
			 for (MlbUnicomCard mu : mucList) {
				  if(iccid.get(0).equals(mu.getGuid())) {
					  mucInsertList.add(mu);
				  }
			}
		}
	  	int total =  unicomMapper.insertBatch(mucInsertList);
	  	int actual = uploadDao.insertData(table);
	  	int aaa  =  uploadDao.insertAgentCard(agentId , table );
	  	
	  	return  "共"+ total + "条数据, 插入成功 " + actual + "条" ;
	}
	
	public String insertCmccList(List<List<Object>> listObject, String agentId ,String table) throws UnsupportedEncodingException {
		List<MlbCmccCard> mccList = new ArrayList<>();
		JSONObject jsonForCount = ResponseURLDataUtil.getCmccCard(1,1, "", "all");
		int count = ((JSONArray)jsonForCount.get("result")).getJSONObject(0).getInt("records");
		int index = count%10000  == 0 ?  count /10000 : count/10000 + 1 ;
		for (int i = 1; i <= index ; i++) {
			JSONObject json = ResponseURLDataUtil.getCmccCard(i,1000, "", "all");
			mccList.addAll(getResultCmccFromMlb(json)) ;
			break;
		}
		List<MlbCmccCard> mccInsertList = new ArrayList<>();
		for(List<Object> iccid : listObject) {
			 for (MlbCmccCard mc : mccList) {
				  if(iccid.get(0).equals(mc.getGuid())) {
					  mccInsertList.add(mc);
				  }
			}
		}
	  	int total =  cmccMapper.insertBatch(mccInsertList);
	  	int actual = uploadDao.insertCmccData(table);
	  	int aaa  =  uploadDao.insertAgentCard(agentId , table );
	  	
	  	return  "共"+ total + "条数据, 插入成功 " + actual + "条" ;
	}

	public List<MlbUnicomCard> getResultUnicomFromMlb(JSONObject json) {
		if("null".equals(json.getString("result"))) {
			   return  null;
		  }
		JSONArray ja = ((JSONArray)json.get("result")).getJSONArray(1);
		List<MlbUnicomCard>  list = new ArrayList<>();
		  for(int i=0;i<ja.size();i++){  
			  // 遍历 jsonarray 数组，把每一个对象转成 json 对象  
			 JSONObject job = ja.getJSONObject(i);  
			 MlbUnicomCard muc = new MlbUnicomCard();
			 muc.setAmountusagedata(job.getDouble("amountUsageData"));
			 muc.setDayusagedata(job.getDouble("dayUsageData"));
			 muc.setExpiretime(job.getString("expireTime"));
			 muc.setGuid(job.getString("guid"));
			 muc.setOddtime(job.getString("oddTime"));
			 muc.setPackagename(job.getString("package"));
			 muc.setSimstate(job.getString("simState"));
			 muc.setTotalmonthusageflow(job.getDouble("totalMonthUsageFlow"));
			 list.add(muc);
		  } 
		  return list ;
	}
	
	public List<MlbCmccCard> getResultCmccFromMlb(JSONObject json) {
		if("null".equals(json.getString("result"))) {
			   return  null;
		  }
		JSONArray ja = ((JSONArray)json.get("result")).getJSONArray(1);
		List<MlbCmccCard>  list = new ArrayList<>();
		  for(int i=0;i<ja.size();i++){  
			  // 遍历 jsonarray 数组，把每一个对象转成 json 对象  
			 JSONObject job = ja.getJSONObject(i);  
			 MlbCmccCard muc = new MlbCmccCard();
			 muc.setAmountusagedata(job.getDouble("amountUsageData"));
			 muc.setBootstate(job.getString("bootState"));
			 muc.setExpiretime(job.getString("expireTime"));
			 muc.setFlowleftvalue(job.getDouble("flowLeftValue"));
			 muc.setGuid(job.getString("guid"));
			 muc.setMonthusagedata(job.getDouble("monthUsageData"));
			 muc.setOddtime(job.getString("oddTime"));
			 muc.setPackagename(job.getString("package"));
			 muc.setSim(job.getString("sim"));
			 muc.setSimid(job.getInt("simId"));
			 muc.setTotalmonthusageflow(job.getDouble("totalMonthUsageFlow"));
			 list.add(muc);
		  } 
		  return list ;
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
