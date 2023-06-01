package com.spring.finproj.controller.index;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.finproj.service.board.BoardService;
import com.spring.finproj.service.camping.CampingService;
import com.spring.finproj.service.drive.DriveService;

@Controller
public class HomeController {
	@Autowired
	private CampingService campingService;
	@Autowired
	private DriveService driveService;
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value = {"/", "/index", "/indexNavi"})
	public String homeNavi(Model model, HttpServletRequest rq) throws Exception {
		campingService.getCampingRandomList(model);
		int ran_num=(int)((Math.random()*7)+1);
		model.addAttribute("banner_num", ran_num);
		
		return "index.index";
	}
	
	@RequestMapping("/index/addlist")
	@ResponseBody
	public Map<String, Object> indexAddList(HttpServletRequest request, int cm_no) throws Exception{
		String keyword = "";
		String category = "";
		return boardService.getBoardAddList(request, cm_no, keyword, category);
	}
	
	@RequestMapping("/index/declaration")
	@ResponseBody
	public String indexDeclaration(int cm_no, String reason, HttpSession session) {
		return boardService.declaration(cm_no, reason, session);
	}


	@RequestMapping(value = "/driveNavi")
	public String driveNavi(Model model, HttpServletRequest request) throws Exception {
		driveService.getGeoLocation(model, request);
		return "drive.drive";
	}
	
}