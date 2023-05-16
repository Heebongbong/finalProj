package com.spring.finproj.model.user;

import java.util.List;

public interface UserDAO {
	
	//CRUD
	public List<UserDTO> getUserList();
	public UserDTO getUserContent(int user_no);
	public int insertUserContent(UserDTO dto);
	public int updateUserContent(UserDTO dto);
	public int deleteUserContent(int user_no);
	
	//부가기능
	public int updateUserPwd(UserDTO dto);
}
