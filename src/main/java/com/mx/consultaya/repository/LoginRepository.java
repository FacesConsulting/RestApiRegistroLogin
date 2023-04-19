package com.mx.consultaya.repository;

import org.springframework.stereotype.Repository;

import com.mx.consultaya.model.Usuario;

import java.util.*;

@Repository
public interface LoginRepository {

	public List<Usuario> findAll();
	public Usuario login(String email, String password); 
	public Usuario saveLogin(Usuario user);
}
