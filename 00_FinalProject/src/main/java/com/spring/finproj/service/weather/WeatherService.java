package com.spring.finproj.service.weather;

import org.springframework.ui.Model;

public interface WeatherService {

	void getSatellite_aop(Model model) throws Exception;
	void getNowWeather(Model model, String locX, String loxY) throws Exception;
}