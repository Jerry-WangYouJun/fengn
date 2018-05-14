package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.AgentDao;
import com.dao.UserDao;
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

	public List<User> queryList(User user, Pagination page) {
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
	 	 
	
}	 
