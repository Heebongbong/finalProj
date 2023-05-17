package com.spring.finproj.service.weather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.spring.finproj.model.weather.WeatherDAO;
import com.spring.finproj.service.handler.WeatherAPI;

@Service
public class WeatherServiceImpl implements WeatherService{
	@Autowired
	private WeatherDAO weatherDAO;

	@Override
	public void getSatellite_aop(Model model) throws Exception {
		
		LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String today = now.format(formatter);
		
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/SatlitImgInfoService/getInsightSatlit"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + URLEncoder.encode("phamApqtKDIobE2PYsYGQbaOjZ1ubeYuzGHHRypOTUlsk/vIKv7BlDfoboSoBl+SgdrQXDuV13Xr3a4InxJjdA==", "UTF-8")); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호 Default: 1*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("sat","UTF-8") + "=" + URLEncoder.encode("G2", "UTF-8")); /*위성구분 -G2: 천리안위성 2A호*/
        urlBuilder.append("&" + URLEncoder.encode("data","UTF-8") + "=" + URLEncoder.encode("ir105", "UTF-8")); /*영상구분 -적외영상(ir105) -가시영상(vi006) -수증기영상(wv069) -단파적외영상(sw038) -RGB 컬러(rgbt) -RGB 주야간합성(rgbdn)*/
        urlBuilder.append("&" + URLEncoder.encode("area","UTF-8") + "=" + URLEncoder.encode("ko", "UTF-8")); /*지역구분 -전구(fd) -동아시아(ea) -한반도(ko)*/
        urlBuilder.append("&" + URLEncoder.encode("time","UTF-8") + "=" + URLEncoder.encode(today, "UTF-8")); /*년월일(YYYYMMDD)*/
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
        
        JSONObject jo = new JSONObject(sb.toString());
        JSONArray ja = jo.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");
        String str = (String) ja.getJSONObject(0).get("satImgC-file");
        
        StringTokenizer st = new StringTokenizer(str.substring(1, str.length()-1), ",");
        ArrayList<String> list = new ArrayList<String>();
        
        int count = 0;
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (count >= 0 && count <= 9) {
                list.add(token);
            }
            count++;
        }
        
        model.addAttribute("list", list);
	}
	
	@Override
	public void getNowWeather(Model model, String locX, String locY) throws Exception {
		
        WeatherAPI wapi = new WeatherAPI();
        
        String sb1 = wapi.nowWeatherAPI("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst",
        		locX, locY, "00");
        String sb2 = wapi.nowWeatherAPI("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst",
        		locX, locY, "30");
        System.out.println("현재실황: "+sb1);
        System.out.println("날씨예보: "+sb2);
        
        model.addAttribute("str", sb1);
        model.addAttribute("str2", sb2);
	}
}
