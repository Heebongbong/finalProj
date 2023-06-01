package com.spring.finproj.model.chat;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.finproj.model.alarm.AlarmDTO;

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

	@Override
	public List<FaqDTO> getFaqList() {
		// TODO Auto-generated method stub
		return sqlSession.selectList("faq_list");
	}

	@Override
	public ChatDTO getChatRoomContent(ChatDTO dto) {
		// TODO Auto-generated method stub
		return sqlSession.selectOne("chat_room_content", dto);
	}

	@Override
	public int deleteChatRoom(int chat_room_no) {
		// TODO Auto-generated method stub
		return sqlSession.delete("chat_room_delete", chat_room_no);
	}

	@Override
	public int deleteChatList(int chat_room_no) {
		// TODO Auto-generated method stub
		return sqlSession.delete("chat_list_delete", chat_room_no);
	}

	@Override
	public int insertAlarm(AlarmDTO a) {
		// TODO Auto-generated method stub
		return sqlSession.insert("alarm_insert", a);
	}

	@Override
	public List<AlarmDTO> getAlarmList(int user_no) {
		// TODO Auto-generated method stub
		return sqlSession.selectList("alarm_list", user_no);
	}
	
}
