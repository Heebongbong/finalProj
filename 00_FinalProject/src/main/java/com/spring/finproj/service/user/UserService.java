package com.spring.finproj.service.user;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.spring.finproj.model.user.UserDTO;

public interface UserService {

	public int insertUserContent(UserDTO dto, HttpServletRequest request, HttpServletResponse response, HttpSession session, MultipartFile multipartFile) throws Exception;
	public String getNickCheck(String nickname, HttpSession session) throws IOException;
	public String sendSMS(String phone, HttpSession session) throws Exception;
	public String checkSMS(String input_code, HttpSession session);
	public String getPhoneCheck(String phone, String type);
	public String getEmailCheck(String email);
	public String getSnsProfile(HttpSession session, HttpServletResponse response) throws Exception;
	public String checkPwd(String check_pwd, HttpSession session);
	public int updateUserContent(UserDTO dto, HttpSession session, MultipartFile mfile, HttpServletRequest request) throws Exception;
	public int updatePwd(UserDTO dto);
	public String sendSMSSite(String phone, HttpSession session) throws Exception;
	public int deleteUser(HttpSession session, HttpServletResponse response) throws Exception;
	public void getBoardUserCont(int user_no, Model model);
	
	
	//관리자
	public void getUserList(Model model);
	public void userDelete(int user_no);

}