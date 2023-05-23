package com.spring.finproj.service.user;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.spring.finproj.model.user.UserDTO;

public interface UserService {

	public String insertUserContent(UserDTO dto, HttpServletRequest request, HttpServletResponse response, MultipartFile multipartFile, Model model) throws Exception;
	public void updateUserContent(UserDTO dto, String pwd_update, HttpServletResponse response);
	public String getNickCheck(String nickname, HttpSession session) throws IOException;
	public String sendSMS(String phone, HttpSession session) throws Exception;
	public String checkSMS(String input_code, HttpSession session);
	public String getPhoneCheck(String phone);
	public String getSnsProfile(HttpSession session, HttpServletResponse response) throws Exception;
	public String checkPwd(String check_pwd, HttpSession session);

}