package com.spring.finproj.controller.camping;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
	public String campingContent(Model model, 
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String category) throws Exception {
		//campingService.getCampingList(model, keyword, category);
		
		model.addAttribute("Category", category);
		model.addAttribute("C_Keyword", keyword);
		return "camping.camping";
	}

	@RequestMapping("/details")
	public String campingContent(Model model, int content_id) throws Exception {
		
		campingService.getCampingContent(model, content_id);
		return "camping.details";
	}
	
	// 관리자
	@RequestMapping("/admin/update")
	public void campingUpdateList() throws IOException {
		campingService.updateCampingListSetDB();
	}
	
	@RequestMapping("/addlist")
	@ResponseBody
	public List<CampingDTO> campingAddList(int content_id,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String category){

		return campingService.getCampingAddList(content_id, keyword, category);
	}
	
	@RequestMapping("/review")
	@ResponseBody
	public Map<String, Object> campingReviewList(int content_id, HttpServletRequest request, int cm_no) throws Exception {
		return campingService.getCampingReviewList(content_id, request, cm_no);
	}
	
	@RequestMapping("/write")
    public String reviewWrite(int content_id, Model model) {
		model.addAttribute("Content_id", content_id);
        return "board.write";
    }
}