package com.spring.finproj.model.user;

import java.util.List;
import java.util.Map;

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

	@Override
	public UserDTO getUserContentId(Map<String, Object> idlist) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("user_cont_id", idlist);
	}

	@Override
	public int insertUserSession(UserSessionDTO dto) {
		// TODO Auto-generated method stub
		return sqlSession.insert("user_insert_session", dto);
	}

	@Override
	public int insertUserSNSContent(UserDTO user) {
		// TODO Auto-generated method stub
		return sqlSession.insert("user_insert_sns", user);
	}

	@Override
	public int insertUserSNSProfileContent(UserDTO user) {
		// TODO Auto-generated method stub
		return sqlSession.insert("user_insert_sns_profile", user);
	}

	@Override
	public int deleteUserSessionContent(int user_no) {
		// TODO Auto-generated method stub
		return sqlSession.delete("user_delete_session", user_no);
	}

	public String getNickCheck(String nickName) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("user_nick_check", nickName);
	}
	
	@Override
	public Object getPhoneCheck(String phone) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("user_phone_check", phone);
	}

	@Override
	public UserSessionDTO getUserSession(int user_no) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("user_get_session_no", user_no);
	}

	@Override
	public UserSessionDTO getUserSession(String sessionID) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("user_get_session_id", sessionID);
	}

	@Override
	public void updateUserSession(UserSessionDTO se_dto) {
		// TODO Auto-generated method stub
		sqlSession.update("user_update_session", se_dto);
	}
	
}
