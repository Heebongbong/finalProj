package com.spring.finproj.model.weather;

import lombok.Data;

@Data
public class WeatherDTO {
	private String clfmAbbrCd; //운형
	private String dc10Tca; //운량
	private String hm; //습도
	private String wd; //풍향
	private String ws; //풍속
	private String ta; //기온
	private String tm; //시간
}
