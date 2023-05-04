package com.mx.consultaya.repository.impl;

import java.util.Optional;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mx.consultaya.model.Usuario;
import com.mx.consultaya.repository.UserRepository;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class UserRepositoryImp implements UserRepository{
    private MongoTemplate mongoTemplate;
    
   

    @Override
    public Usuario saveLogin(Usuario user) {
        mongoTemplate.insert(user);
        return user;
    }

    @Override
    public Boolean existsByEmail(String email){
        Query query = new Query();
        query.addCriteria(Criteria.where("email").is(email));
        return mongoTemplate.exists(query, Usuario.class);
    }


}
