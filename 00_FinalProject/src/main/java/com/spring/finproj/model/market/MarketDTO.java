package com.spring.finproj.model.market;

import lombok.Data;

@Data
public class MarketDTO {

	private int cm_no;
	private int user_no;
	private String title;
	private String content;
	private String date;
	private String update;
	private String hashtag;
	private String kategory;
	private int price;
	private int index;
	private String photo_folder;
	private String phot_length;
}
