package com.spring.finproj.service.camping;

import java.io.IOException;

import org.springframework.ui.Model;

public interface CampingService {

	public void getCampingList(Model model) throws IOException;
	public void insertCampingListSetDB() throws IOException;
	public void getCampingRandomList(Model model);
}