package com.mx.consultaya.model;

import java.util.Collection;
import java.util.List;

import org.springframework.context.annotation.Role;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import nonapi.io.github.classgraph.json.Id;
/**
 * The Class LoginInput
 * @author 
 *
 */
@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@Document("Usuario")

public class Usuario {

	
	@Id
	private String id;
	
	private String email;

	private String password;

	
}
