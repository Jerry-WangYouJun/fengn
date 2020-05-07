package com.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.PackageDao;
import com.dao.PackageMapper;
import com.model.Packages;
import com.model.Pagination;
import com.model.Rebate;

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
	
	public Packages selectPackagesById(Integer pacid){
		return  pacDao.selectByPrimaryKey(pacid);
	}


	public void insertPacRef(String pacids, String agentid, String parentAgentId , Double childcost) {
		dao.insertPacRef(pacids,agentid,parentAgentId , childcost);
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
	
	public Rebate getRebateByIccid(String iccid){
		return dao.queryByIccId(iccid);
	}
	
	public List<Rebate> getRebatePersonList(String iccId) {
		// TODO Auto-generated method stub
		List<Rebate> rebateList = new ArrayList<Rebate>();
		//通过iccid 查询 cmcc_card_agent 表 获取 代理商id 与  套餐id 
		Rebate rebatePerson = dao.queryByIccId(iccId);		
		rebateList.add(rebatePerson);
		
		if(rebatePerson.getParentAgentId() != 1)
		{
			
			while(true)
			{
				rebatePerson = dao.queryByAgentIdAndPacId(rebatePerson);
				rebateList.add(rebatePerson);
				if(rebatePerson.getParentAgentId()==1)
				{
					break;
				}
			}
		}	
		return rebateList;
	}

	public int queryByPacIdAndAgentId(String pacids, String agentid) {
		// TODO Auto-generated method stub
		return dao.queryByPacIdAndAgentId(pacids,agentid);
	}
	/**
	 * 查询麦联宝返利人员
	 * @param iccId
	 * @return
	 */
	public List<Rebate> getMlbRebatePersonList(String iccId) {
		// TODO Auto-generated method stub
			List<Rebate> rebateList = new ArrayList<Rebate>();
			//通过iccid 查询 cmcc_card_agent 表 获取 代理商id 与  套餐id 
			Rebate rebatePerson = dao.queryMlbByIccId(iccId);
			
			if(rebatePerson.getPacTypeName().indexOf("gd")!= -1)
			{
				/////固定套餐售价是15 
				double amount = 15 - rebatePerson.getPaccost();
				/////防止设置异常 出现负数
				if(amount>0)
				{
					rebatePerson.setAmount(amount);
				}
				else
				{
					return null;
				}
			}
			rebateList.add(rebatePerson);
			
			///////上级代理商返利与原逻辑相同
			if(rebatePerson.getParentAgentId() != 1)
			{				
				while(true)
				{
					rebatePerson = dao.queryByAgentIdAndPacId(rebatePerson);
					rebateList.add(rebatePerson);
					if(rebatePerson.getParentAgentId()==1)
					{
						break;
					}
				}
			}	
			return rebateList;
	}
}
