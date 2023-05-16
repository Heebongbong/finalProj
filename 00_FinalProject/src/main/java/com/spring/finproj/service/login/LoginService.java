package com.spring.finproj.service.login;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface LoginService {
	public void loginGoogle(String token, String credential, HttpSession session, HttpServletResponse response) throws Exception;
	public void loginNaver(String code, String state, HttpSession session, HttpServletResponse response) throws Exception;
	public void loginKakao(String code, HttpSession session, HttpServletResponse response, HttpServletRequest request) throws Exception;
	public void loginSite(String id, String pwd, HttpSession session, HttpServletResponse response);
	public void logoutUser(HttpSession session, HttpServletResponse response, String sessionID) throws Exception;
}