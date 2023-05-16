package com.spring.finproj.model.user;

import java.util.List;

public interface UserDAO {
	
	//CRUD
	public List<UserDTO> getUserList();
	public UserDTO getUserContent(int user_no);
	public int insertUserContent(UserDTO dto);
	public int updateUserContent(int user_no);
	public int deleteUserContent(int user_no);
	
	// 비밀번호 바꾸기
	public int updateUserContent(UserDTO dto);
}
