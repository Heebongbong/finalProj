package com.spring.finproj.service.camping;

import java.io.IOException;

import org.springframework.ui.Model;

public interface CampingService {

	public void getCampingList(Model model, String loc) throws IOException;
	public void getCampingContent(Model model, int content_id) throws IOException;
	public void insertCampingListSetDB() throws IOException;
	public void getCampingRandomList(Model model);
}