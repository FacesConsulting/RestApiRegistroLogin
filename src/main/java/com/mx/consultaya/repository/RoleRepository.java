package com.mx.consultaya.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mx.consultaya.model.ERole;
import com.mx.consultaya.model.Role;
@Repository
public interface RoleRepository extends MongoRepository<Role,String> {
    Optional<Role> findByName(ERole namERole);
}
