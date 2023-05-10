package com.spring.finproj.service.drive;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.finproj.model.drive.DriveDAO;

@Service
public class DriveServiceImpl implements DriveService{
	@Autowired
	private DriveDAO driveDAO;

	@Override
	public String getPathSerch(double xDri, double yDri) throws Exception {
		String stx = "126.9831230334937";
		String sty = "37.567944413725904";
		
		String curl = "https://naveropenapi.apigw.ntruss.com/map-direction-15/v1/driving";
		
		curl += "?start="+stx+","+sty+"&goal="+xDri+","+yDri;
		
		// 1. 장치에 요청할 URI를 입력한다.
        URL url = new URL(curl.toString());
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
        JSONArray jo2 = jo.getJSONObject("route").getJSONArray("traoptimal");
        JSONObject jo3 = jo2.getJSONObject(0);
        JSONArray jo4 = jo3.getJSONArray("path");
        
		return jo4.toString();
	}
}
