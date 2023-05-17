package com.spring.finproj.model.user;

import java.util.List;
import java.util.Map;

public interface UserDAO {
	
	//CRUD
	public List<UserDTO> getUserList();
	public UserDTO getUserContent(int user_no);
	public int insertUserContent(UserDTO dto);
	public int updateUserContent(UserDTO dto);
	public int deleteUserContent(int user_no);
	
	//부가기능
	public int updateUserPwd(UserDTO dto);
	public UserDTO getUserContentId(Map<String, Object> idlist);
	public int insertUserSession(Map<String, Object> list);
	public int insertUserSNSContent(UserDTO user);
	public int insertUserSNSProfileContent(UserDTO user);
	public int deleteUserSessionContent(int user_no);
	public String getNickCheck(String nickName) ;
}
