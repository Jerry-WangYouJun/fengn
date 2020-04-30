package com.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dao.HistoryMapper;
import com.model.MlBBackInfo;
import com.model.UnicomHistory;

@Controller
@RequestMapping("/mlb")
public class MlbBack {
	
	 @Autowired
	 HistoryMapper  historyDao;
	 
	 @ResponseBody
	 @RequestMapping(value = "order", method = {RequestMethod.POST,RequestMethod.GET })
	 public String  getOrder(HttpServletRequest request, HttpServletResponse response , String data){
		 String param= null; 
	        try {
	        	UnicomHistory  history = new UnicomHistory();
	            JSONObject jsonObject = JSONObject.fromObject(data);
	            String iccid = jsonObject.getString("iccid");
	            history.setIccid(iccid);
	            history.setImsi(jsonObject.getString("imsi"));
	            history.setMoney(jsonObject.getDouble("total_fee"));
	            history.setUpdateDate(jsonObject.getString("pay_time"));
	            historyDao.insert(history);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return param;
	 }
	 
	 

}
