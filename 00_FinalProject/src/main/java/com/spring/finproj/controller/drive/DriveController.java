package com.spring.finproj.controller.drive;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.finproj.service.drive.DriveService;

@Controller
@RequestMapping("/drive")
public class DriveController {
	@Autowired
	private DriveService driveService;
	
	@ResponseBody
	@RequestMapping("/content")
	public Map<String, Object> driveContent(@RequestParam double xDri,@RequestParam double yDri,
			@RequestParam(required = false) double xStart, @RequestParam(required = false) double yStart) throws Exception {

		return driveService.getPathSerch(xDri, yDri, xStart, yStart);
	}
	
	@RequestMapping("/load/insert")
	public String driveLordInsert() throws IOException {
		driveService.insertDriveRoad();
		return "redirect:/driveNavi";
	}

	@RequestMapping("/load/insert/xy")
	public String driveLordInsertXY() throws IOException {
		driveService.insertDriveRoadXY();
		return "redirect:/driveNavi";
	}
	
	@RequestMapping("/rev/geocode")
	@ResponseBody
	public String driveReverseGeocode(double coords_x, double coords_y) throws Exception{
		
		return driveService.getReverseGeo(coords_x, coords_y);
	}
}