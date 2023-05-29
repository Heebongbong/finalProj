package com.spring.finproj.service.camping;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.spring.finproj.model.camping.CampingDTO;

public interface CampingService {

	public void getCampingList(Model model, String keyword, String keyword2) throws IOException;
	public void getCampingContent(Model model, int content_id) throws IOException;
	public void insertCampingListSetDB() throws IOException;
	public void getCampingRandomList(Model model);
	public List<CampingDTO> getCampingAddList(int content_id, String keyword, String keyword2);
	public Map<String, Object> getCampingReviewList(int content_id, HttpServletRequest request, int cm_no) throws Exception;
}