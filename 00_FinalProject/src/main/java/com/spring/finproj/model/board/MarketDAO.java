package com.spring.finproj.model.board;

import java.util.List;
import java.util.Map;

public interface MarketDAO {

	public List<BoardDTO> getMarketList();
	//public List<BoardDTO> getMarketList(String keyword);
	public List<BoardDTO> getMarketList(List<String> hashList);
	public int insertBoardContent(BoardDTO dto);
	public int insertMarketContent(BoardDTO dto);
	public int updateBoardContent(BoardDTO dto);
	public int updateMarketContent(BoardDTO dto);
	public BoardDTO getMarketContent(int cm_no);
	public int getCmMax();
	public List<BoardDTO> getMarketList(int cm_no);
	public List<BoardDTO> getMarketHashKeyMap(Map<String, Object> map);
	public List<BoardDTO> getMarketKeyMap(Map<String, Object> map);
	public List<BoardDTO> getMarketList(Map<String, Object> map);

}