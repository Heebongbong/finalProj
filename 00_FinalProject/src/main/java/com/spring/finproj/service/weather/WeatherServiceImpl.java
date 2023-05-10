package com.spring.finproj.service.weather;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.spring.finproj.model.weather.WeatherDAO;

@Service
public class WeatherServiceImpl implements WeatherService{
	@Autowired
	private WeatherDAO weatherDAO;

	@Override
	public void getSatellite(Model model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getNowWeather(Model model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getWeatherDetail(Model model, String num) {
		// TODO Auto-generated method stub
		
	}
}
