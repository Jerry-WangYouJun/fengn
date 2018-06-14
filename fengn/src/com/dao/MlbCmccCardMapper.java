package com.dao;

import java.util.List;

import com.model.MlbCmccCard;

public interface MlbCmccCardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MlbCmccCard record);

    int insertSelective(MlbCmccCard record);

    MlbCmccCard selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MlbCmccCard record);

    int updateByPrimaryKey(MlbCmccCard record);

	int insertBatch(List<MlbCmccCard> mccInsertList);

	List<String> selectNoSIM(String createdate);
	
	int updateBatch(List<MlbCmccCard> mccInsertList);
    
    
}