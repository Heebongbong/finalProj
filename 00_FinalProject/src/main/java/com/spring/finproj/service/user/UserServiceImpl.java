package com.spring.finproj.service.user;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.spring.finproj.model.camping.CampingDTO;
import com.spring.finproj.model.user.UserDAO;
import com.spring.finproj.model.user.UserDTO;
import com.spring.finproj.model.user.UserSessionDTO;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDao;

	@SuppressWarnings("all")
	@Override
	public int insertUserContent(UserDTO dto, HttpServletRequest request, HttpServletResponse response,
			HttpSession session, MultipartFile mfile) throws Exception {

		//sms인증 후 코드 삭제 처리
		if(session.getAttribute("code")!=null) {
			session.removeAttribute("code");
		}
		
		dto.setType("S");
		
		if(!mfile.isEmpty()) {
			String originalFileName = mfile.getOriginalFilename();
			
			// 절대경로 가져오기
			Properties prop = new Properties();
			FileInputStream fis = new FileInputStream(
					request.getRealPath("WEB-INF\\classes\\properties\\filepath.properties"));
			prop.load(new InputStreamReader(fis));
			fis.close();

			LocalDateTime nowDate = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddhhmmss");
			String today = nowDate.format(formatter);

			// type + 이메일 아이디
			String type = dto.getType();

			StringTokenizer st = new StringTokenizer(dto.getEmail(), "@", true);
			String email_id = st.nextToken();

			String userFolder = "\\profile\\" + type + "_" + email_id;
			String saveFolder = prop.getProperty(System.getenv("USERPROFILE").substring(3)) + userFolder;

			File folder1 = new File(saveFolder);
			File folder2 = new File(saveFolder + "\\" + today);
			if (!folder1.exists()) {
				folder1.mkdirs();
			}

			if (!folder2.exists()) {
				folder2.mkdir();
			}

			String saveFileName = UUID.randomUUID().toString()
					+ originalFileName.substring(originalFileName.lastIndexOf('.'));
			mfile.transferTo(new File(saveFolder + "\\" + today, saveFileName));
			dto.setProfile(
					"/finproj/resources/images/profile/" + type + "_" + email_id + "/" + today + "/" + saveFileName);
			dto.setProfile_type(false);

		} else {
			// 기본이미지 세팅
			dto.setProfile("/finproj/resources/images/profile/default/default_profile.png");
			dto.setProfile_type(true);
		}

		if (dto.getPhone() != null) {
			dto.setAuthen(false);
		} else {
			dto.setAuthen(true);
		}

		System.out.println("service dto ================>>>> " + dto);

		int check1 = userDao.insertUserContent(dto);
		int check2 = userDao.insertUserProfileContent(dto);
		int check = 0;

		if (check1 == 1 && check2 == 1) {
			check = 1;
		}
		return check;

	}

	@Override
	public int updateUserContent(UserDTO dto, HttpSession session, MultipartFile mfile, HttpServletRequest request)
			throws Exception {
		session.removeAttribute("code");

		UserDTO sdto = (UserDTO) session.getAttribute("LoginUser");

		sdto.isAuthen();
		dto.isProfile_type();

		if (dto.getPwd().equals("")) {
			dto.setPwd(sdto.getPwd());
			System.out.println("기존 비밀번호 세팅");
		}

		if (!dto.getPhone().equals("")) {
			dto.setAuthen(true);
		}

		// 프로필 값비교 & 저장
		

		if (!mfile.isEmpty()) {
			String originalFileName = mfile.getOriginalFilename();
			// 절대경로 가져오기
			Properties prop = new Properties();
			@SuppressWarnings("deprecation")
			FileInputStream fis = new FileInputStream(
					request.getRealPath("WEB-INF\\classes\\properties\\filepath.properties"));
			prop.load(new InputStreamReader(fis));
			fis.close();

			LocalDateTime nowDate = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddhhmmss");
			String today = nowDate.format(formatter);

			// type + 이메일 아이디
			String type = dto.getType();

			StringTokenizer st = new StringTokenizer(dto.getEmail(), "@", true);
			String email_id = st.nextToken();

			String userFolder = "\\profile\\" + type + "_" + email_id;
			String saveFolder = prop.getProperty(System.getenv("USERPROFILE").substring(3)) + userFolder;

			String saveFileName = UUID.randomUUID().toString()
					+ originalFileName.substring(originalFileName.lastIndexOf('.'));

			String profile_src = "/finproj/resources/images/profile/" + type + "_" + email_id + "/" + today + "/"
					+ saveFileName;

			if (sdto.getProfile() != profile_src) {
				dto.setProfile(profile_src);

				File folder1 = new File(saveFolder);
				File folder2 = new File(saveFolder + "\\" + today);
				if (!folder1.exists()) {
					folder1.mkdirs();
				}

				if (!folder2.exists()) {
					folder2.mkdir();
				}

				mfile.transferTo(new File(saveFolder + "\\" + today, saveFileName));
			} else {
				dto.setProfile(sdto.getProfile());
			}

		} else { // 기존 이미지 세팅
			if (dto.getProfile() == null) {
				System.out.println("프로필 수정 없어서 파일 정보 없음~");
				dto.setProfile(sdto.getProfile());
			}
		}

		return userDao.updateUserContent(dto);

	}

	@Override
	public int updatePwd(UserDTO dto) {

		return userDao.updateUserPwd(dto);
	}

	@Override
	public String getNickCheck(String nickName, HttpSession session) throws IOException {

		String check = "";
		UserDTO dto = (UserDTO) session.getAttribute("LoginUser");

		if (userDao.getNickCheck(nickName) == null || nickName.equals(dto.getNickname())) {
			check = "true";
		}
		return check;
	}

	@Override
	public String getPhoneCheck(String phone, String type) {
		Map<String, String> map = new HashMap<String, String>();

		map.put("phone", phone);
		map.put("type", type);

		String check = "";
		System.out.println(userDao.getPhoneCheck(map));
		if (userDao.getPhoneCheck(map) == null) {
			check = "true";
		}
		return check;
	}

	@Override
	public String getEmailCheck(String email) {
		String check = "";
		System.out.println("서비스 > " + email);
		System.out.println(userDao.getEmailCheck(email));
		if (userDao.getEmailCheck(email) == null) {
			check = "true";
		}
		return check;
	}

	@Override
	public String sendSMS(String phone, HttpSession session) throws Exception {

		/*
		 * SendSMSAPI send = new SendSMSAPI();
		 * 
		 * String code = send.sendSMS(phone);
		 */

		String code = "123";

		String check = "실패";

		if (code != null) {

			session.setAttribute("code", code);
			System.out.println("코드생성 및 발신 성공~");
			check = "전송";
		}

		return check;
	}

	@Override
	public String sendSMSSite(String phone, HttpSession session) {

		String check = "";
		String res = userDao.checkTypeAndPhone(phone);
		System.out.println("user_no === " + res);
		if (res != null) {

			/*
			 * SendSMSAPI send = new SendSMSAPI();
			 * 
			 * String code = send.sendSMS(phone);
			 */

			String code = "123";

			if (code != null) {
				session.setAttribute("code", code);
				System.out.println("코드생성 및 발신 성공~");
				check = res;
			}
		} else {
			System.out.println("미등록 유저");
		}

		return check;
	}

	@Override
	public String checkSMS(String input_code, HttpSession session) {

		String check = "true";

		// 발송 코드
		String code = (String) session.getAttribute("code");
		System.out.println("세션 코드 === " + code);

		if (!input_code.equals(code)) {
			check = "false";
		}
		return check;
	}

	@Override
	public String checkPwd(String check_pwd, HttpSession session) {

		UserDTO dto = (UserDTO) session.getAttribute("LoginUser");

		String check = "";

		if (check_pwd.equals(dto.getPwd())) {
			check = "true";
		}
		return check;
	}

	@Override
	public int deleteUser(String check_pwd, HttpSession session, HttpServletResponse response) throws Exception {

		UserDTO dto = (UserDTO) session.getAttribute("LoginUser");
		UserSessionDTO sdto = userDao.getUserSession(dto.getUser_no());
		String sessionID = sdto.getSessionID();
		
		int check = -1;
		int res = -1;
		
		if (check_pwd.equals(dto.getPwd()) && dto.getType().equals("S")) {
			// 쿼리문 type = D 로 변경
			userDao.deleteUser(dto.getUser_no());
			userDao.deleteUserSessionContent(dto.getUser_no());
			res = 1;
		}
		
		if(check_pwd.equals("")) {
			if (dto.getType().equals("K")) {
				deleteKakaorUser(sessionID);
			} else if (dto.getType().equals("N")) {
				deleteNaverUser(sessionID);
			}else if(dto.getType().equals("G")) {
				deleteGoogleUser(sessionID);
			}
			
			userDao.deleteUser(dto.getUser_no());
			userDao.deleteUserSessionContent(dto.getUser_no());
			
			res = 1;
		}
		
		
		
		if (res == 1) {
			check = 1;
			
			Cookie a_t = new Cookie("AccessToken", null);
	        a_t.setMaxAge(0);
	        a_t.setPath("/");
	        response.addCookie(a_t);
	        Cookie j_t = new Cookie("JSESSIONID", null);
	        a_t.setMaxAge(0);
	        a_t.setPath("/");
	        response.addCookie(j_t);
	        
			session.invalidate();
		}

		return check;
	}

	private void deleteKakaorUser(String sessionID) throws Exception {
		StringBuilder urlBuilder = new StringBuilder("https://kapi.kakao.com/v1/user/unlink");
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Authorization", "Bearer " + sessionID);
		System.out.println("delete code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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
		// sb.toString();
	}

	private void deleteNaverUser(String sessionID) throws Exception {

		StringBuilder urlBuilder = new StringBuilder("https://nid.naver.com/oauth2.0/token");
		urlBuilder.append("?" + URLEncoder.encode("grant_type", "UTF-8") + "=" + URLEncoder.encode("delete", "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("client_id", "UTF-8") + "="
				+ URLEncoder.encode("2fzdhIRlmXgPi9uo_5Xi", "UTF-8"));
		urlBuilder.append(
				"&" + URLEncoder.encode("client_secret", "UTF-8") + "=" + URLEncoder.encode("nPmw0vdmyR", "UTF-8"));
		urlBuilder
				.append("&" + URLEncoder.encode("access_token", "UTF-8") + "=" + URLEncoder.encode(sessionID, "UTF-8"));
		urlBuilder.append(
				"&" + URLEncoder.encode("service_provider", "UTF-8") + "=" + URLEncoder.encode("NAVER", "UTF-8"));
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
		System.out.println("delete code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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
	

	private void deleteGoogleUser(String sessionID) throws Exception {

		String urlBuilder = "https://oauth2.googleapis.com/revoke?token="+sessionID;
		URL url = new URL(urlBuilder);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
		System.out.println("delete code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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

	@Override
	public String getSnsProfile(HttpSession session, HttpServletResponse response) throws Exception {

		UserDTO dto = (UserDTO) session.getAttribute("LoginUser");

		UserSessionDTO sdto = userDao.getUserSession(dto.getUser_no());

		if (dto.getType().equals("N")) {
			sdto = naverTokenRefresh(sdto);
		} else if (dto.getType().equals("K")) {
			sdto = kakaoTokenRefresh(sdto);
		}

		// 쿠키 세션 등록
		Cookie a_t = new Cookie("AccessToken", sdto.getSessionID());
		a_t.setMaxAge(60 * 60 * 24 * 7);
		a_t.setPath("/");
		response.addCookie(a_t);

		return refreshProfile(sdto.getSessionID(), dto.getType());
	}

	// 로그인 토큰 관련 메서드 ===========================
	private String refreshProfile(String sessionID, String type) throws IOException {
		String profile = null;
		String curl = null;

		if (type.equals("K")) {
			curl = "https://kapi.kakao.com/v2/user/me";
		} else if (type.equals("N")) {
			curl = "https://openapi.naver.com/v1/nid/me";
		} else {
			return null;
		}

		URL url = new URL(curl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		conn.setRequestProperty("Authorization", "Bearer " + sessionID);
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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

		if (type.equals("N")) {
			JSONObject jo2 = jo.getJSONObject("response");
			if (jo2.has("profile_image")) {
				profile = jo2.getString("profile_image");
			}
		} else if (type.equals("K")) {
			JSONObject joA = jo.getJSONObject("kakao_account");
			if (joA.has("profile")) {
				JSONObject joP = joA.getJSONObject("profile");
				if (joP.has("thumbnail_image_url")) {
					profile = joP.getString("thumbnail_image_url");
				}
			}
		}
		return profile;
	}

	private UserSessionDTO kakaoTokenRefresh(UserSessionDTO se_dto) throws Exception {

		StringBuilder urlBuilder = new StringBuilder("https://kauth.kakao.com/oauth/token");
		urlBuilder.append("?" + URLEncoder.encode("grant_type", "UTF-8") + "=" + URLEncoder.encode("refresh_token", "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("client_id", "UTF-8") + "=" + URLEncoder.encode("98777fbdb2c9b1364e02210caf720b42", "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("refresh_token", "UTF-8") + "=" + URLEncoder.encode(se_dto.getRefreshToken(), "UTF-8"));
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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

		Long exp = (Long) (System.currentTimeMillis() / 1000) + (60 * 60 * 6);

		JSONObject jo = new JSONObject(sb.toString());
		String a_t = jo.getString("access_token");

		se_dto.setSessionID(a_t);
		se_dto.setExpiresTime(exp.toString());
		userDao.updateUserSession(se_dto);
		return se_dto;
	}

	private UserSessionDTO naverTokenRefresh(UserSessionDTO se_dto) throws Exception {

		StringBuilder urlBuilder = new StringBuilder("https://nid.naver.com/oauth2.0/token");
		urlBuilder.append(
				"?" + URLEncoder.encode("grant_type", "UTF-8") + "=" + URLEncoder.encode("refresh_token", "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("client_id", "UTF-8") + "="
				+ URLEncoder.encode("2fzdhIRlmXgPi9uo_5Xi", "UTF-8"));
		urlBuilder.append(
				"&" + URLEncoder.encode("client_secret", "UTF-8") + "=" + URLEncoder.encode("nPmw0vdmyR", "UTF-8"));
		urlBuilder.append("&" + URLEncoder.encode("refresh_token", "UTF-8") + "="
				+ URLEncoder.encode(se_dto.getRefreshToken(), "UTF-8"));
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
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

		Long exp = (Long) (System.currentTimeMillis() / 1000) + (60 * 60);

		JSONObject jo = new JSONObject(sb.toString());
		String a_t = jo.getString("access_token");

		se_dto.setSessionID(a_t);
		se_dto.setExpiresTime(exp.toString());

		userDao.updateUserSession(se_dto);
		return se_dto;
	}

	@Override
	public void getBoardUserCont(int user_no, Model model) {
		// TODO Auto-generated method stub
		UserDTO user = userDao.getUserContent(user_no);
		model.addAttribute("User_cont", user);
	}

	// 관리자
	@Override
	public void getUserList(Model model) {
		List<UserDTO> list = userDao.getUserList();
		model.addAttribute("userList", list);
	}

	@Override
	public void userDelete(int user_no) {
		// TODO Auto-generated method stub
		userDao.deleteUser(user_no);
		
	}
}
