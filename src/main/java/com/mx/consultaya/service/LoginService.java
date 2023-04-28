package com.mx.consultaya.service;
import java.util.List;
import com.mx.consultaya.model.Usuario;

public interface LoginService {
	
	public List<Usuario> findAll();

	public Usuario saveUsuario(Usuario user);
	
	public boolean findByUsername(String nombre);

	public Usuario loggearUsuario(Usuario user);
}
