package com.spring.finproj.service.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.spring.finproj.model.chat.ChatDAO;
import com.spring.finproj.model.user.UserDTO;

@Component
public class ChatHandler extends TextWebSocketHandler {
	private List<WebSocketSession> sessions = new ArrayList<WebSocketSession>();
	private Map<Integer, WebSocketSession> userSessions = new HashMap<Integer, WebSocketSession>();
	@Autowired
	private ChatDAO chatDAO;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    	sessions.add(session);
        // 연결 성립 시 실행할 로직을 구현합니다.
        
        Map<String, Object> sessionVal =  session.getAttributes();
        
        UserDTO user = (UserDTO) sessionVal.get("LoginUser"); 
		int userId = user.getUser_no();
		
		if(userSessions.get(userId) != null) {
			//userId에 원래 웹세션값이 저장되어 있다면 update
			userSessions.replace(userId, session);
		} else {
			//userId에 웹세션값이 없다면 put
			userSessions.put(userId, session);
		}
		System.out.println(sessionVal.toString());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    	//System.out.println("handleTextmessage: " + session + " : " + message);
    	//message.toPayloadLength와 Payload의 실재값이 달라서 substring에서 오류가 발생함.
    	
		//protocol: 내용, 보내는id, 받는id (content, requestId, responseId)
		String msg = message.getPayload();
		if(StringUtils.isNotEmpty(msg)) {
			String[] strs = msg.split(",");
			if (strs != null && strs.length == 3) {
				String sendId = strs[0];
				String receiveUserId = strs[1];
				String content = strs[2];
				
				System.out.println(sendId+"/"+receiveUserId+"/"+content);
				System.out.println("최종맵리스트"+userSessions);
				//broadcasting
				if(receiveUserId.equals("")) {
					for (WebSocketSession sess: sessions) {
						//message를 TextMessage형태로 받음 (22번째줄, string x)
						sess.sendMessage(new TextMessage(message.getPayload()));
						//sess.sendMessage(new TextMessage(receiveUserId + ":" + message.getPayload()));
					}
				} else {
					WebSocketSession responseIdSession = userSessions.get(Integer.parseInt(receiveUserId));
					
					if (responseIdSession != null) {
						TextMessage tmpMsg = new TextMessage(sendId + "," + receiveUserId + "," + content);
						responseIdSession.sendMessage(tmpMsg);
					}
				}
			}
		}
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // 연결이 종료되었을 때 실행할 로직을 구현합니다.
    	sessions.remove(session);
		System.out.println("afterHandleTextmessage: " + session + " : " + status);
    }
}