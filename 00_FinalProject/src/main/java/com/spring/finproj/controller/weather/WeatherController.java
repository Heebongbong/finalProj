package com.spring.finproj.controller.weather;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.finproj.service.weather.WeatherService;

@Controller
@RequestMapping("/weather")
public class WeatherController {
	@Autowired
	private WeatherService weatherService;
	
	@RequestMapping("/now")
	public String nowWeather(Model model, String locX, String locY) throws Exception {
		
		weatherService.getNowWeather(model, locX, locY);
		
		return "weather.now";
	}
	
	@RequestMapping("/star")
	public String stella(Model model) throws Exception {
		weatherService.getSatellite(model);
		return "weather.star";
	}
	
	@RequestMapping("/stella")
	public String star(Model model) throws Exception {
		return "weather.stella";
	}
}