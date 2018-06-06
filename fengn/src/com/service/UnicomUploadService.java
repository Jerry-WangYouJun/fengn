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
	
	public void  insertMlbRenewData(String  cmtpTable , String historyTable) {
		uploadDao.insertMlbHistory(  cmtpTable ,  historyTable);
	}

	public void insertHistoryList(List<UnicomHistory> historyList) {
		historyDao.deleteAll();
		historyDao.insertBatch(historyList);
		historyDao.insertData();
	}
}
