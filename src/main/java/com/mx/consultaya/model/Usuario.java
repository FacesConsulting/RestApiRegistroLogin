package com.mx.consultaya.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Document("Usuario")
public class Usuario{
	
	@Id
	private String id;

	@NotBlank
  	@Size(max = 20)
	private String nombre;

	@NotBlank
  	@Size(max = 20)
	private String apellidos;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
  	@Size(max = 16)
	private String password;

	@DBRef
	private Set<Role> roles = new HashSet<>();

	}
