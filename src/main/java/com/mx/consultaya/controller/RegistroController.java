package com.mx.consultaya.controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
	private PasswordEncoder passwordEncoder;
    @PostMapping(path = "signUp")
	public ResponseEntity<Usuario> saveUser(@RequestBody @Valid Usuario user){
		if(loginService.findByUsername(user.getNombre())){
			return new ResponseEntity<Usuario>(HttpStatus.ALREADY_REPORTED);
		}
		//Usuario us = new Usuario(null, null, null, null, null, null, null);
		//	us.setPassword(passwordEncoder.encode(user.getPassword()));
		log.info("guarda usuario {}",user.toString());
		return ResponseEntity.ok(loginService.saveUsuario(user));
	}

}
