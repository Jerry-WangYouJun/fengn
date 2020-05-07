package com.dao;

import java.util.List;

import com.model.Packages;



public interface PackageMapper {

	public void insertData(Packages pac);
	
	public void delete( Integer id );
	
	public void update(Packages pac);
	
	public Packages selectByPrimaryKey(Integer id);
	
	public List<Packages> selectAll();

}