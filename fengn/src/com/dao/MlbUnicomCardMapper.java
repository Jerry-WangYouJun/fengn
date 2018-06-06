package com.dao;

import com.model.MlbUnicomCard;

public interface MlbUnicomCardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MlbUnicomCard record);

    int insertSelective(MlbUnicomCard record);

    MlbUnicomCard selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MlbUnicomCard record);

    int updateByPrimaryKey(MlbUnicomCard record);
}