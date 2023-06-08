package com.spring.finproj.service.chat;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.spring.finproj.model.chat.FaqDTO;

public interface ChatService {
	public Map<String, Object> getChatListContUser(int user_no, HttpSession session);
	public String insertChatRoomCont(int user_no, HttpSession session);
	public Map<Integer, List<FaqDTO>> getFaqList();
	public int deleteChatRoom(int chat_room_no);
}
