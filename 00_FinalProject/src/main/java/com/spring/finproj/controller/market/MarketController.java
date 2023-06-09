package com.spring.finproj.controller.market;

import java.net.URLDecoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.finproj.model.board.BoardDTO;
import com.spring.finproj.service.market.MarketService;

@Controller
@RequestMapping("/market")
public class MarketController {
	@Autowired
	private MarketService marketService;
	
	@RequestMapping("/list")
	public String marketList(Model model, @RequestParam(required = false) String category,
			@RequestParam(required = false) String keyword) throws Exception {
		
		if(!(keyword==null||keyword=="")) {
    		keyword = URLDecoder.decode(keyword, "UTF-8");
    	}
    	if(!(category==null||category=="")) {
    		category = URLDecoder.decode(category, "UTF-8");
    	}
    	
    	model.addAttribute("M_Keyword", keyword);
		model.addAttribute("Category", category);
		
		return "market.market";
	}
	
	@RequestMapping("/addlist")
	@ResponseBody
	public Map<String , Object> marketAddList(HttpServletRequest request, 
    		@RequestParam(defaultValue = "0") int cm_no, 
    		@RequestParam(required = false) String keyword,
    		@RequestParam(required = false) String category) throws Exception{

		if(!(keyword==null||keyword=="")) {
    		keyword = URLDecoder.decode(keyword, "UTF-8");
    	}
    	if(!(category==null||category=="")) {
    		category = URLDecoder.decode(category, "UTF-8");
    	}
    	
    	return marketService.getMarketList(keyword, category, request, cm_no);
    }
	
	@RequestMapping("/write")
	public String marketWrite() {
		return "market.write";
	}
	
	@RequestMapping("/writeform")
	public String marketWriteForm(BoardDTO dto, String[] category, @RequestParam("files") MultipartFile[] files,
			HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {
		
		for(String s: category) {
			System.out.println(s);
		}
		
		int re = marketService.insertMarketCont(dto, category, files, request, session);
		if(re>0) {
			return "redirect:/market/list";
		}else {
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().println("<script>"
					+ "alert('글 작성 중 오류 발생');"
					+ "history.back();"
					+ "</script>");
			return null;
		}
	}
	
	@RequestMapping("/update")
    public String boardUpdate(HttpServletRequest request, int cm_no, Model model) throws Exception {
    	Map<String, Object> map = marketService.contentBoard(request, cm_no, model);
    	model.addAttribute("Map", map);
    	return "market.update";
  
    }
    
    @RequestMapping("/updateform")
    public String update(BoardDTO dto, @RequestParam("upfile") MultipartFile[] files, 
    		@RequestParam(value = "deletefile", required = false) String[] deletefile, HttpSession session,
    		@RequestParam(required = false) String[] category,
    		HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
    	int check = marketService.updateMarketCont(dto, files, session, request, deletefile, category);
    	
    	if(check>0) {
			return "redirect:/market/list";
		}else {
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().println("<script>"
					+ "alert('글 작성 중 오류 발생');"
					+ "history.back();"
					+ "</script>");
			return null;
		}
    }
    
    @RequestMapping("/security")
    public String marketSecurity() {
    	return "market/security";
    }
}
