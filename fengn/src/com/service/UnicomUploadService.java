package com.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.ContextString;
import com.common.DateUtils;
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

	public String insertUnicomList(List<List<Object>> listObject, String agentId ,String table) throws Exception {
		StringBuffer sb = new StringBuffer();
		String token = ResponseURLDataUtil.getToken();
		List<MlbUnicomCard> mucList = new ArrayList<>();
		//List<MlbUnicomCard> mucInsertList = new ArrayList<>();
		JSONObject jsonForCount = ResponseURLDataUtil.getUnicomCard(1,1, "", "2,3,4" ,token);
		int size = 1000;
		int count = ((JSONArray)jsonForCount.get("result")).getJSONObject(0).getInt("records");
		int index = count%size  == 0 ?  count /size : count/size + 1 ;
		int total = 0 ;
		for (int i = 1; i <= index ; i++) {
			JSONObject json = ResponseURLDataUtil.getUnicomCard(i,size, "", "2,3,4" ,token);
			mucList  = getResultUnicomFromMlb(json);
//			for (MlbUnicomCard mu : mucList) {
//				for(List<Object> iccid : listObject) {
//					  if(iccid.get(0).equals(mu.getGuid())) {
//						  mucInsertList.add(mu);
//					  }
//				}
//			}
			 total +=  unicomMapper.insertBatch(mucList);
		}
	  	int actual = uploadDao.insertData(table);
	  	int aaa  =  uploadDao.insertAgentCard(agentId , table );
	  	
	  	return  "共"+ total + "条数据, 插入成功 " + actual + "条" ;
	}
	
	public String insertCmccList(List<List<Object>> listObject, String agentId ,String table) throws Exception {
//		List<MlbCmccCard> mccList = new ArrayList<>();
//		String token = ResponseURLDataUtil.getToken();
//		JSONObject jsonForCount = ResponseURLDataUtil.getCmccCard(1,1, "", "all" ,token);
//		int count = ((JSONArray)jsonForCount.get("result")).getJSONObject(0).getInt("records");
//		int size = 10000;
//		int index = count%size  == 0 ?  count /size : count/size + 1 ;
//		int total = 0 ;
//		for (int i = 1; i <= index ; i++) {
//			System.out.println("读取第"+i+"次数据" + DateUtils.formatDate("yyyyMMddHHmmss"));
//			JSONObject json = ResponseURLDataUtil.getCmccCard(i,size, "", "all" ,token);
//			mccList = getResultCmccFromMlb(json) ;
//			System.out.println("读取" + mccList.size() + "条");
//			//break;
//			if(mccList.size() > 0){
//				System.out.println("开始第"+i+"次插入数据" + DateUtils.formatDate("yyyyMMddHHmmss"));
//					int temp = cmccMapper.insertBatch(mccList);
//					System.out.println("插入" + temp + "条");
//					total += temp;
//					System.out.println("结束第"+i+"次插入数据" + DateUtils.formatDate("yyyyMMddHHmmss"));	
//			}
//		}
//	  	System.out.println("累计插入" + total);
//	  	int actual = uploadDao.insertCmccData(table);
//	  	int aaa  =  uploadDao.insertAgentCard(agentId , table );
	  	return updateCmccBySIM("");
	  	//return  "共"+ total + "条数据, 插入成功 " + actual + "条"   ;
	}
	
	public String updateCmccBySIM(String createdate) throws UnsupportedEncodingException, Exception {
		List<String> simList = cmccMapper.selectNoSIM(createdate);
		int size = 1000;
		int index = simList.size()%size  == 0 ?  simList.size() /size : simList.size()/size + 1 ;
		int total = 0 ;
		for (int i = 1; i <= index ; i++) {
			Map map = new HashMap();
			map.put("simIds", simList.subList(0, 1000));
			System.out.println("开始第"+i+"次更新数据" + DateUtils.formatDate("yyyyMMddHHmmss"));
			JSONObject json = ResponseURLDataUtil.getMLBData(ResponseURLDataUtil.getToken(),ContextString.URL_CMCC_BIND , map);
			List<MlbCmccCard> list =getResultCmccBind(json);
					int temp = cmccMapper.updateBatch(list);
					System.out.println("更新" + temp + "条");
					total += temp;
					System.out.println("结束第"+i+"次更新数据" + DateUtils.formatDate("yyyyMMddHHmmss"));	
		}
	  	System.out.println("累计更新" + total);
	  	return "更新" + total + "条数据";
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
			 muc.setCreateTime(job.getString("createTime"));
			 list.add(muc);
		  } 
		  return list ;
	}
	public List<MlbCmccCard> getResultCmccBind(JSONObject json) {
		if("null".equals(json.getString("result"))) {
			   return  null;
		  }
		JSONArray ja = ((JSONArray)json.get("result"));
		List<MlbCmccCard>  list = new ArrayList<>();
		  for(int i=0;i<ja.size();i++){  
			  // 遍历 jsonarray 数组，把每一个对象转成 json 对象  
			 JSONObject job = ja.getJSONObject(i);  
			 MlbCmccCard muc = new MlbCmccCard();
			 muc.setActiveTime(job.getString("activeTime"));
			 muc.setPackagePeriodSrc(job.getString("packagePeriodSrc"));
			 muc.setPackagename(job.getString("package"));
			 muc.setHoldName(job.getString("holdName"));
			 muc.setSimid(job.getInt("simId"));
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
