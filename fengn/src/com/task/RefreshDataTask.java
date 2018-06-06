package com.task;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.common.DateUtils;
import com.dao.TTaskPointMapper;

public class RefreshDataTask {
	@Autowired
	QueryMlbNewData service ;
	
	@Autowired
	private TransactionTemplate transactionTemplate;
	
	@Autowired
	TTaskPointMapper task;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void myExecutor(){  
			Calendar calendar = Calendar.getInstance();
			int curHour = calendar.get(Calendar.HOUR_OF_DAY);
		    final Map<String,String> map = new HashMap<>();
	        String timeStart =DateUtils.formatDate("yyyy-MM-dd ") + (curHour -1) + ":00:00";
	        	String timeEnd = DateUtils.formatDate("yyyy-MM-dd " + (curHour -1) + ":59:59"); ;
	        List<Map<String,String>> logList = task.selectStartTime();
	        String successTime = "";
	        String failedTime ="";
	        for(Map<String,String> mapTemp : logList) {
	        		   if("failed".equals(mapTemp.get("error"))) {
	        			   failedTime = mapTemp.get("startTime");
	        		   }
	        		   
	        		   if("success".equals(mapTemp.get("error"))) {
	        			   successTime = mapTemp.get("startTime");
	        		   }
	        }
	        int flag = successTime.compareTo(failedTime);
	        if(flag >= 0 ) {
		        	map.put("start", timeStart);
	        }else {
	        		map.put("start", failedTime);
	        }
	        map.put("end", timeEnd);
			this.transactionTemplate.execute(new TransactionCallback() {
	            public Object doInTransaction(TransactionStatus transactionStatus) {
	      		  try{
	      			  return  service.getData(map.get("start") , map.get("end"));
	      		  }catch (Exception e) {
					 return e.getMessage();
				}
	         }
	        });
  }  
}
