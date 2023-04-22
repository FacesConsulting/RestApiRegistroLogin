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

import com.mx.consultaya.model.Usuario;
import com.mx.consultaya.service.LoginService;
import com.mx.consultaya.utils.Utils;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("")
public class LoginController {
	
	private LoginService loginService;
	
	@PostMapping(path = "registrar")
	public ResponseEntity<Usuario> saveUser(@RequestBody @Valid Usuario user){
		log.info("guarda usuario {}",user.toString());
		return ResponseEntity.ok(loginService.saveUsuario(user));
	}

	
	@PostMapping(path = "ingresar")
	public ResponseEntity<Object> loggearUser(@RequestBody @Valid String data)throws Exception{

		log.info("loggeando usuario");
		log.info("data: "+data);
		String des1 = Utils.desencritarData("JiXKp/pTKGTg5csVALDawlX9fiZ470S1EA36W82Qvhszj/bCVn4nhY+dORPhSYws","12345678901234567890123456789012");
		log.info("desencriptado 1 "+ des1);
		String des = Utils.desencritarData(data,"12345678901234567890123456789012");
		log.info("desencriptado front"+ des);
		
		// log.info(user.getEmail()+ " " + user.getPassword());
		// Usuario usuario = loginService.loggearUsuario(user);
		// log.info(usuario != null ? "El usuario existe este es su email: " + usuario.getEmail() : null);
		// if (usuario == null) {
		// 	log.info(" usuario nulo");
        // } else {
		// 	return ResponseEntity.ok(usuario);
        // }	
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Credenciales invalidas\"}");
	}
	@PostMapping(path ="encriptar")
    public ResponseEntity<Boolean> encriptar(String data,String key,String iv) throws Exception{
        byte[] decodedKey = Base64.getDecoder().decode(key);
        byte[] decodedIv = Base64.getDecoder().decode(iv);
        byte[] decodedData = Base64.getDecoder().decode(data);

        SecretKeySpec keySpec = new SecretKeySpec(decodedKey, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(decodedIv);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        byte[] decryptedData = cipher.doFinal(decodedData);

        log.info("data:{}",new String(decryptedData));
        return ResponseEntity.ok(true);
    }

    public static String decryptData(String encryptedData, String key, String iv) throws Exception {
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes("UTF-8"));
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }	
}
