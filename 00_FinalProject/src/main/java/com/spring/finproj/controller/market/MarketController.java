package com.spring.finproj.controller.market;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.spring.finproj.model.board.BoardDTO;
import com.spring.finproj.service.market.MarketService;

@Controller
@RequestMapping("/market")
public class MarketController {
	@Autowired
	private MarketService marketService;
	
	@RequestMapping({"/list"})
	public String marketList(@RequestParam(required = false) String keyword, Model model, HttpServletRequest request) throws Exception {
		
		marketService.getMarketList(model, keyword, request);
		return "market.market";
	}
	
	@RequestMapping("/write")
	public String marketWrite() {
		return "market.write";
	}
	
	@RequestMapping("/writeform")
	public String marketWriteForm(BoardDTO dto, String[] category, @RequestParam("files") MultipartFile[] files,
			HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {
		
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
}
