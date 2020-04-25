package com.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.AgentDao;
import com.dao.UserDao;
import com.model.Agent;
import com.model.Pagination;
import com.model.User;

@Service
public class UserService {
	
	@Autowired
	UserDao  dao ;
	@Autowired
	AgentDao  agentDao ;

	public User checkUser(User user) {
		
		return dao.checkUser(user);
	}

	public List<Agent> queryList(User user, Pagination page) {
		return dao.queryList(user ,page);
	}
	
	public int queryListCount(User user  ) {
		return dao.queryTotal(user);
	}

	public void insert(User user) {
		 dao.insert(user);
	}

	public void update(User user) {
		dao.update(user);
		
	}

	public void delete(Integer id) {
		dao.delete(id);
	}
	
	public List<User> getRebatePerson(String iccId)
	{
		Agent temp = agentDao.queryCodeByIccId(iccId);
		User user = dao.queryUserByAgentId(temp.getId());
		user.setAgent(temp);		
		List<User> userList = new ArrayList<User>();
		userList.add(user);
		
		if(temp.getParentId()!=1)
		{
			while(true)
			{
				temp = agentDao.queryParentIdById(temp.getParentId());
				User tempUser = new User();
				user = dao.queryUserByAgentId(temp.getId());
				user.setAgent(temp);
				userList.add(user);
				if(temp.getParentId() == 1)
				{
					break;
				}
			}
		}
//		userList = dao.getRebatePerson(idList);	
		return userList;
	}
}	 
