package com.first.spring.utilities;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.HexFormat;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.springframework.core.env.Environment;

public class AESEncryptor {

	private final String AES_ALGORITHM = "AES/CBC/PKCS5Padding";

	private SecretKey secretKey;

	private IvParameterSpec iv;
	
	private Logger logger = Loggers.getControllersLogger();

	public AESEncryptor() {
		try {
			this.secretKey = generateSecretKey();
			this.iv = generateIv();
		} catch (Exception ex) {
			ex.getStackTrace();
		}

	}
	
	
	public SecretKey getSecretKey() {
		return secretKey;
	}

	public IvParameterSpec getIv() {
		return iv;
	}

	public String encrypt(String plainText) throws Exception {
		Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
		return Base64.getEncoder().encodeToString(encryptedBytes);
	}

	public String decrypt(String encryptedText) throws Exception {
		Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
		byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));

		return new String(decryptedBytes);
	}

	private SecretKey generateSecretKey() throws NoSuchAlgorithmException {

		KeyGenerator keyGen = KeyGenerator.getInstance("AES");

		// Initialize the key generator with the desired key size (e.g., 256 bits)
		keyGen.init(256);

		// Generate the secret key
		SecretKey secretKey = keyGen.generateKey();
		logger.warn("AES_SECRET_KEY: " + toHex(secretKey.getEncoded()));

		return secretKey;
	}

	private IvParameterSpec generateIv() {
		byte[] iv = new byte[16];
		new SecureRandom().nextBytes(iv);
		logger.warn("AES_IV: " + toHex(iv));
		return new IvParameterSpec(iv);
	}

	private String toHex(byte[] keyBytes) {
		// Convert bytes to hexadecimal string
		StringBuilder hexString = new StringBuilder();
		for (byte b : keyBytes) {
			hexString.append(String.format("%02X", b));
		}
		 
		return hexString.toString();
	}
	
//	 private static String toUTF8(byte[] bytes) {
//	        // Convert byte array to UTF-8 string
//	        return new String(bytes, StandardCharsets.UTF_8);
//	    }

}