package com.mx.consultaya.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mx.consultaya.model.Usuario;
import com.mx.consultaya.repository.UserRepository;
import com.mx.consultaya.service.UserService;
import com.mx.consultaya.utils.RandomString;
import com.mx.consultaya.utils.Utils;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@AllArgsConstructor
public class UserServiceImp implements UserService {

    private UserRepository userRepository;

    @Override
    public Usuario saveUsuario(Usuario user) {
        String encryptedPwd = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12));
        user.setPassword(encryptedPwd);
        user.setRol("Paciente");
<<<<<<< HEAD
        user.setVerificado(false);
=======
        String randomCode = Utils.verifyToken(user);

        user.setCodigoVerificacion(randomCode);
        user.setVerificado(false);
        user.setCreadoEn(new Date());

>>>>>>> b201956fa179d5fb4ebdac17940d4933e7fc9d77
        log.info("Guarda usuario: {}", user.toString());
        return userRepository.saveUsuario(user);
    }

    @Override
    public boolean findUserByEmail(String email) {
        return userRepository.findByEmail(email);

    }

    @Override
    public boolean isVerified() {
        return userRepository.isVerified();
    }

    @Override
    public void enviarCorreoVerificacion(Usuario user) throws UnsupportedEncodingException, MessagingException {
        userRepository.enviarCorreoVerificacion(user);
    }
}
