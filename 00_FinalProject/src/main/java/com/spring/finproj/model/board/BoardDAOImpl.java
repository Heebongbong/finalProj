package com.spring.finproj.model.board;

import java.util.List;

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
}