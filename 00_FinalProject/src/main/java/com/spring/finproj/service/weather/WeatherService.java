package com.spring.finproj.service.weather;

import org.springframework.ui.Model;

public interface WeatherService {

	void getSatellite(Model model);

	void getNowWeather(Model model);

	void getWeatherDetail(Model model, String num);

}