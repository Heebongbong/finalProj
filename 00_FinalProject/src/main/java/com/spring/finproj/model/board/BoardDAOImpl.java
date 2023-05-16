package com.spring.finproj.model.board;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAOImpl implements BoardDAO{
	private SqlSessionTemplate sqlSession;
	
	
}