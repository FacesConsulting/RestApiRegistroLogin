package com.mx.consultaya.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mx.consultaya.model.ERole;
import com.mx.consultaya.model.Role;

public interface RoleRepository extends MongoRepository<Role,String> {

    Role findByNamERole(ERole namERole);
}
