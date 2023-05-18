package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import com.spring.finproj.service.handler.MakeCode;
import com.spring.finproj.service.handler.Signature;

public class MainTest {

	public static void main(String[] args) {
		
			int result = 0;
			String code = new MakeCode().makeSMSCode();
			
			Signature sign = new Signature();
			
			String signUrl = "/sms/v2/services/ncp:sms:kr:307378897490:campion/messages";
			String curl = "https://sens.apigw.ntruss.com"+signUrl;
			
			//메세지 내용
			String str = "{" + 
					"    \"type\":\"SMS\"," + 
					"    \"contentType\":\"COMM\"," + 
					"    \"countryCode\":\"82\"," + 
					"    \"from\":\"01044889509\"," + 
					"    \"content\":\"SMS인증작업\"," + 
					"    \"messages\":[" + 
					"        {" + 
					"            \"to\":\""+"01094345821"+"\"," + 
					"            \"content\":\"입력하실 인증번호는 ["+code+"] 입니다.\"" + 
					"        }" + 
					"    ]" + 
					"}";
			JSONObject jo = new JSONObject(str);
			
			// 1. 장치에 요청할 URI를 입력한다.
	        URL url = new URL(curl);
	        HttpURLConnection con = (HttpURLConnection)url.openConnection();

	        con.setRequestProperty("Content-Type", "application/json;charset=utf-8");

	        // 2. Method 타입을 정의하고 API를 전송한다.
	        con.setRequestMethod("POST");
	        con.setRequestProperty("x-ncp-apigw-timestamp", sign.getNowTime());
	        con.setRequestProperty("x-ncp-iam-access-key", "So6WkkHqyaafmNSxc05s");
	        con.setRequestProperty("x-ncp-apigw-signature-v2", sign.makePostSignature(signUrl));
	        con.setDoOutput(true);
	        
	        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
	        bw.write(jo.toString());
	        bw.flush();
	        bw.close();
	        
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
	        
	        JSONObject reJo = new JSONObject(response.toString());
			
	        System.out.println(reJo);
			
		}

	}

