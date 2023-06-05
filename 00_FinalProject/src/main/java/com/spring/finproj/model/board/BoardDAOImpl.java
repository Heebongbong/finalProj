package com.spring.finproj.model.board;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAOImpl implements BoardDAO{
	@Autowired
	private SqlSessionTemplate sqlSession;

	//CRUD
	@Override
	public List<BoardDTO> getBoardList() {
		return this.sqlSession.selectList("board_list");
	}
	@Override
	public BoardDTO getBoardContent(int cm_no) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("board_cont", cm_no);
	}

	@Override
	public int insertBoardContent(BoardDTO dto) {
		// TODO Auto-generated method stub
		return sqlSession.insert("board_insert", dto);
	}

	@Override
	public int updateBoardContent(BoardDTO dto) {
		// TODO Auto-generated method stub
		return sqlSession.update("board_update", dto);
	}

	@Override
	public int deleteBoardContent(int cm_no) {
		// TODO Auto-generated method stub
		return sqlSession.delete("board_delete", cm_no);
	}
	
	//부가기능
	
	//overlode 키워드로 리스트 받기
	@Override
	public List<BoardDTO> getBoardList(String keyword) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("board_list_search", keyword);
	}
	//게시글 인덱스 이하 5건 가져오기 - 무한스크롤 사용
	@Override
	public List<BoardDTO> getBoardList(int cm_no) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("board_list_add", cm_no);
	}
	//게시글 인덱스 이하 무한스크롤 - 키워드 사용
	@Override
	public List<BoardDTO> getBoardList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("board_list_add_search", map);
	}
	@Override
	public List<BoardDTO> getBoardHashKeyList(List<String> list) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("board_list_hash_list", list);
	}
	@Override
	public List<BoardDTO> getBoardHashKeyMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return  sqlSession.selectList("board_list_hash_map", map);
	}
	@Override
	public int insertDeclaration(Map<String, String> decla) {
		// TODO Auto-generated method stub
		return sqlSession.insert("declaration_insert", decla);
	}
	@Override
	public int checkDeclaration(Map<String, String> decla) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("declaration_content", decla);
	}
	@Override
	public int deleteAccuserContent(int cm_no) {
		// TODO Auto-generated method stub
		return sqlSession.delete("board_accuser_delete", cm_no);
	}
	@Override
	public int deleteMentionContent(int cm_no) {
		// TODO Auto-generated method stub
		return sqlSession.delete("board_mention_delete", cm_no);
	}
	@Override
	public int deleteRecommandContent(int cm_no) {
		// TODO Auto-generated method stub
		return sqlSession.delete("board_recommand_delete", cm_no);
	}
	@Override
	public List<Integer> getBoardLikeList(int user_no) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("board_like_cm_list", user_no);
	}
	@Override
	public int getBoardLikeCount(int cm_no) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("board_like_count", cm_no);
	}
	@Override
	public int deleteBoardLike(Map<String, Integer> keyMap) {
		// TODO Auto-generated method stub
		return sqlSession.delete("board_like_delete", keyMap);
	}
	@Override
	public int insertBoardLike(Map<String, Integer> keyMap) {
		// TODO Auto-generated method stub
		return sqlSession.insert("board_like_insert", keyMap);
	}
	@Override
	public List<BoardDTO> getBoardUserList(int user_no) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("board_user_list", user_no);
	}
	@Override
	public List<BoardDTO> getBoardUserList(Map<String, Integer> map) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("board_user_list_map", map);
	}
	@Override
	public List<BoardDTO> getBoardUserLikeList(int user_no) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("board_user_like_list", user_no);
	}
	@Override
	public List<BoardDTO> getBoardUserLikeList(Map<String, Integer> map) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("board_user_like_list_map", map);
	}
	@Override
	public int insertReviewCont(BoardDTO boardDTO) {
		// TODO Auto-generated method stub
		return sqlSession.insert("review_insert", boardDTO);
	}
	@Override
	public int deleteReview(int cm_no) {
		// TODO Auto-generated method stub
		return sqlSession.delete("board_review_delete", cm_no);
	}

	@Override
	public int updateAlarmUserCheck(int user_no) {
		// TODO Auto-generated method stub
		return sqlSession.update("alarm_user_check", user_no);
	}
	@Override
	public int deleteAlarmUserField(Map<String, Integer> map) {
		// TODO Auto-generated method stub
		return sqlSession.delete("alarm_user_delete", map);
	}
	
	// 관리자
	@Override
	public List<BoardDTO> getAccuseList() {
		// TODO Auto-generated method stub
		return sqlSession.selectList("accuse_list");
	}
	@Override
	public List<BoardDTO> getAccusedList() {
		// TODO Auto-generated method stub
		return sqlSession.selectList("accused_list");
	}
}