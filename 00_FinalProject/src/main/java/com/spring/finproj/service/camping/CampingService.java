package com.spring.finproj.service.camping;

import java.io.IOException;

import org.springframework.ui.Model;

public interface CampingService {

	void getCampingDetail(Model model, int num) throws IOException;

}