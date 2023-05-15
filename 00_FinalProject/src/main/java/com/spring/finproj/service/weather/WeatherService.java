package com.spring.finproj.service.weather;

import org.springframework.ui.Model;

public interface WeatherService {

	void getSatellite(Model model) throws Exception;

	void getNowWeather(Model model) throws Exception;

	void getWeatherDetail(Model model, String num) throws Exception;

}