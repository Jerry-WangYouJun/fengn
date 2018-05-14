package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.model.TMlbTemp;
import com.model.TMlbTempExample;

public interface TMlbTempMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_mlb_temp
	 * @mbg.generated  Sat May 12 18:10:29 CST 2018
	 */
	long countByExample(TMlbTempExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_mlb_temp
	 * @mbg.generated  Sat May 12 18:10:29 CST 2018
	 */
	int deleteByExample(TMlbTempExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_mlb_temp
	 * @mbg.generated  Sat May 12 18:10:29 CST 2018
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_mlb_temp
	 * @mbg.generated  Sat May 12 18:10:29 CST 2018
	 */
	int insert(TMlbTemp record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_mlb_temp
	 * @mbg.generated  Sat May 12 18:10:29 CST 2018
	 */
	int insertSelective(TMlbTemp record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_mlb_temp
	 * @mbg.generated  Sat May 12 18:10:29 CST 2018
	 */
	List<TMlbTemp> selectByExample(TMlbTempExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_mlb_temp
	 * @mbg.generated  Sat May 12 18:10:29 CST 2018
	 */
	TMlbTemp selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_mlb_temp
	 * @mbg.generated  Sat May 12 18:10:29 CST 2018
	 */
	int updateByExampleSelective(@Param("record") TMlbTemp record, @Param("example") TMlbTempExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_mlb_temp
	 * @mbg.generated  Sat May 12 18:10:29 CST 2018
	 */
	int updateByExample(@Param("record") TMlbTemp record, @Param("example") TMlbTempExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_mlb_temp
	 * @mbg.generated  Sat May 12 18:10:29 CST 2018
	 */
	int updateByPrimaryKeySelective(TMlbTemp record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_mlb_temp
	 * @mbg.generated  Sat May 12 18:10:29 CST 2018
	 */
	int updateByPrimaryKey(TMlbTemp record);
}