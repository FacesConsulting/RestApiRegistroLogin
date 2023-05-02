package com.mx.consultaya.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.mx.consultaya.model.EncryptedData;
import com.mx.consultaya.model.Usuario;
import com.mx.consultaya.model.response.JwtResponse;
import com.mx.consultaya.repository.RoleRepository;
import com.mx.consultaya.repository.UserRepository;
import com.mx.consultaya.service.LoginService;
import com.mx.consultaya.service.impl.UserDetailsImpl;
import com.mx.consultaya.utils.JwtUtils;
import com.mx.consultaya.utils.Utils;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class LoginController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	private LoginService loginService;
	
	@PostMapping(path = "signIn")
	public ResponseEntity<JwtResponse> singIn(@RequestBody @Valid EncryptedData encryptedData) throws Exception{
		log.info("loggeando usuario");
		String data = encryptedData.getData();
		String key = encryptedData.getKey();
		String iv = encryptedData.getIv();

		log.info("Data: " + data);
		log.info("Key: " + key);
		log.info("iv: " + iv);

		//try {
			
			String dataDecrypt = Utils.decryptData(data, key, iv);

			Gson g = new Gson();
			
			Usuario user = g.fromJson(dataDecrypt, Usuario.class);
			// JWT
			Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

				SecurityContextHolder.getContext().setAuthentication(authentication);
				String jwt = jwtUtils.generateJwtToken(authentication);
				
				UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
				List<String> roles = userDetails.getAuthorities().stream()
						.map(item -> item.getAuthority())
						.collect(Collectors.toList());

			//log.info(user.getEmail()+ " " + user.getPassword());

			//Usuario usuario = loginService.loggearUsuario(user);

			//log.info(usuario != null ? "El usuario existe este es su email: " + usuario.getEmail() : null);

			//if (usuario == null) {
			//    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Credenciales invalidas\"}");
			//} else {
			//    return ResponseEntity.ok(usuario);
			//}
		//} catch (Exception e) {
			// TODO: handle exception
		//	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Credenciales invalidas\"}");
		//}	
		return ResponseEntity.ok(new JwtResponse(jwt,
												 userDetails.getId(), 
												 userDetails.getUsername(),
												 userDetails.getApellidos(), 
												 userDetails.getEmail(), 
												 roles));
	}
}