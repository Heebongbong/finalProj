package com.spring.finproj.model.weather;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WeatherDAOImpl implements WeatherDAO{
	private SqlSessionTemplate sqlSession;
}
