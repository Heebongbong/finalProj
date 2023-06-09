package com.spring.finproj.interceptor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.spring.finproj.model.alarm.AlarmCountVO;
import com.spring.finproj.model.alarm.AlarmDTO;
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
				
				if((Long.parseLong(se_dto.getExpiresTime())) < nowTime) { // 토큰 만료 - 갱신 필요
					String type = user.getType();
					
					if(type.equals("K")) {
						se_dto = kakaoTokenRefresh(se_dto);
						
						if(!user.isProfile_type()) {
							String profile = refreshProfile(se_dto.getSessionID(), type);
							user.setProfile(profile);
							userDAO.updateUserProfile(user);
						}
						
						UserDTO dto = userDAO.getUserContent(se_dto.getUser_no());
						
						dto.setProfile(user.getProfile()); //프로필 적용
						
						//쿠키 등록
						Cookie re_a_t = new Cookie("AccessToken", se_dto.getSessionID());
						re_a_t.setMaxAge(60*60*24*7);
						re_a_t.setPath("/");
						response.addCookie(re_a_t);
						
						session.setAttribute("LoginUser", dto);
						session.setMaxInactiveInterval(60*60*6);
					}else if(type.equals("N")) {
						se_dto = naverTokenRefresh(se_dto);
						
						if(!user.isProfile_type()) {
							String profile = refreshProfile(se_dto.getSessionID(), type);
							user.setProfile(profile);
							userDAO.updateUserProfile(user);
						}
						
						UserDTO dto = userDAO.getUserContent(se_dto.getUser_no());
						
						dto.setProfile(user.getProfile());

						//쿠키 등록
						Cookie re_a_t = new Cookie("AccessToken", se_dto.getSessionID());
						re_a_t.setMaxAge(60*60*24*7);
						re_a_t.setPath("/");
						response.addCookie(re_a_t);
						
						session.setAttribute("LoginUser", dto);
						session.setMaxInactiveInterval(60*60);
					}else if(type.equals("G")) {
						se_dto = googleTokenRefresh(se_dto);
						
						if(!user.isProfile_type()) {
							String profile = refreshProfile(se_dto.getSessionID(), type);
							user.setProfile(profile);
							userDAO.updateUserProfile(user);
						}
						
						UserDTO dto = userDAO.getUserContent(se_dto.getUser_no());
						
						dto.setProfile(user.getProfile());

						//쿠키 등록
						Cookie re_a_t = new Cookie("AccessToken", se_dto.getSessionID());
						re_a_t.setMaxAge(60*60*24*7);
						re_a_t.setPath("/");
						response.addCookie(re_a_t);
						
						session.setAttribute("LoginUser", dto);
						session.setMaxInactiveInterval(60*60);
						
					}else { //사이트 토큰 만료 갱신
						nowTime += (60*60*6);
						se_dto.setExpiresTime(nowTime.toString());
						userDAO.updateUserSession(se_dto);

						//쿠키 등록
						Cookie re_a_t = new Cookie("AccessToken", se_dto.getSessionID());
						re_a_t.setMaxAge(60*60*24*7);
						re_a_t.setPath("/");
						response.addCookie(re_a_t);
						
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

	private String refreshProfile(String sessionID, String type) throws IOException {
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
        
        if(type.equals("K")) {
            conn.setRequestProperty("Authorization", "Bearer "+sessionID);
		}else if(type.equals("N")) {
	        conn.setRequestProperty("Authorization", "Bearer "+sessionID);
		}else {
			return null;
		}
        
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
        System.out.println(sb.toString());
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
	
	private UserSessionDTO googleTokenRefresh(UserSessionDTO se_dto) throws Exception {
		
		String str = "grant_type=refresh_token"
				+ "&client_id=763924312013-ppith6f1s7furfp1jvagis96rboh584f.apps.googleusercontent.com"
				+ "&client_secret=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"
				+ "&refresh_token="+se_dto.getRefreshToken();
		
		
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
		
		Long exp = (Long)(System.currentTimeMillis()/1000)+(60*60);
		JSONObject jo = new JSONObject(sb.toString());
		String a_t = jo.getString("access_token");
		
		se_dto.setSessionID(a_t);
		se_dto.setExpiresTime(exp.toString());
		userDAO.updateUserSession(se_dto);
		return se_dto;
	}

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
			
			request.setAttribute("ChatRoomList", list);
			
			if(user.getUser_no()!=1) {
				ChatDTO a_dto = new ChatDTO();
				a_dto.setUser_no1(user.getUser_no());
				a_dto.setUser_no2(1);
				
				ChatDTO c = chatDAO.getChatRoomContent(a_dto);
				
				if(c==null) {
					int re = chatDAO.insertChatRoomCont(a_dto);
					if(re>0) {
						a_dto.setSend_user(user.getUser_no());
						a_dto.setChat_cont("1:1 문의를 시작합니다.");
						ChatDTO room_cont = chatDAO.getChatRoomContent(a_dto);
						a_dto.setChat_room_no(room_cont.getChat_room_no());
						chatDAO.insertChatCont(a_dto);
						
						request.setAttribute("Admin_chat_room", a_dto);
					}
				}else {
					request.setAttribute("Admin_chat_room", c);
				}
			}
			
			
			
			Map<String, Object> al_list = new HashMap<String, Object>();
			
			List<AlarmDTO> a_list = chatDAO.getAlarmList(user.getUser_no());
			
			String[] db = {"board_like", "ment_like", "ment_ins", "chat_on"};
			
			int total = 0;
			
			for(int i=0;i<db.length;i++) {
				AlarmCountVO v = new AlarmCountVO();
				al_list.put(db[i], v);
			}
			
			for(AlarmDTO a : a_list) {
				switch(a.getField()) {
					case 1 : 
						if(a.isCheck()) {
							((AlarmCountVO)al_list.get(db[0])).setCheckCount();
						}
						((AlarmCountVO)al_list.get(db[0])).setTotalCount();
						total++;
						break;
					case 2 : 
						if(a.isCheck()) {
							((AlarmCountVO)al_list.get(db[1])).setCheckCount();
						}
						((AlarmCountVO)al_list.get(db[1])).setTotalCount();
						total++;
						break;
					case 3 : 
						if(a.isCheck()) {
							((AlarmCountVO)al_list.get(db[2])).setCheckCount();
						}
						((AlarmCountVO)al_list.get(db[2])).setTotalCount();
						total++;
						break;
					case 4 : 
						if(a.isCheck()) {
							((AlarmCountVO)al_list.get(db[3])).setCheckCount();
						}
						((AlarmCountVO)al_list.get(db[3])).setTotalCount();
						total++;
						break;
					default :
						break;
				}
			}
			
			al_list.put("new_check", total);
			
			request.setAttribute("AlarmList", al_list);
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}