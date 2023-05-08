package com.mx.consultaya.service.impl;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mx.consultaya.model.Usuario;
import com.mx.consultaya.repository.UserRepository;
import com.mx.consultaya.service.UserService;
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
        user.setVerificado(false);
        log.info("Guarda usuario: {}", user.toString());
        return userRepository.saveUsuario(user);
    }

    @Override
    public boolean findUserByEmail(String email) {
        return userRepository.findByEmail(email);

    }
}
