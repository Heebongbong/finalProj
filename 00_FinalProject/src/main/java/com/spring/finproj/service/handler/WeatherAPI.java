package com.spring.finproj.service.handler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class WeatherAPI {
	private String nowDate;
	private String nowHour;
	
	public WeatherAPI() {
		LocalDate now = LocalDate.now();
		LocalTime time = LocalTime.now();
		String strTime = time.toString();
		
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        
        this.nowDate = now.format(formatter);
        String nowTime = strTime.replace(":","").substring(0, 2);
        String calTime = strTime.replace(":","").substring(2, 4);
        
        int calTimeValue = Integer.parseInt(calTime);

        if (calTimeValue >= 0 && calTimeValue <= 46) {
            int nowTimeValue = Integer.parseInt(nowTime);
            if(nowTimeValue == 00) {
            	this.nowHour = String.valueOf(nowTimeValue + 23);
            }else {
            	this.nowHour = String.valueOf(nowTimeValue - 1);
            }
        }
	}
	
	public String nowWeatherAPI(String curl, String locX, String locY, String minute) throws Exception {
		StringBuilder urlBuilder = new StringBuilder(curl); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + URLEncoder.encode("phamApqtKDIobE2PYsYGQbaOjZ1ubeYuzGHHRypOTUlsk/vIKv7BlDfoboSoBl+SgdrQXDuV13Xr3a4InxJjdA==", "UTF-8")); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("25", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(this.nowDate, "UTF-8")); /*‘21년 6월 28일 발표*/
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(this.nowHour+minute, "UTF-8")); /*06시 발표(정시단위) */
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(locX, "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(locY, "UTF-8")); /*예보지점의 Y 좌표값*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        
        return sb.toString();
	}
}
