package com.spring.finproj.model.weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherVO {
	private String baseDate;
	private String baseTime;
	private String fcstDate;
	private String fcstTime;
	private int nx;
	private int ny;

	private WeatherCateVO category;
}
