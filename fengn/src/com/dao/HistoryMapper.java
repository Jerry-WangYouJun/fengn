package com.dao;

import java.util.List;

import com.model.UnicomHistory;



public interface HistoryMapper {

    public int insertBatch(List<UnicomHistory>  list);

	public void insertData();
	
	public void deleteAll();

}