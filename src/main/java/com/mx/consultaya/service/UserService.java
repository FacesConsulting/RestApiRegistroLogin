package com.mx.consultaya.service;

import java.io.UnsupportedEncodingException;

import com.mx.consultaya.model.Usuario;

import jakarta.mail.MessagingException;

public interface UserService {

    public Usuario saveUsuario(Usuario user);   

    public boolean findUserByEmail(String email);

    public void enviarCorreoVerificacion(Usuario user) throws UnsupportedEncodingException, MessagingException;

    public Boolean actualizarVerificacion(String email);
    
}
