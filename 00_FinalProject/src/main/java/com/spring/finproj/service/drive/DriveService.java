package com.spring.finproj.service.drive;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public interface DriveService {

	public Map<String, Object> getPathSerch(double xDri, double yDri, double xStart, double yStart) throws Exception;
	public void getGeoLocation(Model model, HttpServletRequest request) throws Exception;
	public void insertDriveRoad() throws IOException;
	public void insertDriveRoadXY();

}