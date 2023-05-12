package com.spring.finproj.service.login;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.spring.finproj.model.user.UserDTO;

@Service
public class LoginServiceImpl implements LoginService{

	@Override
	public void loginGoogle(String token, String credential, HttpSession session) throws Exception {
		HttpTransport transport =  new NetHttpTransport();;
		JsonFactory jsonFactory = GsonFactory.getDefaultInstance();
		
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
				.setAudience(Collections.singletonList("763924312013-ppith6f1s7furfp1jvagis96rboh584f.apps.googleusercontent.com"))
				.build();
		GoogleIdToken idToken = verifier.verify(credential);
		if (idToken != null) {
		  Payload payload = idToken.getPayload();

		  String userId = payload.getSubject();

		  String email = payload.getEmail();
//		  boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
		  String name = (String) payload.get("name");
		  String pictureUrl = (String) payload.get("picture");
//		  String locale = (String) payload.get("locale");
//		  String familyName = (String) payload.get("family_name");
//		  String givenName = (String) payload.get("given_name");
		  
		  UserDTO user = new UserDTO();
		  user.setUser_email(email);
		  user.setUser_pwd(userId);
		  user.setUser_nickname(name);
		  user.setUser_profile(pictureUrl);
		  user.setUser_type("G");
		  user.setUser_token(token);
		  session.setAttribute("loginUser", user);
		} else {
		  System.out.println("Invalid ID token.");
		}
	}

	@Override
	public void loginNaver() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loginSite() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loginKakao(String code, HttpSession session) throws Exception {
		
		StringBuilder urlBuilder = new StringBuilder("https://kauth.kakao.com/oauth/token");
        urlBuilder.append("?" + URLEncoder.encode("grant_type","UTF-8") + "=" + URLEncoder.encode("authorization_code", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("client_id","UTF-8") + "=" + URLEncoder.encode("98777fbdb2c9b1364e02210caf720b42", "UTF-8")); 
        urlBuilder.append("&" + URLEncoder.encode("redirect_uri","UTF-8") + "=" + URLEncoder.encode("http://localhost:8787/finproj/login/kakao", "UTF-8")); 
        urlBuilder.append("&" + URLEncoder.encode("code","UTF-8") + "=" + URLEncoder.encode(code, "UTF-8")); 
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
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
        String a_token = jo.getString("access_token");
        StringTokenizer st = new StringTokenizer(jo.getString("scope"));
        List<String> scope = new ArrayList<String>();
        while(st.hasMoreTokens()) {
        	scope.add(st.nextToken());
        }
        
        UserDTO dto = getKakaoInfo(a_token, scope);
        dto.setUser_token(a_token);
        session.setAttribute("loginUser", dto);
	}
	
	private UserDTO getKakaoInfo(String a_token, List<String> scope) throws Exception {
		
		String curl = "https://kapi.kakao.com/v2/user/me";
        URL url = new URL(curl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        conn.setRequestProperty("Authorization", "Bearer "+a_token);
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
        JSONObject joA = jo.getJSONObject("kakao_account");
        JSONObject joP = joA.getJSONObject("profile");
        
		UserDTO dto = new UserDTO();
		dto.setUser_email(joA.getString("email"));
		Integer id = jo.getInt("id");
		dto.setUser_pwd(id.toString());
		dto.setUser_nickname(joP.getString("nickname"));
		dto.setUser_profile(joP.getString("thumbnail_image_url"));
		dto.setUser_type("K");
		
		return dto;
	}

	@Override
	public void logoutUser(HttpSession session) throws Exception {
		UserDTO dto = (UserDTO) session.getAttribute("loginUser");
		
		switch (dto.getUser_type().charAt(0)) {
		case 'K':
			logoutKakao(dto);
			break;
		case 'G':
			break;
		case 'N':
			
			break;
		}
		session.invalidate();
	}

	private void logoutKakao(UserDTO dto) throws Exception {
		String curl = "https://kapi.kakao.com/v1/user/logout";
        URL url = new URL(curl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Authorization", "Bearer "+dto.getUser_token());
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
	}
}