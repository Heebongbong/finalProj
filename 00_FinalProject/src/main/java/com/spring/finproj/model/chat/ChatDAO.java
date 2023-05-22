package com.spring.finproj.model.chat;

import java.util.List;

public interface ChatDAO {
	public List<ChatDTO> getChatList(ChatDTO dto);
	public List<ChatDTO> getChatRoomList(int user_no);
	public int insertChatRoomCont(ChatDTO dto);
	public void insertChatCont(ChatDTO c_dto);
}
