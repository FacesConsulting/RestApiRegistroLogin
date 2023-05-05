package com.mx.consultaya.utils;

import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class Utils {
    /**
	 * @param data
	 * @return
	 */
	public static String decryptData(String encryptedData, String key, String iv) throws Exception {
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] encryptedKey = Base64.getDecoder().decode(key);
        byte[] encryptedIv = Base64.getDecoder().decode(iv);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        SecretKeySpec secretKeySpec = new SecretKeySpec(encryptedKey, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(encryptedIv);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }	
}

