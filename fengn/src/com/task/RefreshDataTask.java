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
	public void myExecutor(){  
			this.transactionTemplate.execute(new TransactionCallback() {
	            public Object doInTransaction(TransactionStatus transactionStatus) {
	      		  try{
	      			  dataServices.insertUnicomTemp("update" , "unicom" , "3");
	      		  }catch (Exception e) {
					  e.getMessage();
				}
	      		  return null;
	         }
	        });
  }  
}
