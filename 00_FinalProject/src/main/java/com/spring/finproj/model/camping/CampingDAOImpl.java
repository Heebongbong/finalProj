package com.spring.finproj.model.camping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CampingDAOImpl implements CampingDAO{
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	//CRUD
	@Override
	public List<CampingDTO> getCampingList() {
		// TODO Auto-generated method stub
		return sqlSession.selectList("camping_list");
	}

	@Override
	public CampingDTO getCampingContent(int content_id) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("camping_cont", content_id);
	}

	@Override
	public int insertCampingContent(CampingDTO dto) {
		// TODO Auto-generated method stub
		return sqlSession.insert("camping_insert", dto);
	}

	@Override
	public int updateCampingContent(CampingDTO dto) {
		// TODO Auto-generated method stub
		return sqlSession.update("camping_update", dto);
	}

	@Override
	public int deleteCampingContent(int content_id) {
		// TODO Auto-generated method stub
		return sqlSession.delete("camping_delete", content_id);
	}
	
	//부가기능
	@Override
	public int insertCampingList(List<CampingDTO> list) {
		// TODO Auto-generated method stub
		return sqlSession.insert("camping_insert_list", list);
	}
	

	@Override
	public List<CampingDTO> getCampingRandomList() {
		// TODO Auto-generated method stub
		return sqlSession.selectList("camping_list_random");
	}

	@Override
	public List<CampingDTO> getCampingLocList(Map<String, String> map) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("camping_list_loc", map);
	}
}