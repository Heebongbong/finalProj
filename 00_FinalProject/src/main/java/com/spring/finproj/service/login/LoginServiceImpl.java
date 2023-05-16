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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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
	public void loginSite(String id, String pwd, HttpSession session, HttpServletResponse response) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void loginGoogle(String a_token, String credential, HttpSession session, HttpServletResponse response) throws Exception {
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
			//boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
			String name = (String) payload.get("name");
			String pictureUrl = (String) payload.get("picture");
			//String locale = (String) payload.get("locale");
			//String familyName = (String) payload.get("family_name");
			//String givenName = (String) payload.get("given_name");
			UserDTO user = new UserDTO();
			user.setEmail(email);
			user.setPwd(userId);
			user.setNickname(name);
			user.setProfile(pictureUrl);
			user.setType("G");
			
			Cookie a_t = new Cookie("AccessToken", a_token);
			a_t.setMaxAge(60*60*24*7);
			a_t.setPath("/");
			response.addCookie(a_t);
			
			session.setAttribute("LoginUser", user);
			session.setMaxInactiveInterval(60*60*6);
		
		} else {
			System.out.println("Invalid ID token.");
		}
	}

	@Override
	public void loginNaver(String code, String state, HttpSession session, HttpServletResponse response) throws Exception {

		StringBuilder urlBuilder = new StringBuilder("https://nid.naver.com/oauth2.0/token");
		urlBuilder.append("?" + URLEncoder.encode("grant_type","UTF-8") + "=" + URLEncoder.encode("authorization_code", "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("client_id","UTF-8") + "=" + URLEncoder.encode("2fzdhIRlmXgPi9uo_5Xi", "UTF-8")); 
		urlBuilder.append("&" + URLEncoder.encode("client_secret","UTF-8") + "=" + URLEncoder.encode("nPmw0vdmyR", "UTF-8")); 
		urlBuilder.append("&" + URLEncoder.encode("code","UTF-8") + "=" + URLEncoder.encode(code, "UTF-8")); 
		urlBuilder.append("&" + URLEncoder.encode("state","UTF-8") + "=" + URLEncoder.encode(state, "UTF-8")); 
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
		String r_token = jo.getString("refresh_token");
		
		System.out.println("c-"+code);
		System.out.println("a-"+a_token);
		System.out.println("r-"+r_token);

		UserDTO dto = getNaverInfo(a_token);
		
		Cookie a_t = new Cookie("AccessToken", a_token);
		a_t.setMaxAge(60*60*24*7);
		a_t.setPath("/");
		response.addCookie(a_t);
		
		session.setAttribute("LoginUser", dto);
		session.setMaxInactiveInterval(60*60*6);
	}

	private UserDTO getNaverInfo(String a_token) throws Exception {
		UserDTO dto = new UserDTO();
		
		String curl = "https://openapi.naver.com/v1/nid/me";
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
        JSONObject jo2 = jo.getJSONObject("response");
        
        dto.setEmail(jo2.getString("email"));
        dto.setPwd(jo2.getString("id"));
		dto.setNickname(jo2.getString("nickname"));
		dto.setProfile(jo2.getString("profile_image"));
        dto.setType("N");
        
		return dto;
	}

	@Override
	public void loginKakao(String code, HttpSession session, HttpServletResponse response) throws Exception {
		
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
        String r_token = jo.getString("refresh_token");
        StringTokenizer st = new StringTokenizer(jo.getString("scope"));
        
        List<String> scope = new ArrayList<String>();
        while(st.hasMoreTokens()) {
        	scope.add(st.nextToken());
        }
		        
        System.out.println("c-"+code);
        System.out.println("a-"+a_token);
        System.out.println("r-"+r_token);

        UserDTO dto = getKakaoInfo(a_token, scope);
        
        Cookie a_t = new Cookie("AccessToken", a_token);
        a_t.setMaxAge(60*60*24*7);
        a_t.setPath("/");
        response.addCookie(a_t);
        
        session.setAttribute("LoginUser", dto);
		session.setMaxInactiveInterval(60*60*6);
        
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
        Integer id = jo.getInt("id");
		UserDTO dto = new UserDTO();
		dto.setEmail(joA.getString("email"));
		dto.setPwd(id.toString());
		dto.setNickname(joP.getString("nickname"));
		dto.setProfile(joP.getString("thumbnail_image_url"));
		dto.setType("K");
		
		return dto;
	}

	@Override
	public void logoutUser(HttpSession session, HttpServletResponse response, String sessionID) throws Exception {
		
		UserDTO dto = (UserDTO)session.getAttribute("LoginUser");
		switch (dto.getType().charAt(0)) {
		case 'K':
			logoutKakao(sessionID);
			break;
		case 'G':
			break;
		case 'N':
			break;
		}
		
		Cookie a_t = new Cookie("AccessToken", null);
        a_t.setMaxAge(0);
        a_t.setPath("/");
        response.addCookie(a_t);
        Cookie j_t = new Cookie("JSESSIONID", null);
        a_t.setMaxAge(0);
        a_t.setPath("/");
        response.addCookie(j_t);
        
        //세션데이터테이블 정보 삭제 필요
        
		session.invalidate();
	}

	private void logoutKakao(String sessionID) throws Exception {
		String curl = "https://kapi.kakao.com/v1/user/logout";
        URL url = new URL(curl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Authorization", "Bearer "+sessionID);
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