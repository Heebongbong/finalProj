package com.spring.finproj.service.board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.spring.finproj.model.board.BoardDTO;

public interface BoardService {

	public void getCommunity(MultipartFile file);
	public void writeArticle(BoardDTO board);
	public String writeBoard(BoardDTO boardDTO, MultipartFile[] files, 
			Model model, HttpSession session, HttpServletRequest request) throws Exception;
}