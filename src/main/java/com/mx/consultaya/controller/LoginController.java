package com.mx.consultaya.controller;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mx.consultaya.model.EncryptedData;
import com.mx.consultaya.model.Usuario;
import com.mx.consultaya.service.LoginService;
import com.mx.consultaya.utils.Utils;

import jakarta.validation.Valid;
import java.nio.charset.StandardCharsets;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("")
public class LoginController {
	
	private LoginService loginService;
	

	@PostMapping(path = "signUp")

	public ResponseEntity<Usuario> saveUser(@RequestBody @Valid Usuario user){
		log.info("guarda usuario {}",user.toString());
		return ResponseEntity.ok(loginService.saveUsuario(user));
	}

	

	@PostMapping(path = "signIn")
	public ResponseEntity<Object> singIn(@RequestBody @Valid EncryptedData encryptedData){
		log.info("loggeando usuario");
		String data = encryptedData.getData();
		String key = encryptedData.getKey();
		String iv = encryptedData.getIv();

		log.info("Data: " + data);
		log.info("Key: " + key);
		log.info("iv: " + iv);

		try {
			
			String dataDecrypt = decryptData(data, key, iv);

			Gson g = new Gson();
			
			Usuario user = g.fromJson(dataDecrypt, Usuario.class);

			log.info(user.getEmail()+ " " + user.getPassword());

			Usuario usuario = loginService.loggearUsuario(user);

			log.info(usuario != null ? "El usuario existe este es su email: " + usuario.getEmail() : null);

			if (usuario == null) {
			    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Credenciales invalidas\"}");
			} else {
			    return ResponseEntity.ok(usuario);
			}
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Credenciales invalidas\"}");
		}	
	}

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
