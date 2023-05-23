package com.spring.finproj.service.board;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.spring.finproj.model.board.BoardDTO;

public interface BoardService {

	public void getBoardList(HttpServletRequest request, Model model, String keyword) throws Exception;
	public int writeBoard(BoardDTO boardDTO, MultipartFile[] files, 
			Model model, String[] category, HttpSession session, HttpServletRequest request) throws Exception;
	public Map<String, Object> getBoardAddList(HttpServletRequest request, int cm_no, String keyword) throws Exception;
}