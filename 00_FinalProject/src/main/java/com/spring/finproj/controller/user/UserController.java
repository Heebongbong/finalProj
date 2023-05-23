package com.spring.finproj.controller.user;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.spring.finproj.model.user.UserDTO;
import com.spring.finproj.service.user.UserService;


@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/snsProfile")
	@ResponseBody
	public String snsProfile(HttpSession session, HttpServletResponse response) throws Exception {
		return userService.getSnsProfile(session, response);
	}
	
	@RequestMapping("/mypage")
	public String userMypage() throws Exception {
		
		return "user.mypage";
	}
	
	@RequestMapping("/mypageOk")
	public String userMypageOk(@RequestParam("pwd_update") String pwd_update, UserDTO dto, Model model, HttpServletRequest request) throws Exception {
		
		
		return "user.mypageOk";
	}
	
	@RequestMapping("/join")
	public String userJoin() throws Exception {
		
		return "user.join";
	}
	
	@RequestMapping("/joinOk")
	public String userJoinOk(UserDTO dto, HttpServletRequest request, HttpServletResponse response, 
			@RequestParam("upfile") MultipartFile multipartFile, Model model) throws Exception {
		
		System.out.println("=================" + multipartFile);
		System.out.println("controller dto =====>" + dto);
		return userService.insertUserContent(dto, request, response, multipartFile, model);
		
	}
	
	@RequestMapping("/check/nickname")
	@ResponseBody
	public String userCheckNickname(String nickname, HttpSession session) throws Exception {
		System.out.println(nickname);
		return userService.getNickCheck(nickname, session);
	}
	
	@RequestMapping("/check/phone")
	@ResponseBody
	public String userCheckPhone(@RequestParam("phone") String phone) throws Exception {
		
		return userService.getPhoneCheck(phone);
	}
	
	@RequestMapping("/sms/send")
	@ResponseBody
	public String sendSMS(String phone, HttpSession session) throws Exception {

		return userService.sendSMS(phone, session);
	}
	
	@RequestMapping("/sms/check")
	@ResponseBody
	public String checkSMS(String input_code, HttpSession session) throws Exception {
		
		return userService.checkSMS(input_code, session);
	}
	
	@RequestMapping("/check/pwd")
	@ResponseBody
	public String checkPwd(String check_pwd, HttpSession session) throws Exception {
		
		System.out.println(check_pwd);
		return userService.checkPwd(check_pwd, session);
	}
	
	
	
}