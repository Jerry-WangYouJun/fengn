package com.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.DateUtils;
import com.dao.TTaskPointMapper;
import com.task.QueryMlbNewData;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/task")
public class TaskActiveController {
	 
	@Autowired
	QueryMlbNewData service ;
	
	@Autowired
	TTaskPointMapper task;
	
	@Autowired
	TTaskPointMapper  savePointDao;
	 
	@Autowired
	private TransactionTemplate transactionTemplate;
	
	@ResponseBody
	@RequestMapping("/active")
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void myExecutor(){  
		Calendar calendar = Calendar.getInstance();
		int curHour = calendar.get(Calendar.HOUR_OF_DAY);
	    final Map<String,String> map = new HashMap<>();
        String timeStart =DateUtils.formatDate("yyyy-MM-dd ") +  "12:00:00";
        	String timeEnd = DateUtils.formatDate("yyyy-MM-dd " +  "12:59:59"); ;
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
