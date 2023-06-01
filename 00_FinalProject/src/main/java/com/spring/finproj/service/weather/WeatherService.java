package com.spring.finproj.service.weather;

import org.springframework.ui.Model;

public interface WeatherService {

	void getSatellite(Model model) throws Exception;
	void getNowWeather(Model model, String locX, String loxY, String lat, String lng, String address) throws Exception;
}