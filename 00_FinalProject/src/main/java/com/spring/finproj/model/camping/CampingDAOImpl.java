package com.spring.finproj.model.camping;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CampingDAOImpl implements CampingDAO{
	private SqlSessionTemplate sqlSession;
	
}
