package com.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.model.Rebate;

@Repository
public class CardAgentDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;


	
	
}
