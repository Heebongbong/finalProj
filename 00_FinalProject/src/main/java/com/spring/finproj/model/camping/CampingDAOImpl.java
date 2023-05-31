package com.spring.finproj.model.camping;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.finproj.model.board.BoardDTO;

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

	@Override
	public List<CampingDTO> getCampingAddList(Map<String, String> map) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("camping_list_add", map);
	}
	
	//캠핑 리뷰 리스트 Map타입 파라메터 오버로드
	@Override
	public List<BoardDTO> getCampingReviewList(int content_id) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("camping_review_list", content_id);
	}

	@Override
	public List<BoardDTO> getCampingReviewList(Map<String, Integer> keyList) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("camping_review_list_map", keyList);
	}

	@Override
	public int deleteCampingList() {
	    return sqlSession.delete("camping_delete_list");
	}
}