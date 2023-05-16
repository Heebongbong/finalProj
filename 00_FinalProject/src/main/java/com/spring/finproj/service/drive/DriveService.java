package com.spring.finproj.service.drive;

import org.springframework.ui.Model;

public interface DriveService {

	public String getPathSerch(double xDri, double yDri) throws Exception;
	public void getGeoLocation(Model model) throws Exception;

}