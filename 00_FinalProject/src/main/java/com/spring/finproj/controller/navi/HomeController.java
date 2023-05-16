package com.spring.finproj.controller.navi;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.finproj.model.user.UserDAO;
import com.spring.finproj.model.user.UserDTO;

@Controller
public class HomeController {
	@Autowired
	private UserDAO userDAO;

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
	public String loginNavi(HttpServletRequest rq, Model model) {
		return "login.login";
	}
	
	@RequestMapping(value = "/userNavi")
	public String userNavi(HttpServletRequest rq, Model model) {
		return "user.user";
	}
}