package com.spring.finproj.service.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.finproj.model.chat.ChatDAO;
import com.spring.finproj.model.chat.ChatDTO;
import com.spring.finproj.model.chat.FaqDTO;
import com.spring.finproj.model.user.UserDAO;
import com.spring.finproj.model.user.UserDTO;

@Service
public class ChatServiceImpl implements ChatService {
	@Autowired
	private ChatDAO chatDAO;
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public Map<String, Object> getChatListContUser(int user_no, HttpSession session) {
		// TODO Auto-generated method stub
		UserDTO loginUser = (UserDTO)session.getAttribute("LoginUser");
		UserDTO send_user = userDAO.getUserContent(user_no);
		
		ChatDTO dto = new ChatDTO();
		dto.setUser_no1(loginUser.getUser_no());
		dto.setUser_no2(user_no);
		
		Map<String, Object> list = new HashMap<String, Object>();
		
		List<ChatDTO> chatList = chatDAO.getChatList(dto);
		
		list.put("ChatList", chatList);
		list.put("Send_user", send_user);
		
		
		//채팅알람 삭제
		chatDAO.deleteAlarmchat(loginUser.getUser_no());
		
		return list;
	}

	@Override
	public String insertChatRoomCont(int user_no, HttpSession session) {
		// TODO Auto-generated method stub
		ChatDTO dto = new ChatDTO();
		UserDTO login = (UserDTO) session.getAttribute("LoginUser");
		dto.setUser_no1(login.getUser_no());
		dto.setUser_no2(user_no);
		
		ChatDTO re_dto = chatDAO.getChatRoomContent(dto);
		
		if(re_dto==null) { //신규 채팅 등록
			int re = chatDAO.insertChatRoomCont(dto);
			if(re>0) {
				dto.setSend_user(login.getUser_no());
				dto.setChat_cont(login.getNickname()+"님이 채팅을 요청합니다.");
				ChatDTO room_cont = chatDAO.getChatRoomContent(dto);
				dto.setChat_room_no(room_cont.getChat_room_no());
				chatDAO.insertChatCont(dto);
				UserDTO u = userDAO.getUserContent(user_no);
				String str = "{ 'nickname' : '" + u.getNickname()
						+ "' , 'profile' : '" + u.getProfile()
						+ "' , 'chat_room_no' : '" + dto.getChat_room_no()
						+ "' }";
				JSONObject j = new JSONObject(str);
				return j.toString();
			}else {
				return "0";
			}
		}else { //기존 채팅 열기 - 채팅룸 번호
			String str = "{ 'nickname' : '" + "''"
					+ "' , 'profile' : '" + "''"
					+ "' , 'chat_room_no' : '" + re_dto.getChat_room_no()
					+ "' }";
			
			JSONObject j = new JSONObject(str);
			return j.toString();
		}
	}

	@Override
	public Map<Integer, List<FaqDTO>> getFaqList() {
		// TODO Auto-generated method stub
		
		List<FaqDTO> list = chatDAO.getFaqList();
		
		Map<Integer, List<FaqDTO>> faqList = new HashMap<Integer, List<FaqDTO>>();
		
		for(int i=1;i<=5;i++) {
			List<FaqDTO> l = new ArrayList<FaqDTO>();
			for(FaqDTO f : list) {
				if(f.getFaq_cate_no()==i) {
					l.add(f);
				}
			}
			faqList.put(i, l);
		}
		return faqList;
	}

	@Override
	public int deleteChatRoom(int chat_room_no, HttpSession session) {
		// TODO Auto-generated method stub
		ChatDTO dto = new ChatDTO();
		
		UserDTO logU = (UserDTO) session.getAttribute("LoginUser");
		
		dto.setChat_room_no(chat_room_no);
		
		dto = chatDAO.getChatRoomContent(dto.getChat_room_no());
		if(dto.getExit_user()==0) { // 나간 유저가 없을 때 exit user 세팅
			dto.setExit_user(logU.getUser_no());
			chatDAO.updateChatExitUser(dto);
			return 1;
		}else { // 한명이 나가면 채팅방 삭제
			int re = chatDAO.deleteChatRoom(chat_room_no);
			if(re>0) {
				re = chatDAO.deleteChatList(chat_room_no);
			}
			return 2;
		}
	}
}