package com.spring.finproj.controller.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.finproj.service.login.LoginService;

@Controller
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private LoginService loginService;
	
	@RequestMapping("/logout")
	public String logout(HttpSession session, HttpServletResponse response,
			@CookieValue(value = "AccessToken", required = false) String access_token,
			@CookieValue(value = "JSESSIONID", required = false) String jsessionID) throws Exception {

		String sessionID = null;
		if(access_token==null) {
			sessionID = jsessionID;
		}else {
			sessionID = access_token;
		}
		loginService.logoutUser(session, response, sessionID);
		return "redirect:/index";
	}
	
	@RequestMapping("/kakao")
	public String loginKakao(String code, HttpSession session, 
			HttpServletResponse response, HttpServletRequest request) throws Exception {
		loginService.loginKakao(code, session, response, request);
		response.setContentType("text/html; charset=UTF-8");
		return "redirect:/index";
	}
	
	@RequestMapping("/google")
	public String loginGoogle(String credential, String g_csrf_token, HttpSession session, 
			HttpServletResponse response, HttpServletRequest request) throws Exception {
		loginService.loginGoogle(g_csrf_token, credential, session, response);
		response.setContentType("text/html; charset=UTF-8");
		return "redirect:/index";
	}
	
	@RequestMapping("/naver")
	public String loginNaver(@RequestParam(required = false) String code, String state,
			HttpSession session, HttpServletResponse response, HttpServletRequest request,
			@RequestParam(required = false) String error) throws Exception {
		System.out.println(error);
		response.setContentType("text/html; charset=UTF-8");
		if(error!=null) {
			return "redirect:/index";
		}else {
			loginService.loginNaver(code, state, session, response);
			return "redirect:/index";
		}
	}
	
	@RequestMapping("/site")
	public void loginSite(HttpSession session, 
			HttpServletResponse response, @CookieValue("JSESSIONID") String jSessionId,
			String email, String pwd) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		loginService.loginSite(email, pwd, session, response, jSessionId);
	}
}