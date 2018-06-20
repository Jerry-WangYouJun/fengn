package com.dao;

import java.util.List;

import com.model.MlbUnicomCard;

public interface MlbUnicomCardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MlbUnicomCard record);

    int insertSelective(MlbUnicomCard record);

    MlbUnicomCard selectByPrimaryKey(Integer id);
    
    MlbUnicomCard selectByIccid(String guid);

    int updateByPrimaryKeySelective(MlbUnicomCard record);

    int updateByPrimaryKey(MlbUnicomCard record);
    
    public int insertBatch(List<MlbUnicomCard>  list);

	List<String> selectNoSIM();

	int updateBatch(List<MlbUnicomCard> list);
}