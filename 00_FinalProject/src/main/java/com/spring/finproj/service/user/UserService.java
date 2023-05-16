package com.spring.finproj.service.user;

import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.spring.finproj.model.user.UserDTO;

public interface UserService {
	
	
	void getUserContent(Model model, int user_no) throws Exception;
	
	void updateUserContent(UserDTO dto, String pwd_update, HttpServletResponse response);
	
}