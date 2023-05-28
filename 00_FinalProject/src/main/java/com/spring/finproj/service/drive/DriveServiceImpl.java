package com.spring.finproj.service.drive;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.spring.finproj.model.drive.DriveDAO;
import com.spring.finproj.model.drive.DriveRoadDTO;
import com.spring.finproj.service.handler.CrawlingRoad;
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

	@Override
	public void insertDriveRoad() throws IOException {
		// TODO Auto-generated method stub
		List<DriveRoadDTO> list = new CrawlingRoad().RoadInfo();
		
		for(DriveRoadDTO d : list) {
			int re = driveDAO.insertRoadInfo(d);
			if(re>0) {
				System.out.println(d.getName()+" 등록");
			}
		}
	}

	@Override
	public void insertDriveRoadXY() {
		// TODO Auto-generated method stub
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		Queue<String> q = new LinkedList<String>();
		
//		q.add("128.3584939");
//		q.add("37.6097115");
//		
//		q.add("128.8928928");
//		q.add("37.1382206");
//		
//		q.add("126.8334288");
//		q.add("36.0767040");
//		
//		q.add("126.8993324");
//		q.add("35.5201185");
//		
//		q.add("126.2762279");
//		q.add("33.1682492");
//		
//		q.add("129.0453559");
//		q.add("35.5482015");
//		
//		q.add("127.0128648");
//		q.add("35.3266082");
//		
//		q.add("128.8680530");
//		q.add("37.7039505");
//		
//		q.add("127.9593931");
//		q.add("35.4717464");
//		
//		q.add("126.8964413");
//		q.add("35.5279797");
//		
//		q.add("126.8786455");
//		q.add("34.1657416");
//		
//		q.add("128.7575479");
//		q.add("37.6918836");
//		
//		q.add("126.7382116");
//		q.add("37.4021678");
//		
//		q.add("127.1536591");
//		q.add("35.8182114");
//		
//		q.add("126.9462997");
//		q.add("37.9391560");
//		
//		q.add("129.2734945");
//		q.add("36.9405249");
//		
//		q.add("127.1508285");
//		q.add("35.8152635");
//		
//		q.add("128.4634110");
//		q.add("37.0280047");
//		
//		q.add("126.8246768");
//		q.add("35.8709657");
//		
//		q.add("127.3384526");
//		q.add("36.2214416");
//		
//		q.add("129.2433725");
//		q.add("35.7983263");
		
		q.add("126.9066871");
		q.add("35.1855925");
		q.add("128.2317721");
		q.add("34.6394080");
		q.add("127.0084797");
		q.add("35.1887948");
		q.add("126.8921102");
		q.add("37.6278226");
		q.add("127.8528069");
		q.add("36.7348966");
		q.add("126.8455944");
		q.add("36.4056203");
		q.add("128.9577379");
		q.add("35.1807499");
		q.add("128.5528357");
		q.add("36.5401023");
		q.add("128.5992722");
		q.add("37.7240250");
		q.add("127.5185323");
		q.add("34.8665984");
		q.add("126.9471219");
		q.add("35.9398097");
		q.add("128.4976057");
		q.add("38.2071122");
		q.add("126.6427996");
		q.add("37.3888150");
		q.add("129.0661867");
		q.add("35.4881215");
		q.add("127.1531121");
		q.add("35.8115208");
		q.add("126.7431327");
		q.add("37.4438755");
		q.add("127.0462330");
		q.add("37.5240673");
		q.add("129.1240958");
		q.add("35.1008064");
		q.add("128.1071912");
		q.add("36.7289141");
		q.add("125.4286229");
		q.add("34.6832050");
		q.add("129.0468807");
		q.add("37.6484028");
		q.add("127.4443727");
		q.add("36.6337208");
		q.add("127.3346174");
		q.add("35.2669127");
		q.add("127.7947931");
		q.add("35.9957844");
		q.add("126.3430256");
		q.add("34.4918361");
		q.add("130.9049011");
		q.add("37.4902827");
		q.add("126.9725736");
		q.add("37.5657746");
		q.add("126.9850620");
		q.add("37.5820266");
		
		for(int i=23;i<=50;i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("wp_no", i);
			map.put("wpX", q.poll());
			map.put("wpY", q.poll());
			driveDAO.insertRoadXY(map);
		}
		
		
		
	}
}
