package com.mx.consultaya.controller;

import org.springframework.http.HttpStatus;
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
@RequestMapping("/auth")
public class LoginController {
	
	private LoginService loginService;
	
	@PostMapping(path = "registar")
	public ResponseEntity<Usuario> saveUser(@RequestBody @Valid Usuario user){
		log.info("guarda usuario {}",user.toString());
		return ResponseEntity.ok(loginService.saveUsuario(user));
	}
	
	@PostMapping(path = "login")
	public ResponseEntity<Object> loggearUser(@RequestBody @Valid Usuario user){
		log.info("loggeando usuario");
		log.info(user.getEmail()+ " " + user.getPassword());
		Usuario usuario = loginService.loggearUsuario(user);
		log.info(usuario != null ? "El usuario existe este es su email: " + usuario.getEmail() : null);
		if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Credenciales invalidas\"}");
        } else {
            return ResponseEntity.ok(usuario);
        }
		
	}
}
