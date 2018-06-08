package com.service;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.DateUtils;
import com.common.PropertyCommons;
import com.common.ResponseURLDataUtil;
import com.common.StringCommons;
import com.common.WlkAPIUtil;
import com.dao.DataMoveDao;
import com.model.History;
import com.model.InfoVo;

import net.sf.json.JSONObject;



@Service
public class CardInfoService {
	
	@Autowired
	DataMoveDao  dao ;
	
	
	public InfoVo queryInfoByICCID(String iccid) throws Exception {
		String sql = "select * , '' name from cmtp where iccid = '" + iccid + "'  or imsi = '" + iccid + "'";
		 List<InfoVo>  infoList =  dao.queryDataList(sql);
		 if(infoList.size() > 0 ){
			 InfoVo  info = infoList.get(0);
//			 	dao.queryDetail(info);
//			 	dao.queryPackage(info);
//			 	dao.queryHistory(info);
			 try {
					getDetail(info);
			} catch (Exception e) {
				System.out.println("Iccid:" + iccid + ",信息错误:" + e.getMessage());
				throw new Exception("Iccid:" + iccid + ",信息错误:" + e.getMessage());
			}
			   return info;
		 }
		return  null ;
	}
	
	public void getDetail(InfoVo  info) throws Exception{
			JSONObject  jsonCardInfo = getResultData(info,StringCommons.API_YONGSI_GPRSSTAUTS);
		    String gprsStatus = jsonCardInfo.get("GPRSSTATUS").toString();
		    if(info.getCardStatus()!=null && !info.getCardStatus().equals(gprsStatus)){
		    	info.setCardStatus(gprsStatus);
		    }
		    /*JSONObject  jsonUserStatus = getResultData(info,"0001000000009");
		    String userStatus = jsonUserStatus.getString("STATUS").toString();
		    if(info.getUserStatus()!=null && !info.getUserStatus().equals(userStatus)){
		    	info.setUserStatus(userStatus);
		    }*/
		    JSONObject  jsonGprsUsed = getResultData(info,StringCommons.API_YONGSI_TOTALGPRS);
		    //jsonGprsUsed.put("total_gprs", "1000");
		     double  gprsUsed = Double.valueOf(jsonGprsUsed.getString("total_gprs").toString())/1024;
//	     	 JSONObject  jsonBalance = getResultData(info,StringCommons.API_YONGSI_BALANCE);
	     	 DecimalFormat df = new DecimalFormat("#.###");  
		     gprsUsed = Double.valueOf(df.format(gprsUsed));
		    if(info.getGprsUsed()!=null && Double.valueOf(info.getGprsUsed()) != gprsUsed){
		    	info.setGprsUsed(gprsUsed + "");
		    }
		    updateGprsUsed(gprsUsed , info.getICCID());
		    //gprsUsed = 0.1 ;
		    	if(gprsUsed == 0  && info.getRestDay() >0 &&"待激活".equals(info.getUserStatus())){
		    		info.setUserStatus("待激活");
		    		info.setRestDay(365L);
		    		info.setDateEnd("****-**-**");
		    		//int curMonth = Integer.valueOf(info.getOpenDate().split("-")[1]) ;
		    	}else if(gprsUsed>0 && Double.valueOf(info.getMonthTotalStream())>  gprsUsed && info.getRestDay()>0){
		    		if(!"正常".equals(info.getUserStatus())){
		    			updateUserStatus("正常",info.getICCID());
		    			info.setUserStatus("正常");
		    		}
		    	}else if( gprsUsed > Double.valueOf(info.getMonthTotalStream())){
		    		if(!"停机".equals(info.getUserStatus())){
		    			updateUserStatus("停机",info.getICCID());
		    			info.setUserStatus("停机");
		    		}
		    	}
	}

	private void updateGprsUsed(double gprsUsed, String iccid) {
		
		String  sql = "update cmtp set gprsused = " +gprsUsed + " where  iccid = '" + iccid + "'" ; 
		dao.update(sql);
	}

	private void updateUserStatus(String userStatus, String iccid) {
		String  sql = "update cmtp set userstatus = '" +userStatus + "' where  iccid = '" + iccid + "'" ; 
		//dao.updateTables(sql);
		dao.update(sql);
		
	}
	
	public static void main(String[] args) throws Exception {
		CardInfoService service = new CardInfoService();
		service.getResultData(new InfoVo(),"898602B6111770667999");
	}

	public  JSONObject  getResultData(InfoVo info ,  String ebid) throws Exception{
		 String url ; 
		 if(StringCommons.API_YONGSI.equals(info.getApiCode())){
			   url = WlkAPIUtil.getUrl(info,ebid) ;
		 }else{
			  url =  PropertyCommons.getUrl(ebid , info.getCardCode().trim()) ;
		 }
		 String jsonString =	ResponseURLDataUtil.getReturnData(url) ;
		 JSONObject jsonObject ;
		 try{
			  jsonObject = JSONObject.fromObject(jsonString);  
		 }catch(Exception e){
			 throw new Exception(jsonString);
		 }
		 if(!"正确".equals(jsonObject.get("message"))){
			 throw new Exception(jsonObject.get("message").toString());
		 }
		 return jsonObject.getJSONArray("result").getJSONObject(0);
	}

	public void insertHistory(History history) {
		String  insertHistorySql = "insert history (iccid , package_id , money , update_date ,orderNo) values "
				+ "('" + history.getIccid() + "','" + history.getPackageId() + "','" + history.getMoney() + "','" + history.getUpdateDate() + "','" + history.getOrderNo() + "' )";
		dao.update(insertHistorySql);
	}

	public void queryHistoryList(InfoVo   info){
		dao.queryHistory(info);
	}

	public void updateCardStatus(String iccid) {
		String  sql = "update cmtp set userstatus = '正常' ,  updateTime = '" + DateUtils.formatDate("yyyy-MM-dd") + "' where  iccid = '" + iccid + "'" ; ;
		dao.update(sql);
		
	}
}
