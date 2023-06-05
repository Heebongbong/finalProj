package com.spring.finproj.model.camping;

import java.util.List;
import java.util.Map;

import com.spring.finproj.model.board.BoardDTO;

public interface CampingDAO {
	public int insertCampingList(List<CampingDTO> list);
	public int deleteCampingList();
	//CRUD
	public List<CampingDTO> getCampingList();
	public CampingDTO getCampingContent(int content_id);
	public int insertCampingContent(CampingDTO dto);
	public int updateCampingContent(CampingDTO dto);
	public int deleteCampingContent(int content_id);
	
	//부가기능
	public List<CampingDTO> getCampingRandomList();
	public List<CampingDTO> getCampingLocList(Map<String, String> map);
	public List<BoardDTO> getCampingReviewList(int content_id);
	public List<BoardDTO> getCampingReviewList(Map<String, Integer> keyList);
	public List<CampingDTO> getCampingAddList();
	public List<CampingDTO> getCampingAddList(int content_id);
	public List<CampingDTO> getCampingAddList(Map<String, Object> keyList);
	
}