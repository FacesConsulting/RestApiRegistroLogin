package com.mx.consultaya.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.mx.consultaya.model.Usuario;

@Repository
public interface UserRepository extends MongoRepository<Usuario,String> {
    public Usuario findUserByEmail(String nombre);
}
