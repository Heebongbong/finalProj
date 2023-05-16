package com.spring.finproj.service.weather;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.spring.finproj.model.weather.WeatherDAO;
import com.spring.finproj.model.weather.WeatherDTO;

@Service
public class WeatherServiceImpl implements WeatherService{
	@Autowired
	private WeatherDAO weatherDAO;

	@Override
	public void getSatellite(Model model) throws Exception {
		
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
		
		
		System.out.println("x좌표 :"+locX);
		System.out.println("x좌표 :"+locY);
		LocalDate now = LocalDate.now();
		LocalTime time = LocalTime.now();
		String strTime = time.toString();
		
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        
        String nowDate = now.format(formatter);
        String nowTime = strTime.replace(":","").substring(0, 2);
        String calTime = strTime.replace(":","").substring(2, 4);
        
        int calTimeValue = Integer.parseInt(calTime);

        if (calTimeValue >= 0 && calTimeValue <= 46) {
            int nowTimeValue = Integer.parseInt(nowTime);
            if(nowTimeValue == 00) {
            	nowTime = String.valueOf(nowTimeValue + 23);
            }else {
            	nowTime = String.valueOf(nowTimeValue - 1);
            }
        }
        
		StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + URLEncoder.encode("phamApqtKDIobE2PYsYGQbaOjZ1ubeYuzGHHRypOTUlsk/vIKv7BlDfoboSoBl+SgdrQXDuV13Xr3a4InxJjdA==", "UTF-8")); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("25", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(nowDate, "UTF-8")); /*‘21년 6월 28일 발표*/
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(nowTime+"00", "UTF-8")); /*06시 발표(정시단위) */
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
        
        StringBuilder urlBuilder2 = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst"); /*URL*/
        urlBuilder2.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + URLEncoder.encode("phamApqtKDIobE2PYsYGQbaOjZ1ubeYuzGHHRypOTUlsk/vIKv7BlDfoboSoBl+SgdrQXDuV13Xr3a4InxJjdA==", "UTF-8")); /*Service Key*/
        urlBuilder2.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
        urlBuilder2.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("25", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder2.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default: XML*/
        urlBuilder2.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(nowDate, "UTF-8")); /*‘21년 6월 28일 발표*/
        urlBuilder2.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(nowTime+"30", "UTF-8")); /*12시 발표(정시단위) */
        urlBuilder2.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode(locX, "UTF-8")); /*예보지점의 X 좌표값*/
        urlBuilder2.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode(locY, "UTF-8")); /*예보지점의 Y 좌표값*/
        URL url2 = new URL(urlBuilder2.toString());
        HttpURLConnection conn2 = (HttpURLConnection) url2.openConnection();
        conn2.setRequestMethod("GET");
        conn2.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn2.getResponseCode());
        BufferedReader rd2;
        if(conn2.getResponseCode() >= 200 && conn2.getResponseCode() <= 300) {
        	rd2 = new BufferedReader(new InputStreamReader(conn2.getInputStream()));
        } else {
        	rd2 = new BufferedReader(new InputStreamReader(conn2.getErrorStream()));
        }
        StringBuilder sb2 = new StringBuilder();
        String line2;
        while ((line2 = rd2.readLine()) != null) {
        	sb2.append(line2);
        }
        rd.close();
        conn.disconnect();
        System.out.println("현재실황: "+sb.toString());
        System.out.println("날씨예보: "+sb2.toString());
        
        model.addAttribute("str", sb.toString());
        model.addAttribute("str2", sb2.toString());
	}
	
	
	@Override
	public void getWeatherDetail(Model model, String num) throws Exception {
		
		LocalDate now = LocalDate.now();
		LocalDate start = now.plusDays(-2);
		LocalDate end = now.plusDays(-1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        
        String strStart = start.format(formatter); //2일 전
        String strEnd = end.format(formatter); //1일 전
        
		String curl = "http://apis.data.go.kr/1360000/AsosHourlyInfoService/getWthrDataList";
		
		StringBuilder urlBuilder = new StringBuilder(curl); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "="+ URLEncoder.encode("phamApqtKDIobE2PYsYGQbaOjZ1ubeYuzGHHRypOTUlsk/vIKv7BlDfoboSoBl+SgdrQXDuV13Xr3a4InxJjdA==", "UTF-8")); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호 Default : 10*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("25", "UTF-8")); /*한 페이지 결과 수 Default : 1*/
        urlBuilder.append("&" + URLEncoder.encode("dataType","UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); /*요청자료형식(XML/JSON) Default : XML*/
        urlBuilder.append("&" + URLEncoder.encode("dataCd","UTF-8") + "=" + URLEncoder.encode("ASOS", "UTF-8")); /*자료 분류 코드(ASOS)*/
        urlBuilder.append("&" + URLEncoder.encode("dateCd","UTF-8") + "=" + URLEncoder.encode("HR", "UTF-8")); /*날짜 분류 코드(HR)*/
        urlBuilder.append("&" + URLEncoder.encode("startDt","UTF-8") + "=" + URLEncoder.encode(strStart, "UTF-8")); /*조회 기간 시작일(YYYYMMDD)*/
        urlBuilder.append("&" + URLEncoder.encode("startHh","UTF-8") + "=" + URLEncoder.encode("01", "UTF-8")); /*조회 기간 시작시(HH)*/
        urlBuilder.append("&" + URLEncoder.encode("endDt","UTF-8") + "=" + URLEncoder.encode(strEnd, "UTF-8")); /*조회 기간 종료일(YYYYMMDD) (전일(D-1) 까지 제공)*/
        urlBuilder.append("&" + URLEncoder.encode("endHh","UTF-8") + "=" + URLEncoder.encode("01", "UTF-8")); /*조회 기간 종료시(HH)*/
        urlBuilder.append("&" + URLEncoder.encode("stnIds","UTF-8") + "=" + URLEncoder.encode(num, "UTF-8")); /*종관기상관측 지점 번호 (활용가이드 하단 첨부 참조)*/
		
		// 1. 장치에 요청할 URI를 입력한다.
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection con = (HttpURLConnection)url.openConnection();

        con.setRequestProperty("Content-Type", "application/json");

        // 2. Method 타입을 정의하고 API를 전송한다.
        con.setRequestMethod("GET");
        con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", "4sqz6l4y8y");
        con.setRequestProperty("X-NCP-APIGW-API-KEY", "bMoniLVncq0fF2RmptqmnYjnkVvJgfP0C9vDvbrh");
        
        con.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        
        JSONObject jo = new JSONObject(response.toString());
        System.out.println("디테일 리스트 : "+response.toString());
        
        
        JSONArray jo2 = jo.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");
        
        ArrayList<WeatherDTO> list = new ArrayList<WeatherDTO>();
        
        for(int i=0;i<jo2.length();i++) {
        	WeatherDTO dto = new WeatherDTO();
        	JSONObject cont = jo2.getJSONObject(i);
        	
        	dto.setTm(cont.getString("tm")); 					//시간
        	dto.setClfmAbbrCd(cont.getString("clfmAbbrCd"));  	//운형
        	dto.setDc10Tca(cont.getString("dc10Tca"));			//운량
        	dto.setHm(cont.getString("hm"));					//습도
        	dto.setWd(cont.getString("wd"));					//풍향
        	dto.setWs(cont.getString("ws"));					//풍속
        	dto.setTa(cont.getString("ta"));					//기온
        	
        	list.add(dto);
        }
		
        model.addAttribute("list", list);
		
	}
}
