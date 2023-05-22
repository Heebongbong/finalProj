package com.spring.finproj.interceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.spring.finproj.model.chat.ChatDAO;
import com.spring.finproj.model.chat.ChatDTO;
import com.spring.finproj.model.user.UserDAO;
import com.spring.finproj.model.user.UserDTO;
import com.spring.finproj.model.user.UserSessionDTO;

public class Interceptor implements HandlerInterceptor{
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private ChatDAO chatDAO;
	
	// 로그인기록 있으나 세션 만료되어있는 상태, 자동 로그인 기능
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		// TODO Auto-generated method stub
		Long nowTime = (System.currentTimeMillis()/1000);
		HttpSession session = request.getSession();
		String sessionID = null;
		String a_t = null;
		String j_s = null;
		
		Cookie[] cs = request.getCookies();
		if(cs!=null) {
			for(Cookie c : cs) {
				if(c.getName().equals("AccessToken")) {
					a_t = c.getValue();
				}else if(c.getName().equals("JSESSIONID")){
					j_s = c.getValue();
				}
			}
		}
		
		if(a_t==null) {
			sessionID = j_s;
		}else {
			sessionID = a_t;
		}
		
		if(session.getAttribute("LoginUser")==null) { //로그아웃 상태
			UserSessionDTO se_dto = userDAO.getUserSession(sessionID);
			
			if(se_dto!=null) { //session 데이터 존재
				UserDTO user = userDAO.getUserContent(se_dto.getUser_no());
				
				if(!user.isProfile_type()) {
					String profile = refreshProfile(se_dto.getSessionID(), user.getType());
					user.setProfile(profile);
				}
				
				if((Long.parseLong(se_dto.getExpiresTime())) < nowTime) { // 토큰 만료 - 갱신 필요
					String type = user.getType();
					
					if(type.equals("K")) {
						se_dto = kakaoTokenRefresh(se_dto);
						UserDTO dto = userDAO.getUserContent(se_dto.getUser_no());
						
						dto.setProfile(user.getProfile());
						
						session.setAttribute("LoginUser", dto);
						session.setMaxInactiveInterval(60*60*6);
					}else if(type.equals("N")) {
						se_dto = naverTokenRefresh(se_dto);
						UserDTO dto = userDAO.getUserContent(se_dto.getUser_no());
						
						dto.setProfile(user.getProfile());
						
						session.setAttribute("LoginUser", dto);
						session.setMaxInactiveInterval(60*60);
					}else if(type.equals("G")) {
						//user = googleTokenRefresh(se_dto);
						
						//구글 세션 갱신법 확인 필요
						response.getWriter().println("<script>"
								+ "alert('구글 세션 만료');"
								+ "history.back();';"
								+ "</script>");
						return false;
						
					}else { //사이트 토큰 만료 갱신
						nowTime += (60*60*6);
						se_dto.setExpiresTime(nowTime.toString());
						userDAO.updateUserSession(se_dto);
						
						session.setAttribute("LoginUser", user);
						session.setMaxInactiveInterval(60*60*6);
					}
				}else { //토큰 유효 - 자동 로그인 처리
					session.setAttribute("LoginUser", user);
					session.setMaxInactiveInterval((int)(Long.parseLong(se_dto.getExpiresTime()) - nowTime));
				}//토큰 정상
			}//비회원or로그인 기록 없음
		}//로그인 상태
		
		return true;
	}

	private String refreshProfile( String sessionID, String type) throws IOException {
		// TODO Auto-generated method stub
		String profile = null;
		String curl = null;
		
		if(type.equals("K")) {
			curl = "https://kapi.kakao.com/v2/user/me";
		}else if(type.equals("N")) {
			curl = "https://openapi.naver.com/v1/nid/me";
		}else {
			return null;
		}
		
        URL url = new URL(curl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
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
        JSONObject jo = new JSONObject(sb.toString());
        
        if(type.equals("N")) {
        	JSONObject jo2 = jo.getJSONObject("response");
            if(jo2.has("profile_image")) {
            	profile = jo2.getString("profile_image");
    		}
        }else if(type.equals("K")) {
        	JSONObject joA = jo.getJSONObject("kakao_account");
            if(joA.has("profile")) {
            	JSONObject joP = joA.getJSONObject("profile");
            	if(joP.has("thumbnail_image_url")) {
            		profile = joP.getString("thumbnail_image_url");
        		}
            }
        }
        return profile;
	}

//	private UserDTO googleTokenRefresh(UserSessionDTO se_dto) throws IOException {
//		//리프레쉬 토큰 있어야함. 다른 가능한 방법 찾아봐야함.
//		try {
//			TokenResponse response = new GoogleRefreshTokenRequest(
//			new NetHttpTransport(), new GsonFactory(),
//			"tGzv3JOkF0XG5Qx2TlKWIA", "s6BhdRkqt3",
//			"7Fjfp0ZBr1KtDRbnfVdmIw").execute();
//			System.out.println("Access token: " + response.getAccessToken());
//		} catch (TokenResponseException e) {
//			if (e.getDetails() != null) {
//				System.err.println("Error: " + e.getDetails().getError());
//				if (e.getDetails().getErrorDescription() != null) {
//					System.err.println(e.getDetails().getErrorDescription());
//				}
//				if (e.getDetails().getErrorUri() != null) {
//					System.err.println(e.getDetails().getErrorUri());
//				}
//			} else {
//				System.err.println(e.getMessage());
//			}
//		}
//		
//		return userDAO.getUserContent(se_dto.getUser_no());
//	}

	private UserSessionDTO kakaoTokenRefresh(UserSessionDTO se_dto) throws Exception {
		
		StringBuilder urlBuilder = new StringBuilder("https://kauth.kakao.com/oauth/token");
		urlBuilder.append("?" + URLEncoder.encode("grant_type","UTF-8") + "=" + URLEncoder.encode("refresh_token", "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("client_id","UTF-8") + "=" + URLEncoder.encode("98777fbdb2c9b1364e02210caf720b42", "UTF-8")); 
		urlBuilder.append("&" + URLEncoder.encode("refresh_token","UTF-8") + "=" + URLEncoder.encode(se_dto.getRefreshToken(), "UTF-8")); 
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
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
		
		Long exp = (Long)(System.currentTimeMillis()/1000)+(60*60*6);
		
		JSONObject jo = new JSONObject(sb.toString());
		String a_t = jo.getString("access_token");
		
		se_dto.setSessionID(a_t);
		se_dto.setExpiresTime(exp.toString());
		userDAO.updateUserSession(se_dto);
		return se_dto;
	}

	private UserSessionDTO naverTokenRefresh(UserSessionDTO se_dto) throws Exception {
		
		StringBuilder urlBuilder = new StringBuilder("https://nid.naver.com/oauth2.0/token");
		urlBuilder.append("?" + URLEncoder.encode("grant_type","UTF-8") + "=" + URLEncoder.encode("refresh_token", "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("client_id","UTF-8") + "=" + URLEncoder.encode("2fzdhIRlmXgPi9uo_5Xi", "UTF-8")); 
		urlBuilder.append("&" + URLEncoder.encode("client_secret","UTF-8") + "=" + URLEncoder.encode("nPmw0vdmyR", "UTF-8")); 
		urlBuilder.append("&" + URLEncoder.encode("refresh_token","UTF-8") + "=" + URLEncoder.encode(se_dto.getRefreshToken(), "UTF-8")); 
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
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
		
		Long exp = (Long)(System.currentTimeMillis()/1000)+(60*60);
		
		JSONObject jo = new JSONObject(sb.toString());
		String a_t = jo.getString("access_token");
		
		se_dto.setSessionID(a_t);
		se_dto.setExpiresTime(exp.toString());
		
		userDAO.updateUserSession(se_dto);
		return se_dto;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) {
		HttpSession s = request.getSession();
		if(s.getAttribute("LoginUser")!=null) {
			UserDTO user = (UserDTO) s.getAttribute("LoginUser");
			List<ChatDTO> list = chatDAO.getChatRoomList(user.getUser_no());
			
			for(ChatDTO d : list) {
				if(d.getUser_no1()!=user.getUser_no()) {
					UserDTO ex = userDAO.getUserContent(d.getUser_no1());
					d.setNickname(ex.getNickname());
				}else {
					UserDTO ex = userDAO.getUserContent(d.getUser_no2());
					d.setNickname(ex.getNickname());
				}
			}
			System.out.println(list);
			request.setAttribute("ChatRoomList", list);
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}