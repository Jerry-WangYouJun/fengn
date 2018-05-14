package com.task;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.common.DateUtils;

public class RefreshDataTask {
	@Autowired
	QueryMlbNewData service ;
	
	@Autowired
	private TransactionTemplate transactionTemplate;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void myExecutor(){      
	        final 	String timeStart =DateUtils.formatDate("yyyy-MM-dd ") + (Calendar.HOUR_OF_DAY -1) + ":00:00";
	        final	String timeEnd = DateUtils.formatDate("yyyy-MM-dd " + (Calendar.HOUR_OF_DAY -1) + ":59:59"); ;
			String result = (String) this.transactionTemplate.execute(new TransactionCallback() {
	            public Object doInTransaction(TransactionStatus transactionStatus) {
	      		  try{
	      			  return  service.getData(timeStart , timeEnd);
	      		  }catch (Exception e) {
					 return "exception:"
					  + 	(e.getMessage().length()>200?e.getMessage().substring(0, 200):e.getMessage() );
				}
	         }
	        });
  }  
}
