package com.mx.consultaya.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mx.consultaya.model.EncryptedData;
import com.mx.consultaya.model.JWTGenerator;
import com.mx.consultaya.model.Usuario;
import com.mx.consultaya.service.UserService;
import com.mx.consultaya.utils.Utils;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class RegistroController {

	private UserService userService;

	/**
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@CrossOrigin(origins = "*")
	@PostMapping(path = "signUp")
	public ResponseEntity<String> saveUser(@RequestBody @Valid EncryptedData encryptedData) throws Exception {
		String data = encryptedData.getData();
		String key = encryptedData.getKey();
		String iv = encryptedData.getIv();

		String dataDecrypt = Utils.decryptData(data, key, iv);

		log.info("dataDecrypt {}", dataDecrypt);
		Gson g = new Gson();

		Usuario user = g.fromJson(dataDecrypt, Usuario.class);

		try {
			log.info("existe correo" + userService.findUserByEmail(user.getCorreoElectronico().toLowerCase()));
			if (userService.findUserByEmail(user.getCorreoElectronico().toLowerCase())) {
				log.info("user email {} ", user.getCorreoElectronico());

				return new ResponseEntity<>("El correo electrónico proporcionado ya está dado de alta",
						HttpStatus.ALREADY_REPORTED);
			} else {

				Usuario createdUser = userService.saveUsuario(user);

				log.info("user id 2: {} ", createdUser.getId());

				log.info("usuario {}", createdUser);

				userService.enviarCorreoVerificacion(createdUser);
				
				log.info("Enviar correo: ");

				return new ResponseEntity<>(
						"Registro exitoso. Te llegará un correo electrónico para la verificación de tu cuenta ",
						HttpStatus.CREATED);
			}
		} catch (Exception e) {
			log.info("exception\n" + e.getMessage());
			return new ResponseEntity<>("Ocurrio un error inesperado", HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@CrossOrigin(origins = "*")
	@PostMapping(path = "verifyEmail")
	public ResponseEntity<String> verifyEmail(@RequestBody String jwt) throws Exception {
		try {
			Gson g = new Gson();
			JsonObject jwtString = g.fromJson(jwt, JsonObject.class);
			String token = jwtString.get("jwt").getAsString();
			DecodedJWT decoder = new JWTGenerator().decriptJWT(token);
			String email = decoder.getClaim("correoElectronico").asString();
			Boolean verif = userService.actualizarVerificacion(email);
			if(Boolean.TRUE.equals(verif)){
				return new ResponseEntity<>(
						"Tu cuenta ha sido verificada correctamente ",
						HttpStatus.OK);
	
			}
			else{
				return new ResponseEntity<>(
						"No se pudo verificar tu cuenta, intentalo más tarde ",
						HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			log.info("" + e.getMessage());
			return new ResponseEntity<>(
						"Ocurrio un error inesperado, intentalo más tarde ",
						HttpStatus.INTERNAL_SERVER_ERROR);
	
		}

		}

}
