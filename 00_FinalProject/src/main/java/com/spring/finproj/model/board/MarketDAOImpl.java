package com.spring.finproj.model.board;

import java.util.List;
import java.util.Map;

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
	public List<BoardDTO> getMarketList(int cm_no) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("market_list_cmno", cm_no);
	}

	@Override
	public List<BoardDTO> getMarketHashKeyMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("market_list_cmno_hash", map);
	}

	@Override
	public List<BoardDTO> getMarketKeyMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("market_list_cmno_key", map);
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
