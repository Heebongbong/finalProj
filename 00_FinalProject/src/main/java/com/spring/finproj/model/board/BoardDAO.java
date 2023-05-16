package com.spring.finproj.model.board;

import java.util.List;

public interface BoardDAO {
	
	// 게시판 전체 리스트를 가져오는 메서드
	List<BoardDTO> getBoardList();
	
	
}