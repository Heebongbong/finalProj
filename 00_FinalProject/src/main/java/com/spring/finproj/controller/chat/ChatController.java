package com.spring.finproj.controller.chat;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.finproj.model.chat.ChatDTO;
import com.spring.finproj.service.chat.ChatService;

@Controller
@RequestMapping("/chat")
public class ChatController {
	@Autowired
	private ChatService chatService;
	
	@RequestMapping("/enter")
	@ResponseBody
	public List<ChatDTO> chatEnter(int user_no, HttpSession session) {
		
		List<ChatDTO> list = chatService.getChatListContUser(user_no, session);
		return list;
	}
	
	@RequestMapping("/board")
	@ResponseBody
	public int chatBoard(int user_no, HttpSession session) {
		return chatService.insertChatRoomCont(user_no, session);
	}
}