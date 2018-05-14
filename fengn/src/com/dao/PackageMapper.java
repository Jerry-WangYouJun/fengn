package com.dao;

import com.model.Packages;



public interface PackageMapper {

	public void insertData(Packages pac);
	
	public void delete( Integer id );
	
	public void update(Packages pac);

}