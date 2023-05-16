package com.spring.finproj.model.board;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BoardDAOImpl implements BoardDAO{
	private SqlSessionTemplate sqlSession;

	@Override
	public List<BoardDTO> getBoardList() {
		return this.sqlSession.selectList("board_list");
	}
	
	
}