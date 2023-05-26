package com.spring.finproj.model.board;

import java.util.List;
import java.util.Map;

public interface BoardDAO {

	//CRUD
	public List<BoardDTO> getBoardList();
	public BoardDTO getBoardContent(int cm_no);
	public int insertBoardContent(BoardDTO dto);
	public int updateBoardContent(BoardDTO dto);
	public int deleteBoardContent(int cm_no);
	public int deleteAccuserContent(int cm_no);
	public int deleteAlarmContent(int cm_no);
	public int deleteMentionContent(int cm_no);
	public int deleteRecommandContent(int cm_no);
	
	//부가기능
	public List<BoardDTO> getBoardList(String keyword);
	public List<BoardDTO> getBoardList(int cm_no);
	public List<BoardDTO> getBoardList(Map<String, Object> map);
	public List<BoardDTO> getBoardHashKeyList(List<String> hashList);
	public List<BoardDTO> getBoardHashKeyMap(Map<String, Object> map);
	public int insertDeclaration(Map<String, String> decla);
	public int checkDeclaration(Map<String, String> decla);
	
}