package com.mx.conectasalud.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mx.conectasalud.service.ClinicaService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {
	
	private ClinicaService clinicaService;
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/login")
	public LoginResponse getLogin( final @RequestParam(email="",required = true) final @RequestParam(password=" ",required = true)) {
		return "all";
	}

	@PostMapping
	public String create(@RequestBody String test) {
		log.info("test");
		return test;
	}
}
