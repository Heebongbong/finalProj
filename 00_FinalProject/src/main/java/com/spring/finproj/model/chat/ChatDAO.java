package com.spring.finproj.model.chat;

import java.util.List;

public interface ChatDAO {
	public List<ChatDTO> getChatList(ChatDTO dto);

}
