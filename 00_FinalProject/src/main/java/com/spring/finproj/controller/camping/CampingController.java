package com.spring.finproj.controller.camping;

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
	public String campingContent(Model model, @RequestParam int num) throws Exception {
		
		campingService.getCampingDetail(model, num);
		
		return "camping.content";
	}
}