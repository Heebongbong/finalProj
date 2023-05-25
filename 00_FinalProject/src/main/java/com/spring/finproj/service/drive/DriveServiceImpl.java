package com.spring.finproj.service.drive;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.spring.finproj.model.drive.DriveDAO;
import com.spring.finproj.service.handler.Signature;

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

	@Override
	public void getGeoLocation(Model model, HttpServletRequest request) throws Exception {
		
		Signature sign = new Signature();
		String signUrl = "/geolocation/v2/geoLocation?ip=112.221.156.36&ext=t&responseFormatType=json";
		String curl = "https://geolocation.apigw.ntruss.com"+signUrl;
		// 1. 장치에 요청할 URI를 입력한다.
        URL url = new URL(curl);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();

        con.setRequestProperty("Content-Type", "application/json");

        // 2. Method 타입을 정의하고 API를 전송한다.
        con.setRequestMethod("GET");
        con.setRequestProperty("x-ncp-apigw-timestamp", sign.getNowTime());
        con.setRequestProperty("x-ncp-iam-access-key", "So6WkkHqyaafmNSxc05s");
        con.setRequestProperty("x-ncp-apigw-signature-v2", sign.makeGetSignature(signUrl));
        System.out.println(con.getResponseCode());
        BufferedReader in;
        if(con.getResponseCode() >= 200 && con.getResponseCode() <= 300) {
        	in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {
        	in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        
        JSONObject jo = new JSONObject(response.toString());

        List<String> list = new ArrayList<String>();
        list.add(jo.toString());
        model.addAttribute("GpsDTO", list);
	}
}
