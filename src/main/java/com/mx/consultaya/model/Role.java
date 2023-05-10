package com.mx.consultaya.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@Document(collection ="Roles")
public class Role {
    @Id
    private String id;
    @NotNull
    private ERole namERole;
}
