package com.spring.finproj.service.board;

import org.springframework.web.multipart.MultipartFile;

import com.spring.finproj.model.board.BoardDTO;

public interface BoardService {

	public void getCommunity(MultipartFile file);
	
	public void writeArticle(BoardDTO board);
}