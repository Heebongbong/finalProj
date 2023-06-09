package com.spring.finproj.service.login;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.finproj.model.user.UserDAO;
import com.spring.finproj.model.user.UserDTO;
import com.spring.finproj.model.user.UserSessionDTO;
import com.spring.finproj.service.handler.MakeNickName;
import com.spring.finproj.service.user.UserServiceImpl;

@Service
public class LoginServiceImpl implements LoginService{
	@Autowired
	private UserDAO userDAO;

	@Override
	public void loginSite(String email, String pwd, HttpSession session, HttpServletResponse response, String jSessionId) throws Exception {
		Map<String, Object> idlist = new HashMap<String, Object>();
		idlist.put("email", email);
		idlist.put("type", "S");
		
		System.out.println("idlist"+idlist);
		UserDTO dto = userDAO.getUserContentId(idlist);
		
		if(dto!=null) { //정보 확인
			if(dto.getPwd().equals(pwd)) {//비번일치
				UserSessionDTO s_dto = userDAO.getUserSession(dto.getUser_no());
				if(s_dto!=null) {//현재 쿠키와 일치하지 않는 기존 세션 존재
					userDAO.deleteUserSessionContent(s_dto.getUser_no());
				}
				//세션 DB 저장
				Long ext = (System.currentTimeMillis()/1000)+(60*60*6);
				UserSessionDTO sessionDto = new UserSessionDTO();
				sessionDto.setUser_no(dto.getUser_no());
				sessionDto.setSessionID(jSessionId);
				sessionDto.setRefreshToken(jSessionId);
				sessionDto.setExpiresTime(ext.toString());
				
				userDAO.insertUserSession(sessionDto);

				//쿠키 등록
				Cookie re_a_t = new Cookie("AccessToken", jSessionId);
				re_a_t.setMaxAge(60*60*24*7);
				re_a_t.setPath("/");
				response.addCookie(re_a_t);
				
				//세션 등록 (쿠키는 기존 id사용)
				session.setAttribute("LoginUser", dto);
				session.setMaxInactiveInterval(60*60*6);
				
				response.sendRedirect("/finproj/index");
				
			}else {//불일치
				response.getWriter().println("<script>"
						+ "alert('비밀번호가 다릅니다.');"
						+ "history.back();"
						+ "</script>");
			}
		}else { //email 없음
			response.getWriter().println("<script>"
					+ "alert('회원정보가 없습니다.');"
					+ "history.back();"
					+ "</script>");
		}
	}
	
	@Override
	public void loginGoogle(String code, HttpSession session, HttpServletResponse response) throws Exception {

		String str = "code="+code
				+ "&client_id=763924312013-ppith6f1s7furfp1jvagis96rboh584f.apps.googleusercontent.com"
				+ "&client_secret=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"
				+ "&redirect_uri=http://localhost:8787/finproj/login/google"
				+ "&grant_type=authorization_code";
		
		StringBuilder urlBuilder = new StringBuilder("https://oauth2.googleapis.com/token");
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
		conn.setDoOutput(true);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
        bw.write(str);
        bw.flush();
        bw.close();
		
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
		
		UserDTO user = getGoogleInfo(a_token, response);
		
		//email, type을 통한 email DB 확인
		Map<String, Object> idlist = new HashMap<String, Object>();
		idlist.put("email", user.getEmail());
		idlist.put("type", "G");
		
		UserDTO dto = userDAO.getUserContentId(idlist);
		System.out.println(dto);
		if(dto==null) { //비회원
			int re1 = userDAO.insertUserSNSContent(user);
			if(re1>0) {
				userDAO.insertUserSNSProfileContent(user);
				user = userDAO.getUserContentId(idlist);
			}
		}else { // 기존 회원
			//프로필 업데이트
			if(!dto.isProfile_type()) {
  				dto.setProfile(user.getProfile());
				userDAO.updateUserProfile(dto);
  			}
			UserSessionDTO s_dto = userDAO.getUserSession(dto.getUser_no());
			if(s_dto!=null) {//현재 쿠키와 일치하지 않는 기존 세션 삭제
				userDAO.deleteUserSessionContent(s_dto.getUser_no());
			}
			user = dto;
		}
		System.out.println(user);
		
		//세션 DB 저장
		Long ext = (System.currentTimeMillis()/1000)+(60*60);
		UserSessionDTO sessionDto = new UserSessionDTO();
		sessionDto.setUser_no(user.getUser_no());
		sessionDto.setSessionID(a_token);
		sessionDto.setRefreshToken(r_token);
		sessionDto.setExpiresTime(ext.toString());
		
		userDAO.insertUserSession(sessionDto);
		
		//쿠키 세션 등록
		Cookie a_t = new Cookie("AccessToken", a_token);
		a_t.setMaxAge(60*60*24*7);
		a_t.setPath("/");
		response.addCookie(a_t);
		
		session.setAttribute("LoginUser", user);
		session.setMaxInactiveInterval(60*60);
	}

	private UserDTO getGoogleInfo(String access_token, HttpServletResponse response) throws Exception {
		UserDTO dto = new UserDTO();
		
		StringBuilder urlBuilder = new StringBuilder("https://oauth2.googleapis.com/tokeninfo");
        urlBuilder.append("?" + URLEncoder.encode("access_token","UTF-8") + "=" + URLEncoder.encode(access_token, "UTF-8"));
        
		URL url = new URL(urlBuilder.toString());
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");
	    conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
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
	
	    
	    dto.setEmail(jo.getString("email"));
	    dto.setPwd(jo.getString("sub"));
		dto.setNickname(makeNickName());
		dto.setProfile("/finproj/resources/images/profile/default/default_profile.png");
		dto.setProfile_type(true);
	    dto.setType("G");
	    
		return dto;
	}

	@Override
	public void loginNaver(String code, String state, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {

		StringBuffer ctxUrl = request.getRequestURL();
		String reUrl = ctxUrl.substring(0, ctxUrl.indexOf("finproj"));
		
		StringBuilder urlBuilder = new StringBuilder("https://nid.naver.com/oauth2.0/token");
		urlBuilder.append("?" + URLEncoder.encode("grant_type","UTF-8") + "=" + URLEncoder.encode("authorization_code", "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("client_id","UTF-8") + "=" + URLEncoder.encode("2fzdhIRlmXgPi9uo_5Xi", "UTF-8")); 
		urlBuilder.append("&" + URLEncoder.encode("client_secret","UTF-8") + "=" + URLEncoder.encode("nPmw0vdmyR", "UTF-8")); 
		urlBuilder.append("&" + URLEncoder.encode("redirect_uri","UTF-8") + "=" + URLEncoder.encode(reUrl+"finproj/login/naver", "UTF-8")); 
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
		
		UserDTO user = getNaverInfo(a_token, response);
		
		//email, type을 통한 email DB 확인
		Map<String, Object> idlist = new HashMap<String, Object>();
		idlist.put("email", user.getEmail());
		idlist.put("type", "N");
		
		UserDTO dto = userDAO.getUserContentId(idlist);
		
		if(dto==null) { //비회원
			int re1 = userDAO.insertUserSNSContent(user);
			if(re1>0) {
				userDAO.insertUserSNSProfileContent(user);
				user = userDAO.getUserContentId(idlist);
			}
		}else { // 기존 회원
			//프로필 업데이트
			if(!dto.isProfile_type()) {
  				dto.setProfile(user.getProfile());
				userDAO.updateUserProfile(dto);
  			}
			UserSessionDTO s_dto = userDAO.getUserSession(dto.getUser_no());
			if(s_dto!=null) {//현재 쿠키와 일치하지 않는 기존 세션 삭제
				userDAO.deleteUserSessionContent(s_dto.getUser_no());
			}
			user = dto;
		}
		
		//세션 DB 저장
		Long ext = (System.currentTimeMillis()/1000)+(60*60);
		UserSessionDTO sessionDto = new UserSessionDTO();
		sessionDto.setUser_no(user.getUser_no());
		sessionDto.setSessionID(a_token);
		sessionDto.setRefreshToken(r_token);
		sessionDto.setExpiresTime(ext.toString());
		
		userDAO.insertUserSession(sessionDto);
		
		//쿠키 세션 등록
		Cookie a_t = new Cookie("AccessToken", a_token);
		a_t.setMaxAge(60*60*24*7);
		a_t.setPath("/");
		response.addCookie(a_t);
		
		session.setAttribute("LoginUser", user);
		session.setMaxInactiveInterval(60*60);
	}

	private UserDTO getNaverInfo(String a_token, HttpServletResponse response ) throws Exception {
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
        
        if(jo2.has("email")) {
        	dto.setEmail(jo2.getString("email"));
        }else {
        	new UserServiceImpl().deleteNaverUser(a_token);
        	
        	response.getWriter().println("<script>"
					+ "alert('이메일 제공 약관에 동의해야합니다.');"
					+ "history.back();"
					+ "</script>");
        }
        
        dto.setPwd(jo2.getString("id"));
		dto.setNickname(makeNickName());
		
		if(jo2.has("profile_image")) {
			dto.setProfile(jo2.getString("profile_image"));
			dto.setProfile_type(false);
		}else {
			dto.setProfile("/finproj/resources/images/profile/default/default_profile.png");
			dto.setProfile_type(true);
		}
		
        dto.setType("N");
        
		return dto;
	}

	@Override
	public void loginKakao(String code, HttpSession session, HttpServletResponse response, HttpServletRequest request) throws Exception {
		
		StringBuffer ctxUrl = request.getRequestURL();
		String reUrl = ctxUrl.substring(0, ctxUrl.indexOf("finproj"));
		
		StringBuilder urlBuilder = new StringBuilder("https://kauth.kakao.com/oauth/token");
        urlBuilder.append("?" + URLEncoder.encode("grant_type","UTF-8") + "=" + URLEncoder.encode("authorization_code", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("client_id","UTF-8") + "=" + URLEncoder.encode("98777fbdb2c9b1364e02210caf720b42", "UTF-8")); 
        urlBuilder.append("&" + URLEncoder.encode("redirect_uri","UTF-8") + "=" + URLEncoder.encode(reUrl+"finproj/login/kakao", "UTF-8")); 
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
        
        UserDTO user = getKakaoInfo(a_token, response);
        
        //email, type을 통한 email DB 확인
  		Map<String, Object> idlist = new HashMap<String, Object>();
  		idlist.put("email", user.getEmail());
  		idlist.put("type", "K");
  		
  		UserDTO dto = userDAO.getUserContentId(idlist);
  		
  		if(dto==null) { //비회원
  			int re1 = userDAO.insertUserSNSContent(user);
  			if(re1>0) {
  				userDAO.insertUserSNSProfileContent(user);
  				user = userDAO.getUserContentId(idlist);
  			}
  		}else { // 기존 회원
  			//프로필 업데이트
  			if(!dto.isProfile_type()) {
  				dto.setProfile(user.getProfile());
				userDAO.updateUserProfile(dto);
  			}
			UserSessionDTO s_dto = userDAO.getUserSession(dto.getUser_no());
			if(s_dto!=null) {//현재 쿠키와 일치하지 않는 기존 세션 삭제
				userDAO.deleteUserSessionContent(s_dto.getUser_no());
			}
  			user = dto;
  		}
  		
  		//세션 DB 저장
		Long ext = (System.currentTimeMillis()/1000)+(60*60*6);
		UserSessionDTO sessionDto = new UserSessionDTO();
		sessionDto.setUser_no(user.getUser_no());
		sessionDto.setSessionID(a_token);
		sessionDto.setRefreshToken(r_token);
		sessionDto.setExpiresTime(ext.toString());
		
		userDAO.insertUserSession(sessionDto);
		
		//쿠키 세션 등록
		Cookie a_t = new Cookie("AccessToken", a_token);
		a_t.setMaxAge(60*60*24*7);
		a_t.setPath("/");
		response.addCookie(a_t);

        session.setAttribute("LoginUser", user);
		session.setMaxInactiveInterval(60*60*6);
	}
	
	private UserDTO getKakaoInfo(String a_token, HttpServletResponse response) throws Exception {
		
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
        
        Integer id = jo.getInt("id");
		UserDTO dto = new UserDTO();
		
		if(joA.has("email")) {
			dto.setEmail(joA.getString("email"));
		}else { //email 미제공 - 페이지로 가야함
			
			new UserServiceImpl().deleteKakaorUser(a_token);
			
			response.getWriter().println("<script>"
					+ "alert('이메일 제공 약관에 동의해야합니다.');"
					+ "history.back();"
					+ "</script>");
		}
		
		dto.setPwd(id.toString());
		dto.setNickname(makeNickName());
		
		if(joA.has("profile")) {
        	JSONObject joP = joA.getJSONObject("profile");
        	if(joP.has("thumbnail_image_url")) {
    			dto.setProfile(joP.getString("thumbnail_image_url"));
    			dto.setProfile_type(false);
    		}else {
    			dto.setProfile("/finproj/resources/images/profile/default/default_profile.png");
    			dto.setProfile_type(true);
    		}
        }else {
			dto.setProfile("/finproj/resources/images/profile/default/default_profile.png");
			dto.setProfile_type(true);
		}
		
		dto.setType("K");
		
		return dto;
	}

	@Override
	public void logoutUser(HttpSession session, HttpServletResponse response, String sessionID) throws Exception {
		
		UserDTO dto = (UserDTO)session.getAttribute("LoginUser");
		
		if(dto.getType().equals("K")) {
			kakaoLogout(sessionID, dto.getPwd());
		}
		
        //세션데이터테이블 정보 삭제
		int re = userDAO.deleteUserSessionContent(dto.getUser_no());
		
		if(re>0) { //삭제 성공
			Cookie a_t = new Cookie("AccessToken", null);
	        a_t.setMaxAge(0);
	        a_t.setPath("/");
	        response.addCookie(a_t);
	        Cookie j_t = new Cookie("JSESSIONID", null);
	        a_t.setMaxAge(0);
	        a_t.setPath("/");
	        response.addCookie(j_t);
	        
			session.invalidate();
		}else {
			System.out.println("세션테이블 삭제 처리 오류");
		}
	}
	
	private String makeNickName() {
		String nickName = new MakeNickName().makeValue();
		if(userDAO.getNickCheck(nickName)!=null) {
			makeNickName();
		}
		return nickName;
	}
	
	private void kakaoLogout(String sessionID, String target_id) throws Exception {
		
		StringBuilder urlBuilder = new StringBuilder("https://kapi.kakao.com/v1/user/logout");
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Authorization", "Bearer "+sessionID);
        conn.setRequestProperty("target_id_type", "user_id");
        conn.setRequestProperty("target_id", target_id);
        System.out.println("delete code: " + conn.getResponseCode());
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