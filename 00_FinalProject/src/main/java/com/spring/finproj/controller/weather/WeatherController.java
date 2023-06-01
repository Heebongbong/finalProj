package com.spring.finproj.controller.weather;

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
	public String nowWeather(Model model, 
			@RequestParam(defaultValue = "127")String locX, 
			@RequestParam(defaultValue = "60")String locY,
			@RequestParam(required = false)String address,
			@RequestParam(required = false)String lat,
			@RequestParam(required = false)String lng) throws Exception {
		System.out.println(locX);
		System.out.println(locY);
		weatherService.getNowWeather(model, locX, locY, lat, lng, address);
		
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