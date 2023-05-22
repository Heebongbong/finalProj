package com.spring.finproj.service.chat;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.spring.finproj.model.chat.ChatDTO;

public interface ChatService {
	public List<ChatDTO> getChatListContUser(int user_no, HttpSession session);
	public int insertChatRoomCont(int user_no, HttpSession session);
}
