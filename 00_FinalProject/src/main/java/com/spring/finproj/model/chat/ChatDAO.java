package com.spring.finproj.model.chat;

import java.util.List;

import com.spring.finproj.model.alarm.AlarmDTO;

public interface ChatDAO {
	public List<ChatDTO> getChatList(ChatDTO dto);
	public List<ChatDTO> getChatRoomList(int user_no);
	public int insertChatRoomCont(ChatDTO dto);
	public void insertChatCont(ChatDTO c_dto);
	public List<FaqDTO> getFaqList();
	public ChatDTO getChatRoomContent(ChatDTO dto);
	public int deleteChatRoom(int chat_room_no);
	public int deleteChatList(int chat_room_no);
	public int insertAlarm(AlarmDTO a);
	public List<AlarmDTO> getAlarmList(int user_no);
	public int updateChatExitUser(ChatDTO dto);
	public ChatDTO getChatRoomContent(int chat_room_no);
}