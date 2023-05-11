package com.spring.finproj.service.login;

import javax.servlet.http.HttpSession;

public interface LoginService {
	public void loginGoogle(String token, String credential, HttpSession session) throws Exception;
	public void loginNaver();
	public void loginSite();
	public void loginKakao(String code, HttpSession session) throws Exception;
	public void logoutUser(HttpSession session) throws Exception;
}