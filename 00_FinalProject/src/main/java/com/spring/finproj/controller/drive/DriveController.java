package com.spring.finproj.controller.drive;

import java.io.IOException;

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
	public String driveContent(@RequestParam double xDri,@RequestParam double yDri) throws Exception {

		return driveService.getPathSerch(xDri, yDri);
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
}