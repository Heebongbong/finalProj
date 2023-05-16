package com.spring.finproj.controller.camping;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.finproj.service.camping.CampingService;

@Controller
@RequestMapping("/camping")
public class CampingController {
	@Autowired
	private CampingService campingService;
	
	@RequestMapping("/content")
	public String campingContent(Model model) throws Exception {
		return "camping.content";
	}
	
	@RequestMapping("/list/insert")
	public String campingInsertList() throws IOException {
		campingService.insertCampingListSetDB();
		return "redirect:/index";
	}
}