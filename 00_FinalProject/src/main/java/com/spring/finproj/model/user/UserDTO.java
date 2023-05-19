package com.spring.finproj.model.user;

import lombok.Data;

@Data
public class UserDTO {
	private int user_no;
	private String email;
	private String pwd;
	private String nickname;
	private String phone;
	private String profile;
	private String type;
	private String date;
	private int point;
	private boolean authen;
	private boolean profile_type;
}