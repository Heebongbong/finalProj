package com.spring.finproj.controller.login;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.finproj.service.login.LoginService;

@Controller
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private LoginService loginService;
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) throws Exception {
		loginService.logoutUser(session);
		return "redirect:/index";
	}
	
	@RequestMapping("/kakao")
	public String loginKakao(@RequestParam(value = "code") String code, HttpSession session) throws Exception {
		loginService.loginKakao(code, session);
		return "redirect:/index";
	}
	
	@RequestMapping("/google")
	public String loginGoogle(String credential, String g_csrf_token, HttpSession session) throws Exception {
		loginService.loginGoogle(g_csrf_token, credential, session);
		return "redirect:/index";
	}
}