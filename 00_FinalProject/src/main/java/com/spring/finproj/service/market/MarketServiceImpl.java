package com.spring.finproj.service.market;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.finproj.model.board.BoardDAO;

@Service
public class MarketServiceImpl implements MarketService{
	@Autowired
	private BoardDAO boardDAO;
}
