package com.dao;

import java.util.List;

import com.model.UnicomHistory;



public interface HistoryMapper {

    public int insertBatch(List<UnicomHistory>  list);

	public void insertData();
	
	public void insertGdData(UnicomHistory history);
	
	public void deleteAll();

	public void insert(UnicomHistory history);

}