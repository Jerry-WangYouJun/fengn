package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.PackageDao;
import com.dao.PackageMapper;
import com.model.Packages;
import com.model.Pagination;

@Service
public class PackagesService {

	@Autowired
	PackageDao dao ;
	
	@Autowired
	PackageMapper pacDao;
	
	public List<Packages> queryList(Packages pac, Pagination page) {
		
		return dao.queryList(pac, page);
	}

	public void update(Packages pac) {
		pacDao.update(pac);
	}

	public void insert(Packages pac) {
		pacDao.insertData(pac);
	}

	public void delete(Integer id) {
		pacDao.delete(id);
	}

	public int queryCardTotal(Packages pac) {
		return dao.queryTotal(pac);
	}


	public void insertPacRef(String pacids, String agentid, String parentAgentId) {
		dao.insertPacRef(pacids,agentid,parentAgentId);
	}

	public void updateChildsPacRef(Packages pac) {
		dao.updateChildsPacRef(pac);
	}

	public void insertChildsPacRef(Packages pac) {
		dao.insertChildsPacRef(pac);
	}

	public List<Packages> getPacListByAgentId(String agentId) {
		return dao.getPacListByAgentId(agentId);
	}
}
