package com.mx.consultaya.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.mx.consultaya.model.EncryptedData;
import com.mx.consultaya.model.Usuario;
import com.mx.consultaya.service.LoginService;
import com.mx.consultaya.utils.Utils;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class LoginController {
	
	private LoginService loginService;
	
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
			
			String dataDecrypt = Utils.decryptData(data, key, iv);

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
}