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
	public static String desencritarData(String cipherText, String key) throws Exception {
		byte[] ivBytes = new byte[16];
        IvParameterSpec ivParams = new IvParameterSpec(ivBytes);

        byte[] keyBytes = key.getBytes("UTF-8");
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParams);

        byte[] cipherTextBytes = Base64.getDecoder().decode(cipherText.getBytes("UTF-8"));
        byte[] decryptedBytes = cipher.doFinal(cipherTextBytes);

        return new String(decryptedBytes);	
        //import org.jasypt.util.text.BasicTextEncryptor;
	}
}

