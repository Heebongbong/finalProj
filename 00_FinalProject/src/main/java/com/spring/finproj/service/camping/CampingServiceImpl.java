package com.spring.finproj.service.camping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.spring.finproj.model.camping.CampingDAO;
import com.spring.finproj.model.camping.CampingDTO;

@Service
public class CampingServiceImpl implements CampingService{
	@Autowired
	private CampingDAO campingDAO;

	@Override
	public void getCampingDetail(Model model, int num) throws IOException {
		
		String curl = "https://apis.data.go.kr/B551011/GoCamping/basedList?numOfRows=10&pageNo="+num+"&MobileOS=WIN&MobileApp=Camping&serviceKey=phamApqtKDIobE2PYsYGQbaOjZ1ubeYuzGHHRypOTUlsk%2FvIKv7BlDfoboSoBl%2BSgdrQXDuV13Xr3a4InxJjdA%3D%3D&_type=json";
		
		// 1. 장치에 요청할 URI를 입력한다.
        URL url = new URL(curl);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();

		con.setRequestProperty("Content-Type", "application/json");
        
        // 2. Method 타입을 정의하고 API를 전송한다.
        con.setRequestMethod("GET");
        
        con.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        
        ArrayList<CampingDTO> list = new ArrayList<CampingDTO>();
        
        JSONObject jo = new JSONObject(response.toString());
        JSONArray jo2 = jo.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");
        
        for(int i=0;i<jo2.length();i++) {
        	CampingDTO dto = new CampingDTO();
        	JSONObject cont = jo2.getJSONObject(i);
        	dto.setName(cont.getString("facltNm"));
        	dto.setImg(cont.getString("firstImageUrl"));
        	dto.setX(cont.getDouble("mapX")); //경도
        	dto.setY(cont.getDouble("mapY")); //위도
        	dto.setIntro(cont.getString("lineIntro")); // intro로 변경가능
        	dto.setHomePage(cont.getString("homepage"));
        	list.add(dto);
        }
        model.addAttribute("campings", response.toString());
        model.addAttribute("campingList", list);
		
	}
}
