package com.mx.consultaya.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
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
	private String firstname;

	@NotBlank
	@Size(max = 20)
	private String lastname;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotNull
	@Size(min =8, max = 20)
	private String password;
	//md5
	private Boolean terminos;

	private Boolean politicas;
	
	public Usuario(String firstname, String lastname, String email, String password, Boolean terminos,
			Boolean politicas) {
				this.firstname = firstname;
				this.lastname = lastname;
				this.email = email;
				this.password = password;
				this.terminos = terminos;
				this.politicas = politicas;
	}

	@DBRef
  	private Set<Role> roles = new HashSet<>();
}
