package com.mx.consultaya.security;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mx.consultaya.model.Usuario;
import com.mx.consultaya.repository.LoginRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService{
        @Autowired
        private LoginRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Usuario user = userRepository.findByUsername(username);
            if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
            }
            return new org.springframework.security.core.userdetails.User(user.getNombre(), user.getPassword(),
                new ArrayList<>());
        }
    
}
