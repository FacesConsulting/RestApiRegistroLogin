package com.mx.consultaya.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.mx.consultaya.model.EncryptedData;
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
		log.info("registrando usuario");
		String data = encryptedData.getData();
		String key = encryptedData.getKey();
		String iv = encryptedData.getIv();

		log.info("Data: " + data);
		log.info("Key: " + key);
		log.info("iv: " + iv);
		String dataDecrypt = Utils.decryptData(data, key, iv);
<<<<<<< HEAD
			log.info("dataDecrypt {}",dataDecrypt);
			Gson g = new Gson();
			
			Usuario user = g.fromJson(dataDecrypt, Usuario.class);
			log.info("user data {} ", user.getCorreoElectronico());
		
		try {
			log.info("existe correo" + userService.findUserByEmail(user.getCorreoElectronico()));
			if(userService.findUserByEmail(user.getCorreoElectronico())){
=======
		log.info("dataDecrypt {}", dataDecrypt);
		Gson g = new Gson();

		Usuario user = g.fromJson(dataDecrypt, Usuario.class);
		log.info("user data {} ", user.getCorreoElectronico().toLowerCase());

		try {
			log.info("existe correo" + userService.findUserByEmail(user.getCorreoElectronico().toLowerCase()));
			if (userService.findUserByEmail(user.getCorreoElectronico().toLowerCase())) {
>>>>>>> b201956fa179d5fb4ebdac17940d4933e7fc9d77
				log.info("user email {} ", user.getCorreoElectronico());
				return new ResponseEntity<>("El correo electrónico proporcionado ya está dado de alta",
						HttpStatus.ALREADY_REPORTED);
			} else {
				log.info("Enviar correo: ");
				log.info("guarda usuario {}", user.toString());
				userService.saveUsuario(user);
				log.info(user.getCodigoVerificacion());
				try {
					userService.enviarCorreoVerificacion(user);
					return new ResponseEntity<>(
							"Registro exitoso. Te llegará un correo electrónico para la verificación de tu cuenta ",
							HttpStatus.CREATED);
				} catch (Exception e) {
					log.info(e.getMessage());
					return new ResponseEntity<>("No se pudo validar su email", HttpStatus.NOT_ACCEPTABLE);
				}
			}
		} catch (Exception e) {
			log.info("exception\n" + e.getMessage());
			return new ResponseEntity<>("ocurrio un error inesperado", HttpStatus.NOT_ACCEPTABLE);
		}
	}

}
