package com.spring.finproj.controller.camping;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.finproj.model.camping.CampingDTO;
import com.spring.finproj.service.camping.CampingService;

@Controller
@RequestMapping("/camping")
public class CampingController {
	@Autowired
	private CampingService campingService;
	
	@RequestMapping("/camping")
	public String campingContent(Model model, @RequestParam(required = false) String keyword, @RequestParam(required = false) String keyword2) throws Exception {
		campingService.getCampingList(model, keyword, keyword2);
		return "camping.camping";
	}

	@RequestMapping("/details")
	public String campingContent(Model model, int content_id) throws Exception {
		System.out.println(content_id);
		
		campingService.getCampingContent(model, content_id);
		System.out.println(11);
		return "camping.details";
	}
	
	@RequestMapping("/list/insert")
	public String campingInsertList() throws IOException {
		campingService.insertCampingListSetDB();
		return "redirect:/index";
	}
	
	@RequestMapping("/addlist")
	@ResponseBody
	public Map<String, CampingDTO> campingAddList(int content_id, String keyword, String keyword2){
		System.out.println(keyword2);
		return campingService.getCampingAddList(content_id, keyword, keyword2);
	}
}