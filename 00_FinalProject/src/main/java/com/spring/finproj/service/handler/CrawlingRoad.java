package com.spring.finproj.service.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.spring.finproj.model.drive.DriveRoadDTO;

public class CrawlingRoad {

	public List<DriveRoadDTO> RoadInfo() throws IOException {
		
		String url;
		List<DriveRoadDTO> list = new ArrayList<DriveRoadDTO>();
		
		for(int i=1;i<=9;i++) {
			url = "https://www.juso.go.kr/bggallery/BackgroundBoardList.do?currentPage="+i+"&countPerPage=6&searchType=&keyword=";
			
			Connection con = Jsoup.connect(url);
			Document doc = con.get();
			
			Element cont = doc.getElementById("gal_list");
			Elements conts = cont.getElementsByTag("li");
		
			for(Element e : conts) {
				
				Elements a = e.select("a");
				String a_id = a.attr("id");
				Elements imgs = e.select(".listImg");
				Elements addr = e.select(".place");
				Elements name = e.select(".subject");
				
				String a_no;
				if(a_id.charAt(a_id.length()-2)=='_') {
					a_no = a_id.substring(a_id.length()-1, a_id.length());
				}else {
					a_no = a_id.substring(a_id.length()-2, a_id.length());
				}
				
				String info = getInfo(a_no);
				
				DriveRoadDTO dto = new DriveRoadDTO();
				dto.setImg("https://www.juso.go.kr"+imgs.attr("src"));
				dto.setName(name.text());
				dto.setAddress(addr.text());
				dto.setInfo(info);
				
				list.add(dto);
			}
		}
		
		return list;
	}

	private String getInfo(String a_no) throws IOException {
		// TODO Auto-generated method stub
		String info = "";
		
		String url = "https://www.juso.go.kr//bggallery/backgroundDetail.do?mgtSn="+a_no;
		
		Connection con = Jsoup.connect(url);
		Document doc = con.get();
		
		Elements conts = doc.getElementsByClass("iconEx");
		for(Element e : conts) {
			Elements li = e.getElementsByTag("li");
			
			
			info += li.text() + "<br>";
		}
		return info;
	}

	private void getXY(String text, DriveRoadDTO dto) throws IOException {
		// TODO Auto-generated method stub
		
		StringBuilder urlBuilder = new StringBuilder("https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode");
        urlBuilder.append("?" + URLEncoder.encode("query","UTF-8") + "=" + URLEncoder.encode(text, "UTF-8"));
        
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
        System.out.println(jo.toString());
	}
}