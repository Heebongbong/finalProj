package com.spring.finproj.service.market;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.spring.finproj.model.board.BoardDTO;
import com.spring.finproj.model.board.MarketDAO;

@Service
public class MarketServiceImpl implements MarketService{
	@Autowired
	private MarketDAO marketDAO;

	@Override
	public void getMarketList(Model model) {
		// TODO Auto-generated method stub
		List<BoardDTO> list = marketDAO.getMarketList();
		model.addAttribute("MarketList", list);
	}
}
