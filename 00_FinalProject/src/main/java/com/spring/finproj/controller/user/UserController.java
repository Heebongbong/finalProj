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
	
	@RequestMapping("/content")
	public String userMypage(@RequestParam("user_no") int user_no, Model model) throws Exception {
		
		userService.getUserContent(model, user_no);
		
		return "user.mypage";
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
	public String userCheckNickname(@RequestParam("nickname") String nickname) throws Exception {
		
		return userService.getNickCheck(nickname);
	}
	
	@RequestMapping("/check/phone")
	@ResponseBody
	public String userCheckPhone(@RequestParam("phone") String phone) throws Exception {
		
		return userService.getPhoneCheck(phone);
	}
	
	@RequestMapping("/check/code")
	@ResponseBody
	public String userCheckCode(@RequestParam("code") String code) throws Exception {
		
		return userService.getCodeCheck(code);
	}
	
	@RequestMapping("/insert")
	public String userMypageOk(@RequestParam("pwd_update") String pwd_update, UserDTO dto, Model model, HttpServletRequest request) throws Exception {
		
		
		return "user.mypageOk";
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
	
	
	
}