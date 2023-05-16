package com.spring.finproj.model.board;

import java.util.List;

public interface BoardDAO {

	//CRUD
	public List<BoardDTO> getBoardList();
	public BoardDTO getBoardContent(int cm_no);
	public int insertBoardContent(BoardDTO dto);
	public int updateBoardContent(BoardDTO dto);
	public int deleteBoardContent(int cm_no);
	
	//부가기능
	
}