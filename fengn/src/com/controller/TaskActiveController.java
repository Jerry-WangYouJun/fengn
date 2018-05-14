package com.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dao.TTaskPointMapper;
import com.task.QueryMlbNewData;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/task")
public class TaskActiveController {
	 
	@Autowired
	QueryMlbNewData service ;
	
	@Autowired
	TTaskPointMapper  savePointDao;
	 
	@Autowired
	private TransactionTemplate transactionTemplate;
	
	@ResponseBody
	@RequestMapping("/active")
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public JSONObject myExecutor(){  
		String timeStart ="2018-05-12" + " 00:00:00";
		  String timeEnd = "2018-05-13" + " 23:59:59";
	       service.getData(timeStart, timeEnd);
			Map map = new HashMap();
			map.put("success", true);
			JSONObject ojb = JSONObject.fromObject(map);
			return ojb;
  }  
}
