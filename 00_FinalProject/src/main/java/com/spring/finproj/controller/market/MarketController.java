package com.spring.finproj.controller.market;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.finproj.service.market.MarketService;

@Controller
@RequestMapping("/market")
public class MarketController {
	@Autowired
	private MarketService marketService;
	
	@RequestMapping("/write")
	public String marketWriter() {
		return "market.write";
	}
	
}
