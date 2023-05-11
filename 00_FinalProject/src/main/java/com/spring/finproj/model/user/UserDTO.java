package com.spring.finproj.model.user;

import lombok.Data;

@Data
public class UserDTO {
	private int user_no;
	private String user_email;
	private String user_pwd;
	private String user_nickname;
	private String user_phone;
	private String user_profile;
	private String user_type;
	private String user_token;
}