package com.spring.finproj.model.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FaqDTO {
	private int faq_no;
	private int faq_cate_no;
	private String name;
	private String content;
}
