package com.spring.finproj.service.board;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.spring.finproj.model.board.BoardDTO;

public interface BoardService {
	public int writeBoard(BoardDTO boardDTO, MultipartFile[] files, 
			Model model, String[] category, HttpSession session, HttpServletRequest request) throws Exception;
	public Map<String, Object> getBoardAddList(HttpServletRequest request, int cm_no, String keyword, String category) throws Exception;
	public String declaration(int cm_no, String reason, HttpSession session);
	public int deleteBoardCont(int cm_no, HttpServletRequest request);
	public Map<String, Object> contentBoard(int cm_no);
	public int updateBoard(BoardDTO boardDTO, MultipartFile[] files, Model model, String[] category, HttpSession session, HttpServletRequest request) throws Exception;
}