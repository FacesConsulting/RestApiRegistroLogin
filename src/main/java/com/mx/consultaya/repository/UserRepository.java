package com.mx.consultaya.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import com.mx.consultaya.model.Usuario;

//@Repository
public interface UserRepository extends MongoRepository<Usuario,String> {
    
    Optional<Usuario> findByUsername(String nombre);

    Boolean existsByUsername(String nombre);

    Boolean existsByEmail(String email);
}
