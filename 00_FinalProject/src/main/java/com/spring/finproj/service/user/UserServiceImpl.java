package com.spring.finproj.service.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.spring.finproj.model.user.UserDAO;
import com.spring.finproj.model.user.UserDTO;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDao;

	@Override
	public void getUserContent(Model model, int user_no) throws Exception {
		UserDTO dto = userDao.getUserContent(user_no);

		model.addAttribute("content", dto);
	}
	
	@SuppressWarnings("all")
	@Override
	public String insertUserContent(UserDTO dto, HttpServletRequest request, HttpServletResponse response, MultipartFile mfile, Model model) throws Exception {

		dto.setType("S");
		
		String originalFileName = mfile.getOriginalFilename();
	    
	    if (!originalFileName.isEmpty()) {
	    
	    // 절대경로 가져오기
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(request.getRealPath("WEB-INF\\classes\\properties\\filepath.properties"));
		prop.load(new InputStreamReader(fis));
		fis.close();
		
		LocalDateTime nowDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddhhmmss");
        String today = nowDate.format(formatter);
        
        // type + 이메일 아이디
        String type = dto.getType();
        
        StringTokenizer st = new StringTokenizer(dto.getEmail(), "@", true);
        String email_id = st.nextToken();
        
        String userFolder = "\\profile\\"  + type + "_" + email_id;
	    String saveFolder = prop.getProperty(System.getenv("USERPROFILE").substring(3)) + userFolder;
	    
	    File folder1 = new File(saveFolder);
	    File folder2 = new File(saveFolder + "\\" + today);
		if(!folder1.exists()) {
		    folder1.mkdirs();
		}
		
		if(!folder2.exists()) {
			folder2.mkdir();
		}
		

	    String saveFileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf('.'));
	    mfile.transferTo(new File(saveFolder + "\\" + today, saveFileName));
	    dto.setProfile("/finproj/resources/images/profile/" + type + "_" + email_id+"/" + today + "/" + saveFileName);
	    dto.setProfile_type(false);
		    
	    } else {
	    	// 기본이미지 세팅
	    	dto.setProfile("/finproj/resources/images/profile/default/default_profile.png"); 
	    	dto.setProfile_type(true);
	    }
	    
	    if(dto.getPhone() != null) {
	    	dto.setAuthen(false);
	    } else {
	    	dto.setAuthen(true);
	    }
	    
		System.out.println("service dto ================>>>> " + dto);

	    
		int check = userDao.insertUserContent(dto);
		int check2 = userDao.insertUserProfileContent(dto);
		
		if(check > 0) {
			return "redirect:/index";
		}else {//불일치
			model.addAttribute("msg", "글작성중 문제가 발생했습니다.");
		    return "error/error";
		}
		
	}

	@Override
	public void updateUserContent(UserDTO dto, String pwd_update, HttpServletResponse response) {

		int check = userDao.updateUserPwd(dto);

	}

	@Override
	public String getNickCheck(String nickName) throws IOException {
		String check = "";
		if(userDao.getNickCheck(nickName) == null) {
			check = "true";
		}
		return check;
	}
	
	@Override
	public String getPhoneCheck(String phone) {
		String check = "";
		if(userDao.getPhoneCheck(phone) == null) {
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
		session.setAttribute("code", code);
		System.out.println("코드생성 및 발신 성공~");
		String check = "전송 성공~";
		
		return check;
	}
	
	@Override
	public String checkSMS(String input_code, HttpSession session) {
		
		String check = "true";
		
		// 발송 코드
		String code = (String) session.getAttribute("code");
		System.out.println("세션 코드 === "+code);
		
		if(!input_code.equals(code)) {
			check = "false";
		}
		return check;
	}
	
	@Override
	public String getCodeCheck(String code) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String makeNickName() {
		// TODO Auto-generated method stub
		return null;
	}
}
