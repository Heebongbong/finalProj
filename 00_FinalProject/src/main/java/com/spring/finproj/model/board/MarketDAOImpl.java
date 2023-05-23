package com.spring.finproj.model.board;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MarketDAOImpl implements MarketDAO{
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public List<BoardDTO> getMarketList() {
		// TODO Auto-generated method stub
		return sqlSession.selectList("market_list");
	}

	@Override
	public List<BoardDTO> getMarketList(String keyword) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("market_list_keyword", keyword);
	}

	@Override
	public List<BoardDTO> getMarketList(List<String> hashList) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("market_list_hashtag", hashList);
	}

	@Override
	public int insertBoardContent(BoardDTO dto) {
		// TODO Auto-generated method stub
		return sqlSession.insert("market_insert_board", dto);
	}

	@Override
	public int insertMarketContent(BoardDTO dto) {
		// TODO Auto-generated method stub
		return sqlSession.insert("market_insert_market", dto);
	}

	@Override
	public int getCmMax() {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("market_cmno_max");
	}
}
