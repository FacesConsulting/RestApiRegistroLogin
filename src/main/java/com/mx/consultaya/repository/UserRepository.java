package com.mx.consultaya.repository;
import com.mx.consultaya.model.Usuario;

public interface UserRepository{

    Usuario saveLogin(Usuario usuario);
  
    Boolean existsByEmail(String email);

}
