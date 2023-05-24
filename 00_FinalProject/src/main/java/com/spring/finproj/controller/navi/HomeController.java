package com.spring.finproj.controller.navi;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.finproj.service.camping.CampingService;
import com.spring.finproj.service.drive.DriveService;

@Controller
public class HomeController {
	@Autowired
	private CampingService campingService;
	@Autowired
	private DriveService driveService;
	
	@RequestMapping(value = {"/", "/index", "/indexNavi"})
	public String homeNavi(Model model, HttpServletRequest rq) throws Exception {
		campingService.getCampingRandomList(model);
		int ran_num=(int)((Math.random()*7)+1);
		model.addAttribute("banner_num", ran_num);
		
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
	public String driveNavi(Model model, HttpServletRequest request) throws Exception {
		driveService.getGeoLocation(model, request);
		return "drive.drive";
	}

	@RequestMapping(value = "/userNavi")
	public String userNavi(HttpServletRequest rq, Model model) {
		return "user.user";
	}
}