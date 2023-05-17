package com.spring.finproj.controller.camping;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.finproj.service.camping.CampingService;

@Controller
@RequestMapping("/camping")
public class CampingController {
	@Autowired
	private CampingService campingService;
	
	@RequestMapping("/content")
	public String campingContent(Model model, @RequestParam(defaultValue = "서울")String loc) throws Exception {
		campingService.getCampingList(model, loc);
		return "camping.search";
	}

	@RequestMapping("/details")
	public String campingContent(Model model, @RequestParam("contentId") int content_id) throws Exception {
		campingService.getCampingContent(model, content_id);
		return "camping.details";
	}
	
	@RequestMapping("/list/insert")
	public String campingInsertList() throws IOException {
		campingService.insertCampingListSetDB();
		return "redirect:/index";
	}
}