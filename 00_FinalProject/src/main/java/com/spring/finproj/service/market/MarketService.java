package com.spring.finproj.service.market;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.spring.finproj.model.board.BoardDTO;

public interface MarketService {

	public Map<String, Object> getMarketList(String keyword, String category, HttpServletRequest request, int cm_no) throws Exception;
	public int insertMarketCont(BoardDTO dto, String[] category, MultipartFile[] files, HttpServletRequest request, HttpSession session) throws Exception;
	public Map<String, Object> contentBoard(HttpServletRequest request, int cm_no, Model model) throws Exception;
	public int updateMarketCont(BoardDTO boardDTO, MultipartFile[] files, HttpSession session, HttpServletRequest request) throws Exception;

}