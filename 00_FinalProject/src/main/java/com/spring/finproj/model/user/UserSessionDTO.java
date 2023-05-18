package com.spring.finproj.model.user;

import lombok.Data;

@Data
public class UserSessionDTO {
	private int user_no;
	private String sessionID;
	private String refreshToken;
	private String expiresTime;
}