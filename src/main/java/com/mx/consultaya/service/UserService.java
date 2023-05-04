package com.mx.consultaya.service;

import com.mx.consultaya.model.Usuario;

public interface UserService {

    public Usuario saveUsuario(Usuario user);   

    public boolean findUserByEmail(String email);
    
}
