package com.spring.finproj.model.chat;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ChatDAOImpl implements ChatDAO{
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public List<ChatDTO> getChatList(ChatDTO dto) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("chat_list", dto);
	}
	
}
