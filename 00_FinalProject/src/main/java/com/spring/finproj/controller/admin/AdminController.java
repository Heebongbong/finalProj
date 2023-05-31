package com.spring.finproj.controller.admin;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.finproj.service.camping.CampingService;


@Controller
public class AdminController {
	
	@RequestMapping(value = {"/admin", "/admin/chat"})
	public String adminNavi() {
		return "admin.admin";
	}
	@RequestMapping("/admin/camping")
	public String adminCampingNavi() {
		return "admin.camping";
	}
	@RequestMapping("/admin/waypoint")
	public String adminWaypointNavi() {
		return "admin.waypoint";
	}
}