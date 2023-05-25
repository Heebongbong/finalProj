package com.spring.finproj.service.chat;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.spring.finproj.model.chat.ChatDTO;
import com.spring.finproj.model.chat.FaqDTO;

public interface ChatService {
	public List<ChatDTO> getChatListContUser(int user_no, HttpSession session);
	public int insertChatRoomCont(int user_no, HttpSession session);
	public Map<Integer, List<FaqDTO>> getFaqList();
}
