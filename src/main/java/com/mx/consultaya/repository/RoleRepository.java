package com.mx.consultaya.repository;

import java.util.Optional;
import com.mx.consultaya.model.ERole;
import com.mx.consultaya.model.Role;

public interface RoleRepository/* extends MongoRepository<Role,String>  */{

    Optional<Role> findByNamERole(ERole namERole);
}
