package com.spring.finproj.service.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

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
	
	@Override
	public void insertUserContent(UserDTO dto, HttpServletRequest request, MultipartFile multipartFile) throws Exception {
		
		if(dto.getProfile() == null) {
			dto.setProfile("sns");
		}
		
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(request.getRealPath("WEB-INF\\classes\\properties\\filepath.properties"));
		prop.load(new InputStreamReader(fis));
		fis.close();
		
		int fileSize = 100 * 1024 * 1024; // 100MB

	    String saveFolder = prop.getProperty(System.getenv("USERPROFILE").substring(3)) + "\\profile";
	    
	    String originalFileName = multipartFile.getOriginalFilename();
	    long size = multipartFile.getSize(); //파일 사이즈
	    
		
	    UUID uuid = UUID.randomUUID();
		System.out.println(uuid.toString());
		String[] uuids = uuid.toString().split("-");
		
		String uniqueName = uuids[0];
		
		File saveFile = new File(saveFolder+"\\"+uniqueName + originalFileName);  
		
		try {
			multipartFile.transferTo(saveFile); // 실제 파일 저장메서드(filewriter 작업을 손쉽게 한방에 처리해준다.)
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
		int check = userDao.insertUserContent(dto);
		
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
	public String makeNickName() {
		// TODO Auto-generated method stub
		return null;
	}
}
