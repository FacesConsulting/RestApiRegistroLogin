package com.mx.consultaya.repository;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.mx.consultaya.model.ERole;
import com.mx.consultaya.model.Role;

public interface RoleRepository extends MongoRepository<ERole,String>{
    Optional<Role> findByName(ERole name);    
}
