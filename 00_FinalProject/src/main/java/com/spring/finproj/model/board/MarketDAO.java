package com.spring.finproj.model.board;

import java.util.List;

public interface MarketDAO {

	public List<BoardDTO> getMarketList();
	public List<BoardDTO> getMarketList(String keyword);
	public List<BoardDTO> getMarketList(List<String> hashList);
	public int insertBoardContent(BoardDTO dto);
	public int insertMarketContent(BoardDTO dto);
	public int getCmMax();

}