package com.mx.consultaya.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	public ResponseEntity<Object> singIn(@RequestBody @Valid EncryptedData encryptedData) {
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
			
			Usuario us = loginService.loggearUsuario(user.getCorreoElectronico().toLowerCase(), user.getPassword());
			
			if(us == null){
				log.info("Usuario no existente");
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"message\": \"Usuario no existente\"}");
			}
			
			BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
			boolean isPasswordMatches = bcrypt.matches(user.getPassword(), us.getPassword());

			if (isPasswordMatches) { // correct password
				if (Boolean.TRUE.equals(us.getVerificado())) {

					return ResponseEntity.status(HttpStatus.OK).body(us);
				} else {
					log.info("Usuario no verificado");
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
							.body("{\"message\": \"Usuario no verificado.\"}");
				}
			} else {
				log.info("Credenciales incorrectas");
				return ResponseEntity.status(HttpStatus.CONFLICT).body("{\"message\": \"Credenciales invalidas\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{\"message\": \"Ocurrio un problema inseperado, intente nuevamente más tarde.\"}");
		}
	}
}