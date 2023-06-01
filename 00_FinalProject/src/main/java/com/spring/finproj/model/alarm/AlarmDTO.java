package com.spring.finproj.model.alarm;

import lombok.Data;

@Data
public class AlarmDTO {
	private int alarm_no;
	private int user_no;
	private int field;
	private boolean check;
}