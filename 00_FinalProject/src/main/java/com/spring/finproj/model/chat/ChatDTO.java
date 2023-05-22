package com.spring.finproj.model.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatDTO {
	private int chat_no;
	private int chat_room_no;
	private int user_no1;
	private int user_no2;
	private String chat_cont;
	private int send_user;
	private int exit_user;
	private String created;
	private String nickname;
}