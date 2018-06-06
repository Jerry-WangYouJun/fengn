package com.dao;

import com.model.MlbCmccCard;

public interface MlbCmccCardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MlbCmccCard record);

    int insertSelective(MlbCmccCard record);

    MlbCmccCard selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MlbCmccCard record);

    int updateByPrimaryKey(MlbCmccCard record);
    
    
}