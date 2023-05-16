package com.spring.finproj.model.user;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO{
	@Autowired
	private SqlSessionTemplate template;

	@Override
	public List<UserDTO> getUserList() {
		// TODO Auto-generated method stub
		return template.selectList("user_list");
	}

	@Override
	public UserDTO getUserContent(int user_no) {
		// TODO Auto-generated method stub
		System.out.println(1);
		System.out.println(template);
		
		UserDTO d = template.selectOne("user_cont", user_no);
		System.out.println(d);
		return d;
	}

	@Override
	public int insertUserContent(UserDTO dto) {
		// TODO Auto-generated method stub
		return template.insert("user_insert", dto);
	}

	@Override
	public int updateUserContent(int user_no) {
		// TODO Auto-generated method stub
		return template.update("user_update", user_no);
	}

	@Override
	public int deleteUserContent(int user_no) {
		// TODO Auto-generated method stub
		return template.delete("user_delete", user_no);
	}
	
	@Override
	public int updateUserContent(UserDTO dto) {
		return template.update("user_update_pwd", dto);
	}

}
