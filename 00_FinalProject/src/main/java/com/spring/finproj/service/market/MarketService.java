package com.spring.finproj.service.market;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.multipart.MultipartFile;

import com.spring.finproj.model.board.BoardDTO;

public interface MarketService {

	public Map<String, Object> getMarketList(String keyword, HttpServletRequest request, int cm_no) throws Exception;
	public int insertMarketCont(BoardDTO dto, String[] category, MultipartFile[] files, HttpServletRequest request, HttpSession session) throws Exception;

}