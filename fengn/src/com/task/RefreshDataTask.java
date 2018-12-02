package com.task;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.common.DateUtils;
import com.dao.TTaskPointMapper;
import com.service.UnicomUploadService;

public class RefreshDataTask {
	@Autowired
	QueryMlbNewData service ;
	
	@Autowired
	private TransactionTemplate transactionTemplate;
	
	@Autowired
	private UnicomUploadService dataServices;
	
	
	@Autowired
	TTaskPointMapper task;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void cmccUpdateDisable(){  
		System.out.println("cmccUpdateDisable");
			this.transactionTemplate.execute(new TransactionCallback() {
	            public Object doInTransaction(TransactionStatus transactionStatus) {
	      		  try{
	      			  dataServices.deleteDataTemp("mlb_cmcc_card_copy");
	      			  String s  =dataServices.insertCmccTemp("update" , "cmcc" , "3");
	      			  System.out.println(s);
	      		  }catch (Exception e) {
					  e.getMessage();
				}
	      		  return null;
	         }
	        });
  } 
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void cmccUpdateNormal(){
		System.out.println("cmccUpdateNormal");
		this.transactionTemplate.execute(new TransactionCallback() {
            public Object doInTransaction(TransactionStatus transactionStatus) {
      		  try{
      			 dataServices.deleteDataTemp("mlb_cmcc_card_copy");
      			 String s =  dataServices.insertCmccTemp("update" , "cmcc" , "3");
      			 System.out.println(s);
      		  }catch (Exception e) {
				  e.getMessage();
			}
      		  return null;
         }
        });
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void unicomUpdateNormal(){
		System.out.println("unicomUpdateNormal");
		this.transactionTemplate.execute(new TransactionCallback() {
            public Object doInTransaction(TransactionStatus transactionStatus) {
      		  try{
      			dataServices.deleteDataTemp("mlb_unicom_card_copy");
      			 String s =  dataServices.insertUnicomTemp("update" , "unicom" , "3");
      			 System.out.println(s);
      		  }catch (Exception e) {
				  e.getMessage();
			}
      		  return null;
         }
        });
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void unicomUpdateDisable(){
		System.out.println("unicomUpdateDisable");
		this.transactionTemplate.execute(new TransactionCallback() {
            public Object doInTransaction(TransactionStatus transactionStatus) {
      		  try{
      			 dataServices.deleteDataTemp("mlb_unicom_card_copy");
      			 String s = dataServices.insertUnicomTemp("update" , "unicom" , "4");
      			 System.out.println(s);
      		  }catch (Exception e) {
				  e.getMessage();
			}
      		  return null;
         }
        });
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void unicomUpdateRenew_C(){
		this.transactionTemplate.execute(new TransactionCallback() {
            public Object doInTransaction(TransactionStatus transactionStatus) {
      		  try{
      			  System.out.println("renew");
      			 dataServices.deleteDataTemp("u_history_temp");
      			 String today = DateUtils.formatDate("yyyy-MM-dd");
      			 Calendar c= Calendar.getInstance();
      			 c.setTime(new Date());
      			 c.add(Calendar.DAY_OF_MONTH, -1);
      			 String startDay = DateUtils.formatDate("yyyy-MM-dd", c.getTime());
      			 String s = dataServices.insertHistoryTemp(startDay,today);
      			 System.out.println(s);
      		  }catch (Exception e) {
				  e.getMessage();
				  System.out.println("有错误："+e.getMessage());
			  }
      		  
      		  return null;
         }
        });
	}
}
