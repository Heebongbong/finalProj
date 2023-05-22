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
}
