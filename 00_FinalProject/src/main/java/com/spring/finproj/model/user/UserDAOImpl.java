package com.spring.finproj.model.user;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO{
	@Autowired
	private SqlSessionTemplate sqlSession;

	//CRUD
	@Override
	public List<UserDTO> getUserList() {
		// TODO Auto-generated method stub
		return sqlSession.selectList("user_list");
	}

	@Override
	public UserDTO getUserContent(int user_no) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("user_cont", user_no);
	}

	@Override
	public int insertUserContent(UserDTO dto) {
		// TODO Auto-generated method stub
		return sqlSession.insert("user_insert", dto);
	}

	@Override
	public int updateUserContent(UserDTO dto) {
		// TODO Auto-generated method stub
		return sqlSession.update("user_update", dto);
	}

	@Override
	public int deleteUserContent(int user_no) {
		// TODO Auto-generated method stub
		return sqlSession.delete("user_delete", user_no);
	}
	
	
	//부가기능
	@Override
	public int updateUserPwd(UserDTO dto) {
		return sqlSession.update("user_update_pwd", dto);
	}
	
}
