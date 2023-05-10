package com.mx.consultaya.utils;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.mx.consultaya.exception.CustomException;
import com.mx.consultaya.model.JWTGenerator;
import com.mx.consultaya.model.Usuario;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {

    /**
     * Descifra los datos encriptados proporcionados utilizando la clave y el vector
     * de inicialización especificados.
     *
     * @param encryptedData los datos encriptados que se van a descifrar.
     * @param key           la clave utilizada para encriptar los datos.
     * @param iv            el vector de inicialización utilizado para encriptar los
     *                      datos.
     * @return una cadena que representa los datos descifrados.
     * @throws CustomException si se produce un error al descifrar los datos.
     */
    public static String decryptData(String encryptedData, String key, String iv) throws CustomException {
        try {
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
            byte[] encryptedKey = Base64.getDecoder().decode(key);
            byte[] encryptedIv = Base64.getDecoder().decode(iv);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            SecretKeySpec secretKeySpec = new SecretKeySpec(encryptedKey, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(encryptedIv);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes);
        } catch (IllegalArgumentException | NoSuchAlgorithmException | InvalidKeyException
                | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException
                | NoSuchPaddingException e) {
            throw new CustomException("Error al descifrar los datos", e);
        }
    }

    public static String verifyToken(Usuario user) {
        try {

            log.info("usuario {}", user);
            JWTGenerator jwt = new JWTGenerator();
            Map<String, String> mapa = new HashMap<>();
            mapa.put("nombre", user.getNombre());
            mapa.put("apellidos", user.getApellidos());
            mapa.put("correoElectronico", user.getCorreoElectronico());
            return jwt.generateJWT(mapa);
        } catch (Exception e) {
            return null;
        }

    }
}
