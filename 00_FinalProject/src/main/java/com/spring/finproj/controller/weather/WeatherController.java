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
	
	@RequestMapping("/weather")
	public String weatherSearch() {
		return "weather.weather";
	}
	
	@RequestMapping("/content")
	public String weatherContent(Model model, @RequestParam String loc) throws Exception {
		
		String num = "108";
		
		weatherService.getWeatherDetail(model, num);
		
		return "weather.content";
	}
	
	@RequestMapping("/nowWeather")
	public String nowWeather(Model model) throws Exception {
		
		weatherService.getNowWeather(model);
		
		return "weather.nowWeather";
	}
	
	@RequestMapping("/nowContent")
	public String nowContent(Model model) throws Exception {
		
		return "weather.nowContent";
	}
	
	@RequestMapping("/satellite")
	public String satellite(Model model) throws Exception {

		weatherService.getSatellite(model);
		
		return "weather.satellite";
	}
}