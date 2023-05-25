package com.spring.finproj.service.camping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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
	public void insertCampingListSetDB() throws IOException {
		for(int j=1;j<=350;j++) {
			String curl = "https://apis.data.go.kr/B551011/GoCamping/basedList?numOfRows=10&pageNo="+j+"&MobileOS=WIN&MobileApp=Camping&serviceKey=phamApqtKDIobE2PYsYGQbaOjZ1ubeYuzGHHRypOTUlsk%2FvIKv7BlDfoboSoBl%2BSgdrQXDuV13Xr3a4InxJjdA%3D%3D&_type=json";
			
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
	        	
	        	dto.setContent_id(cont.getInt("contentId"));
	        	dto.setFacltNm(cont.getString("facltNm"));
	        	dto.setLineIntro(cont.getString("lineIntro"));
	        	dto.setIntro(cont.getString("intro"));
	        	dto.setAllar(cont.getInt("allar"));
	        	dto.setFeatureNm(cont.getString("featureNm"));
	        	dto.setInduty(cont.getString("induty"));
	        	dto.setLctCl(cont.getString("lctCl"));
	        	dto.setAddr1(cont.getString("addr1"));
	        	dto.setAddr2(cont.getString("addr2"));
	        	dto.setMapX(cont.getString("mapX"));
	        	dto.setMapY(cont.getString("mapY"));
	        	dto.setTooltip(cont.getString("tooltip"));
	        	dto.setTel(cont.getString("tel"));
	        	dto.setHomepage(cont.getString("homepage"));
	        	dto.setOperPdCl(cont.getString("operPdCl"));
	        	dto.setOperDeCl(cont.getString("operDeCl"));
	        	dto.setPosblFcltyCl(cont.getString("posblFcltyCl"));
	        	dto.setExprnProgrm(cont.getString("exprnProgrm"));
	        	dto.setThemaEnvrnCl(cont.getString("themaEnvrnCl"));
	        	if(cont.getString("animalCmgCl").equals("불가능")) {
	        		dto.setAnimalCmgCl(0);
	        	}else {
	        		dto.setAnimalCmgCl(1);
	        	}
	        	dto.setFirstImageUrl(cont.getString("firstImageUrl"));
	        	
	        	list.add(dto);
	        }
	        
	        int re = campingDAO.insertCampingList(list);
	        
	        System.out.println(j+" 번째 리스트 작업 ["+re+"] 행 추가");
	        
		}
	}

	@Override
	public void getCampingList(Model model, String loc) throws IOException {
		// TODO Auto-generated method stub
		List<CampingDTO> list = campingDAO.getCampingLocList(loc);
		model.addAttribute("CampingList", list);
		System.out.println(list);
	}

	@Override
	public void getCampingRandomList(Model model) {
		// TODO Auto-generated method stub
		List<CampingDTO> list = campingDAO.getCampingRandomList();
		model.addAttribute("CampingList", list);
	}

	@Override
	public void getCampingContent(Model model, int content_id) throws IOException {
		CampingDTO dto = campingDAO.getCampingContent(content_id);
		model.addAttribute("Content", dto);
		
	}
}