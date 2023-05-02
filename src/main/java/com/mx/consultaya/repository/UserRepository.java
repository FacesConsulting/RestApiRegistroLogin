package com.mx.consultaya.repository;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.mx.consultaya.model.Usuario;

public interface UserRepository extends MongoRepository<Usuario,String> {
    
    Optional<Usuario> findByUsername(String nombre);

    Boolean existsByUsername(String nombre);

    Boolean existsByEmail(String email);
}
