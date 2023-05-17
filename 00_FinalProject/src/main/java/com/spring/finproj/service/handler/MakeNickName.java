package com.spring.finproj.service.handler;

import java.util.Random;

import com.spring.finproj.model.user.UserDAOImpl;

public class MakeNickName {
	public String makeValue() {
		final char[] possibleCharacters =
				{'1','2','3','4','5','6','7','8','9','0'
				,'a','b','c','d'
				,'e','f','g','h'
				,'i','j','k','l'
				,'m','n','o','p'
				,'q','r','s','t'
				,'u','v','w','x','y','z'};
		final int possibleCharacterCount = possibleCharacters.length;
		Random rnd = new Random();
		StringBuffer buf = new StringBuffer(12);
		for (int i=8;i>0;i--) {
			buf.append(possibleCharacters[rnd.nextInt(possibleCharacterCount)]);
		}
		String nickName = buf.toString();
		
		return nickName;
	}
}