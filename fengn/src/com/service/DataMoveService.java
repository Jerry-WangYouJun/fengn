package com.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.model.InfoVo;
import com.model.Pagination;


public interface DataMoveService {
	
	public void deleteDataTemp(String table);

	public int dataMoveSql2Oracle(String apiCode);
	
	public void insertDataToTemp(List<List<Object>> listob, String apiCode);

	public int updateExistData(String apiCode);

	public List<InfoVo> queryDataList(String dateBegin, String dateEnd, String status , Pagination pagination  , String iccid );

	public void updateDataStatus(String id, String color);

	public int queryDataSize(String dateBegin, String dateEnd, String status  , String iccid );

	public void insertAgentCard();

	public List<List<Object>> getDataList(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception;

	
	
}