package com.service;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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

@Service
public class UnicomUploadService extends DataMoveServiceImpl {
	
	public static void main(String[] args) {
		String s = "7545978.0";
		 System.out.println(getSimIdFormat(s));
	}
	@Autowired
	UnicomUploadDao uploadDao;
	
	@Autowired
	HistoryMapper historyDao ;
	
	@Autowired
	MlbUnicomCardMapper unicomMapper;
	@Autowired
	MlbCmccCardMapper  cmccMapper;

	public String insertUnicomTemp(String type, String table , String createdate) throws Exception {
		String token = ResponseURLDataUtil.getToken();
		List<MlbUnicomCard> mucList = new ArrayList<>();
		//List<MlbUnicomCard> mucInsertList = new ArrayList<>();
		JSONObject jsonForCount = ResponseURLDataUtil.getUnicomAll(1,1, createdate ,token);
		int size = 1000;
		if("null".equals(jsonForCount.getString("result"))) {
			   return  null;
		  }
		int count = ((JSONArray)jsonForCount.get("result")).getJSONObject(0).getInt("records");
		int index = count%size  == 0 ?  count /size : count/size + 1 ;
		int total = 0 ;
		int totalUpdate = 0 ;
		for (int i = 1; i <= index ; i++) {
			System.out.println("读取第"+i+"次数据" + DateUtils.formatDate("yyyyMMddHHmmss"));
			JSONObject json = ResponseURLDataUtil.getUnicomAll(i,size, createdate ,token);
			mucList  = getResultUnicomFromMlb(json);
			List<String> simidList = new ArrayList<>();
			for (MlbUnicomCard mlbUnicomCard : mucList) {
				simidList.add(mlbUnicomCard.getSimid());
			}
			JSONObject bindJson = ResponseURLDataUtil.getBind(1, size, simidList, ContextString.URL_UNICOM_BIND, ResponseURLDataUtil.getToken());
			getResultUnicomBind(bindJson , mucList);
				//break;
				if(mucList.size() > 0){
					System.out.println("开始第"+i+"次操作数据" + DateUtils.formatDate("yyyyMMddHHmmss"));
						int temp = 0 ;
						temp = unicomMapper.insertBatch(mucList);
						System.out.println("操作" + temp + "条");
						total += temp;
						System.out.println("结束第"+i+"次操作数据" + DateUtils.formatDate("yyyyMMddHHmmss"));	
				}
		}
		if("insert".equals(type)){
			totalUpdate = uploadDao.insertUnicomData(table);
		}else{
			totalUpdate = uploadDao.updateUnicomData(table);
		}
	  	return  "共"+ total + "条数据; 更新" + totalUpdate + "条" ;
	}
	
	
	public String insertCmccTemp( String type ,String createDate,String table ) throws Exception {
		List<MlbCmccCard> mccList = new ArrayList<>();
		String token = ResponseURLDataUtil.getToken();
		JSONObject jsonForCount = ResponseURLDataUtil.getCmccCard(1,1,createDate ,token);
		if("null".equals(jsonForCount.getString("result"))) {
			   return  null;
		  }
		int count = ((JSONArray)jsonForCount.get("result")).getJSONObject(0).getInt("records");
		int size = 1000;
		int index = count%size  == 0 ?  count /size : count/size + 1 ;
		int total = 0 ;
		int actual = 0 ;
		for (int i = 1; i <= index ; i++) {
			System.out.println("读取第"+i+"次数据" + DateUtils.formatDate("yyyyMMddHHmmss"));
			JSONObject json = ResponseURLDataUtil.getCmccCard(i,size, createDate ,token);
			mccList = getResultCmccFromMlb(json) ;
			List<String> simidList = new ArrayList<>();
			for (MlbCmccCard cmccCard : mccList) {
				simidList.add(cmccCard.getSimid());
			}
 			JSONObject bindJson = ResponseURLDataUtil.getBind(1, size, simidList, ContextString.URL_CMCC_BIND, ResponseURLDataUtil.getToken());
			getResultCmccBind(bindJson , mccList);
			System.out.println("读取" + mccList.size() + "条");
			//break;
			if(mccList.size() > 0){
				System.out.println("开始第"+i+"次插入数据" + DateUtils.formatDate("yyyyMMddHHmmss"));
					int temp = cmccMapper.insertBatch(mccList);
					System.out.println("插入" + temp + "条");
					total += temp;
					System.out.println("结束第"+i+"次插入数据" + DateUtils.formatDate("yyyyMMddHHmmss"));	
			}
		}
	  	System.out.println("累计插入" + total);
	  	if("insert".equals(type)){
	  		actual += uploadDao.insertCmccData(table);
	  	}else{
	  		actual += uploadDao.updateCmccData(table);
	  	}
	  	return  "共"+ total + "条数据, 操作成功 " + actual + "条"   ;
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
			 muc.setSimid(job.getInt("simId") + "");
			 muc.setHoldname(job.getString("holdName"));
			 muc.setLastactivetime(job.getString("lastActiveTime"));
			 muc.setFlowLeftValue(job.getDouble("flowLeftValue"));
			 muc.setMonthUsageData(job.getDouble("monthUsageData"));
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
			 muc.setSimid(getSimIdFormat(job.getString("simId")));
			 muc.setTotalmonthusageflow(job.getDouble("totalMonthUsageFlow"));
			 muc.setCreateTime(job.getString("createTime"));
			 list.add(muc);
		  } 
		  return list ;
	}
	
	public static String getSimIdFormat(String simId){
		 if(simId.indexOf(".") != -1){
			 return simId.substring(0, simId.indexOf("."));
		 }
		 return simId;
	}
	
	public List<MlbCmccCard> getResultCmccBind(JSONObject json ,List<MlbCmccCard> mcList) {
		if("null".equals(json.getString("result"))) {
			   return  null;
		  }
		JSONArray ja = ((JSONArray)json.get("result"));
		for (MlbCmccCard mlbCmccCard : mcList) {
			for(int i=0;i<ja.size();i++){  
				// 遍历 jsonarray 数组，把每一个对象转成 json 对象  
				JSONObject job = ja.getJSONObject(i);  
				if(job.getString("simId").equals(mlbCmccCard.getSimid())){
					mlbCmccCard.setActiveTime(job.getString("activeTime"));
					mlbCmccCard.setPackagePeriodSrc(job.getString("packagePeriodSrc"));
					mlbCmccCard.setPackagename(job.getString("package"));
					mlbCmccCard.setHoldName(job.getString("holdName"));
				}
			} 
		}
		  
		  return mcList ;
	}
	
	public List<MlbUnicomCard> getResultUnicomBind(JSONObject json ,List<MlbUnicomCard> mcList ) {
		if("null".equals(json.getString("result"))) {
			   return  null;
		  }
		JSONArray ja = ((JSONArray)json.get("result"));
		for (MlbUnicomCard mlbUnicomCard : mcList) {
			for(int i=0;i<ja.size();i++){  
				// 遍历 jsonarray 数组，把每一个对象转成 json 对象  
				JSONObject job = ja.getJSONObject(i);
				if(job.getString("simId").equals(mlbUnicomCard.getSimid())){
					mlbUnicomCard.setSimid(job.getString("simId"));
					mlbUnicomCard.setSim(job.getString("sim"));
					mlbUnicomCard.setOutWarehouseDate(job.getString("OutWarehouseDate"));
					mlbUnicomCard.setFlowlefttime(job.getString("flowLeftTime"));
					mlbUnicomCard.setImsi(job.getString("imsi"));
					mlbUnicomCard.setLastactivetime(job.getString("lastActiveTime"));
				}
			} 
		}
		  return mcList ;
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
