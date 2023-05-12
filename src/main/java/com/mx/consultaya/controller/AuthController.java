package com.mx.consultaya.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mx.consultaya.exception.CustomException;
import com.mx.consultaya.model.EncryptedData;
import com.mx.consultaya.service.AuthService;

import jakarta.mail.MessagingException;
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
		try {
			authService.signIn(encryptedData);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("{\"message\": \"Ocurrio un problema inseperado, intente nuevamente más tarde.\"}");
		}
		return ResponseEntity.status(HttpStatus.OK).body("Acceso exitoso");
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

	@CrossOrigin(origins = "*")
	@PostMapping(path = "refreshToken")
	public ResponseEntity<?> refreshToken(@RequestBody String id) throws CustomException, UnsupportedEncodingException, MessagingException {
			try {
				log.info("id"+id);
				authService.refreshToken(id);
		} catch (Exception e) {
			log.info("error " + e.getMessage());
			return new ResponseEntity<>(
					"Ocurrio un error inesperado, intentalo más tarde ",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(
				"se actualizo correctamente.",
				HttpStatus.OK);

	}

}
