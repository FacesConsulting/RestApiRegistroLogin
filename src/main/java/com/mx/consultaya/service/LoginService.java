package com.mx.consultaya.service;
import java.util.List;
import com.mx.consultaya.model.Usuario;

public interface LoginService {
	
	public List<Usuario> findAll();
	
	public Usuario loggearUsuario(Usuario user);

	public boolean findUserByEmail(String email);
}
