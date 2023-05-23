package com.spring.finproj.service.market;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.spring.finproj.model.board.BoardDTO;

public interface MarketService {

	public void getMarketList(Model model, String keyword, HttpServletRequest request) throws Exception;
	public int insertMarketCont(BoardDTO dto, String[] category, MultipartFile[] files, HttpServletRequest request, HttpSession session) throws Exception;

}