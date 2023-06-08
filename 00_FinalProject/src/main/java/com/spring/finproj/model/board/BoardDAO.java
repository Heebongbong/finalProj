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

	//추가 CRUD
	public int insertReviewCont(BoardDTO boardDTO);
	
	public int deleteAccuserContent(int cm_no);
	public int deleteMentionContent(int cm_no);
	public int deleteRecommandContent(int cm_no);
	public int deleteReview(int cm_no);
	
	//부가기능
	public List<BoardDTO> getBoardList(String keyword);
	public List<BoardDTO> getBoardList(int cm_no);
	public List<BoardDTO> getBoardList(Map<String, Object> map);
	public List<BoardDTO> getBoardHashKeyList(List<String> hashList);
	public List<BoardDTO> getBoardHashKeyMap(Map<String, Object> map);
	public int insertDeclaration(Map<String, String> decla);
	public int checkDeclaration(Map<String, String> decla);
	public List<Integer> getBoardLikeList(int user_no);
	public int getBoardLikeCount(int cm_no);
	public int deleteBoardLike(Map<String, Integer> keyMap);
	public int insertBoardLike(Map<String, Integer> keyMap);
	public List<BoardDTO> getBoardUserList(int user_no);
	public List<BoardDTO> getBoardUserList(Map<String, Integer> map);
	public List<BoardDTO> getBoardUserLikeList(int user_no);
	public List<BoardDTO> getBoardUserLikeList(Map<String, Integer> map);
	public int updateAlarmUserCheck(int user_no);
	public int deleteAlarmUserField(Map<String, Integer> map);
	
	// 관리자
	public List<BoardDTO> getAccuseList();
	public List<BoardDTO> getAccusedList();
	public int deleteAcuuseCont(Map<String, Integer> map);
	
}