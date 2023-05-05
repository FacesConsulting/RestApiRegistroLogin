package com.mx.consultaya.service;
import java.util.List;
import com.mx.consultaya.model.Usuario;

public interface LoginService {
	
	public List<Usuario> findAll();
	
	public Usuario loggearUsuario(String email,String password);

	public boolean existUserByEmail(String email);

    public Usuario findByEmail(String email);
}
