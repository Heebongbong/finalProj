package com.spring.finproj.controller.camping;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.finproj.model.board.BoardDTO;
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
		
		campingService.getCampingContent(model, content_id);
		return "camping.details";
	}
	
	@RequestMapping("/list/insert")
	public String campingInsertList() throws IOException {
		campingService.insertCampingListSetDB();
		return "redirect:/index";
	}
	
	@RequestMapping("/addlist")
	@ResponseBody
	public List<CampingDTO> campingAddList(int content_id, String keyword, String keyword2){
		return campingService.getCampingAddList(content_id, keyword, keyword2);
	}
	
	@RequestMapping("/review")
	@ResponseBody
	public Map<String, Object> campingReviewList(int content_id, HttpServletRequest request, int cm_no) throws Exception {
		return campingService.getCampingReviewList(content_id, request, cm_no);
	}
	
	@RequestMapping("/review/write")
    public String reviewWrite() {
        return "camping.write";
    }
    
}