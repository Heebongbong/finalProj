package com.spring.finproj.model.admin;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class AdminDAOImpl implements AdminDAO{
	
	@Autowired
	private SqlSessionTemplate sqlSession;


	
}