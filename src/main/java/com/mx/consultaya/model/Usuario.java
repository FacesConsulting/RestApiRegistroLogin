package com.mx.consultaya.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Encrypted;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nonapi.io.github.classgraph.json.Id;
/**
 * The Class LoginInput
 * @author 
 *
 */
@Setter
@Getter
@AllArgsConstructor
@Document("Usuario")
public class Usuario {
	
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

	@NotNull
	@Size(min =8, max = 20)
	private String password;
	//md5
	private Boolean terminosCondiciones;

	private Boolean politicaPrivacidad;
	
	//private Role role;
}
