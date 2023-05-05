package com.mx.consultaya.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
public class RegistroController {
	private LoginService loginService;
	
	/**
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@CrossOrigin(origins = "*")
	@PostMapping(path = "signUp")
	public ResponseEntity<?> saveUser(@RequestBody @Valid EncryptedData encryptedData) throws Exception {
		log.info("registrando usuario");
		String data = encryptedData.getData();
		String key = encryptedData.getKey();
		String iv = encryptedData.getIv();

		log.info("Data: " + data);
		log.info("Key: " + key);
		log.info("iv: " + iv);
		String dataDecrypt = Utils.decryptData(data, key, iv);
			log.info("dataDecrypt {}",dataDecrypt);
			Gson g = new Gson();
			
			Usuario user = g.fromJson(dataDecrypt, Usuario.class);
			log.info("user data {} ", user.getEmail());
		
		try {
			Usuario usuario = loginService.saveUsuario(user);
			if(loginService.findUserByEmail(usuario.getEmail())){
				return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
				
			}
			if (usuario.getFirstname() == null) {
				log.info("null");
				return new ResponseEntity<>(HttpStatusCode.valueOf(406));
			} else {
				log.info("guarda usuario {}", user.toString());
				return ResponseEntity.ok(loginService.saveUsuario(user));
			}
		} catch (Exception e) {
			log.info("exception");
			return new ResponseEntity<>(HttpStatusCode.valueOf(406));
		}
	}

}
