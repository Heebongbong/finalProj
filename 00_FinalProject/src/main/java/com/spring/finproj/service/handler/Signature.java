package com.spring.finproj.service.handler;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import lombok.Data;

@Data
public class Signature {
	private String nowTime;
	
	public Signature() {
		// TODO Auto-generated constructor stub
		Long nt = System.currentTimeMillis();
		this.nowTime = nt.toString();
	}
	
	public String makeGetSignature(String url) throws Exception {
		
		String space = " ";					// one space
		String newLine = "\n";					// new line
		String method = "GET";					// method
		String timestamp = nowTime;			// current timestamp (epoch)
		String accessKey = "So6WkkHqyaafmNSxc05s";			// access key id (from portal or Sub Account)
		String secretKey = "hXkKNbkzF8wv85XB02KBiQ1xDnOvVq0PnVSKN5gY";

		String message = new StringBuilder()
			.append(method)
			.append(space)
			.append(url)
			.append(newLine)
			.append(timestamp)
			.append(newLine)
			.append(accessKey)
			.toString();

		SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(signingKey);

		byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
		String encodeBase64String = Base64.encodeBase64String(rawHmac);

	  return encodeBase64String;
	}
	
public String makePostSignature(String url) throws Exception {
		
		String space = " ";					// one space
		String newLine = "\n";					// new line
		String method = "POST";					// method
		String timestamp = nowTime;			// current timestamp (epoch)
		String accessKey = "So6WkkHqyaafmNSxc05s";			// access key id (from portal or Sub Account)
		String secretKey = "hXkKNbkzF8wv85XB02KBiQ1xDnOvVq0PnVSKN5gY";

		String message = new StringBuilder()
			.append(method)
			.append(space)
			.append(url)
			.append(newLine)
			.append(timestamp)
			.append(newLine)
			.append(accessKey)
			.toString();

		SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
		Mac mac = Mac.getInstance("HmacSHA256");
		mac.init(signingKey);

		byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
		String encodeBase64String = Base64.encodeBase64String(rawHmac);

	  return encodeBase64String;
	}
}
