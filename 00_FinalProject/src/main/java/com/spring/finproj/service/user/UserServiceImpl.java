package com.spring.finproj.service.user;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.spring.finproj.model.user.UserDAO;
import com.spring.finproj.model.user.UserDTO;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDAO dao;
	
	@Override
	public void getUserContent(Model model, int user_no) throws Exception {
		UserDTO dto = dao.getUserContent(user_no);
		
		model.addAttribute("content", dto);
	}
	
	@Override
	public void updateUserContent(UserDTO dto, String pwd_update, HttpServletResponse response) {
		
		int check = dao.updateUserPwd(dto);
		
	}
}
