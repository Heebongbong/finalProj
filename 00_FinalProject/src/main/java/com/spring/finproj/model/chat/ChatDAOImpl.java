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

	@Override
	public List<ChatDTO> getChatRoomList(int user_no) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("chat_room_list", user_no);
	}

	@Override
	public int insertChatRoomCont(ChatDTO dto) {
		// TODO Auto-generated method stub
		return sqlSession.insert("chat_room_insert", dto);
	}

	@Override
	public void insertChatCont(ChatDTO c_dto) {
		// TODO Auto-generated method stub
		sqlSession.insert("chat_insert", c_dto);
	}
	
}
