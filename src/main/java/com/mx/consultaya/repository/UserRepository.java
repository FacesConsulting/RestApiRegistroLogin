package com.mx.consultaya.repository;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.mx.consultaya.model.Usuario;

public interface UserRepository/*  extends MongoRepository<Usuario,String> */{
    //Optional<Usuario> findByUsername(String username);

    Boolean existsByUsername(String username);
  
    Boolean existsByEmail(String email);
}
