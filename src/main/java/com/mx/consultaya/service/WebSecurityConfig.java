package com.mx.consultaya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.mx.consultaya.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration

public class WebSecurityConfig{

   // private final UserRepository repository;
    
   /*  @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }*/
   /* @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        //authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    } */
}
