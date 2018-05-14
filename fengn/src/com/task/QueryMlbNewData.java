package com.task;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.DateUtils;
import com.common.ResponseURLDataUtil;
import com.dao.TMlbRecordMapper;
import com.dao.TMlbTempMapper;
import com.dao.TTaskPointMapper;
import com.model.TMlbRecord;
import com.model.TMlbTemp;
import com.model.TMlbTempExample;
import com.model.TTaskPoint;
import com.service.UnicomUploadService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JavaIdentifierTransformer;

@Service
public class QueryMlbNewData {
	@Autowired
	TMlbRecordMapper  mlbDao;
	
	@Autowired
	TMlbTempMapper  mlbTempDao;
	
	@Autowired
	TTaskPointMapper  savePointDao;
	@Autowired
	private UnicomUploadService dataServices;
	
	public static String[] getFiled(TMlbRecord a) {
		Field fields[] = a.getClass().getDeclaredFields();
		String[] name = new String[fields.length];
		try {
			Field.setAccessible(fields, true);
			for (int i = 0; i < name.length; i++) {
				name[i] = fields[i].getName();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}

	 public String getData(String timeStart ,String timeEnd) {
		 mlbTempDao.deleteByExample(new TMlbTempExample());
		 String result = "" ;
		 try{
			 result =   insertData(timeStart , timeEnd);
			 dataServices.insertMlbRenewData("cmtp", "history");
			 dataServices.insertMlbRenewData("u_cmtp", "u_history");
 		  }catch (Exception e) {
 			 result =  "exception:"
			  + 	(e.getMessage().length()>200?e.getMessage().substring(0, 200):e.getMessage() );
		}
		String error = "success";
			if(result.startsWith("exception")) {
				error = "failed";
			}
		savePointDao.insert(new TTaskPoint(timeStart,timeEnd,result,error));
		 return error  ;
	 }
	 
	 public void saveTaskPoint() {
		  
	 }
		 
	  public  String insertData(String timeStart ,String timeEnd) {
		  JSONObject obj =  getMlbData(400,timeStart,timeEnd);
		  if("null".equals(obj.getString("result"))) {
			   return  obj.getString("reason");
		  }
		  JSONArray ja = ((JSONArray)obj.get("result")).getJSONArray(1); 
		  for(int i=0;i<ja.size();i++){  
			  // 遍历 jsonarray 数组，把每一个对象转成 json 对象  
			 JSONObject job = ja.getJSONObject(i);  
			Iterator<String> sIterator = job.keys();  
			 while(sIterator.hasNext()){  
			     // 获得key  
			     String key = sIterator.next();  
			     // 根据key获得value, value也可以是JSONObject,JSONArray,使用对应的参数接收即可  
			     String value = job.getString(key);  
			     if("null".equals(value)) {
			    	 	   job.put(key, "") ;
			     }
			 } 
			 JsonConfig config = new JsonConfig();
		        config.setJavaIdentifierTransformer(new JavaIdentifierTransformer() {

		            @Override
		            public String transformToJavaIdentifier(String str) {
		                char[] chars = str.toCharArray();
		                chars[0] = Character.toLowerCase(chars[0]);
		                return new String(chars);
		            }

		        });
		        config.setRootClass(TMlbRecord.class);
			 // 得到 每个对象中的属性值  
			 TMlbRecord record = (TMlbRecord)JSONObject.toBean(job, new TMlbRecord() ,config); 
			 record.setICCID(job.getString("ICCID"));
			 record.setIMEI(job.getString("IMEI"));
			 record.setSIM(job.getString("SIM"));
			 record.setIMSI(job.getString("IMSI"));
			 record.setRecordTime(DateUtils.formatDate("yyyy-dd-MM hh:mm:ss"));
			 
			 TMlbTemp temp = (TMlbTemp)JSONObject.toBean(job, new TMlbTemp() ,config); 
			 temp.setICCID(job.getString("ICCID"));
			 temp.setIMEI(job.getString("IMEI"));
			 temp.setSIM(job.getString("SIM"));
			 temp.setIMSI(job.getString("IMSI"));
			 temp.setRecordTime(DateUtils.formatDate("yyyy-dd-MM hh:mm:ss"));
			 mlbDao.insert(record);
			 mlbTempDao.insert(temp);
		  } 
		  return "success:" + ja.size();
	}
	  public JSONObject getMlbData(int initPsize , String timeStart  ,String timeEnd) {
		  System.out.println(new Date());
		  JSONObject jsonObject = null ;
			try {
				String url =ResponseURLDataUtil.getUrlMlb(timeStart,timeEnd,initPsize,1);
				jsonObject  = ResponseURLDataUtil.getLmbJsonData(url);
				//System.out.println("data:" + jsonObject);
				if(jsonObject != null && "0".equals(jsonObject.get("error").toString()) 
						&& StringUtils.isNotEmpty(jsonObject.get("result").toString())
						) {
					JSONArray ja = (JSONArray)jsonObject.get("result"); 
					int records = Integer.valueOf(ja.getJSONObject(0).get("records").toString());
					System.out.println(records);
					if(records > initPsize) {
						QueryMlbNewData service = new QueryMlbNewData();
						  service.getMlbData(initPsize * 10 , timeStart,timeEnd );
					}
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			System.out.println(new Date());
			return jsonObject ;
	  }
	  
	  public static void main(String[] args) {
		  String timeStart ="2018-05-13" + " 00:00:00";
  		  String timeEnd = "2018-05-13" + " 23:59:59";
  		  QueryMlbNewData qd = new QueryMlbNewData(); 
        		qd.getData(timeStart , timeEnd);
	}
}
