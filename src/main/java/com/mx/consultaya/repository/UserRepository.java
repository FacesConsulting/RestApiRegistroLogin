package com.mx.consultaya.repository;
import com.mx.consultaya.model.Usuario;

public interface UserRepository{

    Usuario findByEmail(String email);

    Usuario saveUsuario(Usuario usuario);
  
    Boolean existsByEmail(String email);

}
