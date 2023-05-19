package com.spring.finproj.service.user;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.spring.finproj.model.user.UserDTO;

public interface UserService {

	void getUserContent(Model model, int user_no) throws Exception;
	
	void insertUserContent(UserDTO dto, HttpServletRequest request, HttpServletResponse response, MultipartFile multipartFile) throws Exception;
	
	void updateUserContent(UserDTO dto, String pwd_update, HttpServletResponse response);

	String getNickCheck(String nickname) throws IOException;

	String sendSMS(String phone, HttpSession session) throws Exception;

	String checkSMS(String input_code, HttpSession session);

	String makeNickName();

	String getPhoneCheck(String phone);

}