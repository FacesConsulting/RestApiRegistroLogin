package com.mx.consultaya.model;

import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;
import nonapi.io.github.classgraph.json.Id;

@Setter
@Getter
@Document("roles")
public class Role {
    @Id
    private String id;

    private ERole namERole;
}
