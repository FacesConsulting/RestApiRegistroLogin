package com.mx.consultaya.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.backoff.FixedBackOff;
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

	private UserService  userService;

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
			if(userService.findUserByEmail(user.getEmail())){
				log.info("user email {} ", user.getEmail());
				
				return new ResponseEntity<>("El email proporcionado ya esta dado de alta en la aplicación",HttpStatus.ALREADY_REPORTED);
			}
			else{
				log.info("Enviar correo: ");
				BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
				String encryptedPwd = bcrypt.encode(user.getPassword());
				user.setPassword(encryptedPwd);
				final Usuario userF = new Usuario(
							 user.getFirstname(),  
							 user.getLastname(),
							 user.getEmail(),
							 user.getPassword(),
							 user.getTerminos(),
							 user.getPoliticas());
				log.info("guarda usuario {}", userF.toString());

				return ResponseEntity.ok(userService.saveUsuario(user));
			}
			
		} catch (Exception e) {
			log.info("exception\n" +  e.getMessage());
			return new ResponseEntity<>(HttpStatusCode.valueOf(406));
		}
	}

}
