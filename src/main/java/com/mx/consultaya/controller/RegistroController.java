package com.mx.consultaya.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mx.consultaya.model.Usuario;
import com.mx.consultaya.service.LoginService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/register")
public class RegistroController {
	private LoginService loginService;
	
	/**
	 * @param user
	 * @return
	 */
	@PostMapping(path = "signUp")
	public ResponseEntity<Usuario> saveUser(@RequestBody @Valid Usuario user) {
		if(loginService.findUserByEmail(user.getEmail())){
			return new ResponseEntity<Usuario>(HttpStatus.ALREADY_REPORTED);
			
		}
		try {
			Usuario usuario = loginService.saveUsuario(user);
			if (usuario.getNombre() == null) {
				log.info("null");
				return new ResponseEntity<>(HttpStatusCode.valueOf(406));
			} else {
				log.info("guarda usuario {}", user.toString());
				return ResponseEntity.ok(loginService.saveUsuario(user));
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatusCode.valueOf(406));
		}
	}

}
