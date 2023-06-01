package com.spring.finproj.controller.admin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


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