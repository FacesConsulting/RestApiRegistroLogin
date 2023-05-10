package com.mx.consultaya.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mx.consultaya.model.Usuario;
import com.mx.consultaya.repository.LoginRepository;

import com.mx.consultaya.service.LoginService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class LoginServiceImp  implements LoginService{
	private LoginRepository loginRepository;
	
	@Override
	public List<Usuario> findAll() {
		log.info("Obteniendo todos los usuarios");
		return loginRepository.findAll();
	}

	@Override
	public Usuario loggearUsuario(String email,String password){
		
		return loginRepository.login(email,password );
	}
	
	@Override
	public boolean existUserByEmail(String email){
		return loginRepository.existUserByEmail(email);
	}
	@Override
    public Usuario findByEmail(String email) {
      return loginRepository.findByEmail(email);
    }
	

}