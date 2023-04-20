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
@RequestMapping("/login")
public class LoginController {
	
	private LoginService loginService;
	
	@PostMapping(path = "guardar")
	public ResponseEntity<Usuario> saveUser(@RequestBody @Valid Usuario user){
		log.info("guarda usuario {}",user.toString());
		return ResponseEntity.ok(loginService.saveUsuario(user));
	}
	
	@PostMapping(path = "loggear")
	public ResponseEntity<Usuario> loggearUser(@RequestBody @Valid Usuario user){
		log.info("loggeando usuario");
		Usuario usuario = loginService.loggearUsuario(user);
		if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            return ResponseEntity.ok(usuario);
        }
		
	}
}
