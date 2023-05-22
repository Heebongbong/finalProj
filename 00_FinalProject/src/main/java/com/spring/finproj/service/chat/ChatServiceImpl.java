package com.spring.finproj.service.chat;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.finproj.model.chat.ChatDAO;
import com.spring.finproj.model.chat.ChatDTO;
import com.spring.finproj.model.user.UserDTO;

@Service
public class ChatServiceImpl implements ChatService {
	@Autowired
	private ChatDAO chatDAO;
	
	@Override
	public List<ChatDTO> getChatListContUser(int user_no, HttpSession session) {
		// TODO Auto-generated method stub
		UserDTO loginUser = (UserDTO)session.getAttribute("LoginUser");
		
		ChatDTO dto = new ChatDTO();
		dto.setUser_no1(loginUser.getUser_no());
		dto.setUser_no2(user_no);
		
		List<ChatDTO> chatList = chatDAO.getChatList(dto);
		
		return chatList;
	}

	@Override
	public int insertChatRoomCont(int user_no, HttpSession session) {
		// TODO Auto-generated method stub
		ChatDTO dto = new ChatDTO();
		UserDTO login = (UserDTO) session.getAttribute("LoginUser");
		dto.setUser_no1(login.getUser_no());
		dto.setUser_no2(user_no);
		return chatDAO.insertChatRoomCont(dto);
	}
}
