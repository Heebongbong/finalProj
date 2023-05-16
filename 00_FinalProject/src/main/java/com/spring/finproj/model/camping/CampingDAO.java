package com.spring.finproj.model.camping;

import java.util.List;

public interface CampingDAO {
	public int insertCampingList(List<CampingDTO> list);
	//CRUD
	public List<CampingDTO> getCampingList();
	public CampingDTO getCampingContent(int content_id);
	public int insertCampingContent(CampingDTO dto);
	public int updateCampingContent(int content_id);
	public int deleteCampingContent(int content_id);
	public List<CampingDTO> getCampingRandomList();
}