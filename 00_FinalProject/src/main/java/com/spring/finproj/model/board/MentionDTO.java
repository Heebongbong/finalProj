package com.spring.finproj.model.board;

import lombok.Data;

@Data
public class MentionDTO {
	private int mention_no;
	private int cm_no;
	private int user_no;
	private String ment;
	private String created;
	private String nickname;
	private String profile;
	
	private int likeCount;
}
