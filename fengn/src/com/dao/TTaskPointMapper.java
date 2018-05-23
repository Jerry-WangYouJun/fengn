package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.model.TTaskPoint;
import com.model.TTaskPointExample;

public interface TTaskPointMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_task_point
	 * @mbg.generated  Sun May 13 12:26:08 CST 2018
	 */
	long countByExample(TTaskPointExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_task_point
	 * @mbg.generated  Sun May 13 12:26:08 CST 2018
	 */
	int deleteByExample(TTaskPointExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_task_point
	 * @mbg.generated  Sun May 13 12:26:08 CST 2018
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_task_point
	 * @mbg.generated  Sun May 13 12:26:08 CST 2018
	 */
	int insert(TTaskPoint record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_task_point
	 * @mbg.generated  Sun May 13 12:26:08 CST 2018
	 */
	int insertSelective(TTaskPoint record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_task_point
	 * @mbg.generated  Sun May 13 12:26:08 CST 2018
	 */
	List<TTaskPoint> selectByExample(TTaskPointExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_task_point
	 * @mbg.generated  Sun May 13 12:26:08 CST 2018
	 */
	TTaskPoint selectByPrimaryKey(Integer id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_task_point
	 * @mbg.generated  Sun May 13 12:26:08 CST 2018
	 */
	int updateByExampleSelective(@Param("record") TTaskPoint record, @Param("example") TTaskPointExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_task_point
	 * @mbg.generated  Sun May 13 12:26:08 CST 2018
	 */
	int updateByExample(@Param("record") TTaskPoint record, @Param("example") TTaskPointExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_task_point
	 * @mbg.generated  Sun May 13 12:26:08 CST 2018
	 */
	int updateByPrimaryKeySelective(TTaskPoint record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table t_task_point
	 * @mbg.generated  Sun May 13 12:26:08 CST 2018
	 */
	int updateByPrimaryKey(TTaskPoint record);
}