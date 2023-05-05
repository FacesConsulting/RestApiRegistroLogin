package com.mx.consultaya.repository;
import com.mx.consultaya.model.Usuario;

public interface UserRepository{

    Usuario saveUsuario(Usuario usuario);
  
    Boolean findByEmail(String email);

}
