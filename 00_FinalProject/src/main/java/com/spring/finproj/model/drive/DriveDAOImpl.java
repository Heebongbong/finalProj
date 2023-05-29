package com.spring.finproj.model.drive;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DriveDAOImpl implements DriveDAO{
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public int insertRoadInfo(DriveRoadDTO d) {
		// TODO Auto-generated method stub
		return sqlSession.insert("road_insert", d);
	}

	@Override
	public void insertRoadXY(Map<String, Object> map) {
		// TODO Auto-generated method stub
		sqlSession.insert("road_insert_xy", map);
	}

	@Override
	public List<DriveRoadDTO> getRoadXY() {
		// TODO Auto-generated method stub
		return sqlSession.selectList("road_get_list");
	}
}
