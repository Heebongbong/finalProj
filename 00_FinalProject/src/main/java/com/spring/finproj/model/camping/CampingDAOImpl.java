package com.spring.finproj.model.camping;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CampingDAOImpl implements CampingDAO{
	
	@Autowired
	private SqlSessionTemplate template;

	//CRUD
	@Override
	public List<CampingDTO> getCampingList() {
		// TODO Auto-generated method stub
		return template.selectList("camping_list");
	}

	@Override
	public CampingDTO getCampingContent(int content_id) {
		// TODO Auto-generated method stub
		return template.selectOne("camping_cont", content_id);
	}

	@Override
	public int insertCampingContent(CampingDTO dto) {
		// TODO Auto-generated method stub
		return template.insert("camping_insert", dto);
	}

	@Override
	public int updateCampingContent(int content_id) {
		// TODO Auto-generated method stub
		return template.update("camping_update", content_id);
	}

	@Override
	public int deleteCampingContent(int content_id) {
		// TODO Auto-generated method stub
		return template.delete("camping_delete", content_id);
	}
	
	//추가 기능

	@Override
	public int insertCampingList(List<CampingDTO> list) {
		// TODO Auto-generated method stub
		return template.insert("camping_insert_list", list);
	}
	

	@Override
	public List<CampingDTO> getCampingRandomList() {
		// TODO Auto-generated method stub
		return template.selectList("camping_list_random");
	}
}