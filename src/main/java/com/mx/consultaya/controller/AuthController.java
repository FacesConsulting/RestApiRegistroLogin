package com.mx.consultaya.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.mx.consultaya.exception.CustomException;
import com.mx.consultaya.model.EncryptedData;
import com.mx.consultaya.model.Usuario;
import com.mx.consultaya.service.AuthService;
import com.mx.consultaya.utils.Utils;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	private AuthService authService;

	@PostMapping(path = "signIn")
	public ResponseEntity<Object> singIn(@RequestBody @Valid EncryptedData encryptedData) {
		String data = encryptedData.getData();
		String key = encryptedData.getKey();
		String iv = encryptedData.getIv();

		try {

			String dataDecrypt = Utils.decryptData(data, key, iv);

			Gson g = new Gson();

			Usuario user = g.fromJson(dataDecrypt, Usuario.class);

			Usuario us = authService.signIn(user.getCorreoElectronico().toLowerCase(), user.getPassword());

			if (us == null) {
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

	@CrossOrigin(origins = "*")
	@PostMapping(path = "signUp")
	public ResponseEntity<?> signUp(@RequestBody @Valid EncryptedData encryptedData) {
		try {
			authService.signUp(encryptedData);
		} catch (CustomException e) {
			log.info("exception\n" + e.getMessage());
			return new ResponseEntity<>("Ocurrio un error inesperado", HttpStatus.CONFLICT);
		}

		return new ResponseEntity<>(
				"Registro exitoso. Te llegará un correo electrónico para la verificación de tu cuenta ",
				HttpStatus.CREATED);
	}

	@CrossOrigin(origins = "*")
	@PostMapping(path = "verifyEmail")
	public ResponseEntity<?> verifyAccount(@RequestBody String jwt) {
		try {
			authService.verifyMail(jwt);
		} catch (Exception e) {
			log.info("error " + e.getMessage());
			return new ResponseEntity<>(
					"Ocurrio un error inesperado, intentalo más tarde ",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(
				"Correo verificado correctamente.",
				HttpStatus.OK);

	}

}
