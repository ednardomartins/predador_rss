package com.ednardo.predador.model.mongo.security;

import java.security.MessageDigest;

public class HashGenerate {

	private static final String MD5 = "MD5";

	public static String hashString(String message) throws Exception {

		try {
			MessageDigest digest = MessageDigest.getInstance(MD5);
			byte[] hashedBytes = digest.digest(message.getBytes("UTF-8"));

			return convertByteArrayToHexString(hashedBytes);
		} catch (Exception ex) {
			throw new Exception("Could not generate hash from String", ex);
		}
	}

	private static String convertByteArrayToHexString(byte[] arrayBytes) {
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < arrayBytes.length; i++) {
			stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		return stringBuffer.toString();
	}
}
