package com.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

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
}
