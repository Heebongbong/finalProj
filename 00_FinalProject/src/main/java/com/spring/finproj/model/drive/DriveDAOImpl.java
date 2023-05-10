package com.spring.finproj.model.drive;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DriveDAOImpl implements DriveDAO{
	private SqlSessionTemplate sqlSession;
}
