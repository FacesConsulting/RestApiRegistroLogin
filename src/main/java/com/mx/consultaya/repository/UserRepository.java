package com.mx.consultaya.repository;
import java.io.UnsupportedEncodingException;

import com.mx.consultaya.model.Usuario;

import jakarta.mail.MessagingException;

public interface UserRepository{

    Usuario saveUsuario(Usuario usuario);
  
    Boolean findByEmail(String email);

    Boolean actualizarVerificacion(String email);

    void enviarCorreoVerificacion(Usuario user) throws UnsupportedEncodingException, MessagingException;

}
