package com.spring.finproj.controller.navi;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping(value = {"/", "/index", "/indexNavi"})
	public String homeNavi() {
		return "index.index";
	}

	@RequestMapping(value = "/campingNavi")
	public String campingNavi() {
		return "camping.camping";
	}

	@RequestMapping(value = "/weatherNavi")
	public String weatherNavi() {
		return "weather.weather";
	}

	@RequestMapping(value = "/driveNavi")
	public String driveNavi() {
		return "drive.drive";
	}

	@RequestMapping(value = "/boardNavi")
	public String boardNavi() {
		return "board.board";
	}

	@RequestMapping(value = "/marketNavi")
	public String marketNavi() {
		return "market.market";
	}
	
	@RequestMapping(value = "/loginNavi")
	public String loginNavi(HttpServletRequest rq) {
		return "login.login";
	}
}