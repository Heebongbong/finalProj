package com.spring.finproj.service.drive;

import java.io.IOException;

import org.springframework.ui.Model;

public interface DriveService {

	public String getPathSerch(double xDri, double yDri) throws Exception;
	public void getGeoLocation(Model model) throws Exception;

}