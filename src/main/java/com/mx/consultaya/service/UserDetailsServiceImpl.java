package com.mx.consultaya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mx.consultaya.model.Usuario;
import com.mx.consultaya.repository.UserRepository;
import com.mx.consultaya.service.impl.UserDetailsImpl;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String nombre) throws UsernameNotFoundException {
        Usuario user = userRepository.findByUsername(nombre)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + nombre));

		return UserDetailsImpl.build(user);
    
    }
    
}
