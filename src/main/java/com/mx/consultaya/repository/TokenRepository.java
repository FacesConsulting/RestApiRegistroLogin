package com.mx.consultaya.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mx.consultaya.token.Token;

public interface TokenRepository extends MongoRepository<Token,String>{
   
    List<Token> findAllValidTokenByUser(Integer id);

    Optional<Token> findByToken(String token);
}
