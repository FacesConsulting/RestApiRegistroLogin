package com.mx.consultaya.model;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Document("roles")
public class Role {
    @Id
    private String id;
    
    private ERole nameRole;


}
