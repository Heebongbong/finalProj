package com.spring.finproj.service.handler;

import java.util.Random;

public class MakeCode {
	public String makeSMSCode() {
		
		final char[] possibleCharacters =
			{'1','2','3','4','5','6','7','8','9','0'};
		final int possibleCharacterCount = possibleCharacters.length;
		Random rnd = new Random();
		StringBuffer buf = new StringBuffer(12);
		for (int i=6;i>0;i--) {
			buf.append(possibleCharacters[rnd.nextInt(possibleCharacterCount)]);
		}
		String code = buf.toString();
			
		return code;
	}
}