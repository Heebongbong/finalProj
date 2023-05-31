package com.spring.finproj.controller.admin;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.finproj.service.camping.CampingService;


@Controller
@RequestMapping("/admin")
public class AdminController {
   
	@Autowired
	private CampingService campingService;
	
	@RequestMapping(value ="/admin")
	public String adminNavi() {
		return "admin.admin";
	}
	
	@RequestMapping("/camping")
	public String campingContent(Model model, @RequestParam(required = false) String keyword, @RequestParam(required = false) String keyword2) throws Exception {
		campingService.getCampingList(model, keyword, keyword2);
		return "admin.camping";
	}
	
    
    
}