package com.mx.consultaya.model;

import org.springframework.data.mongodb.core.mapping.Document;

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

	private String nombre;
	private String apellidos;
	private String email;
	private String password;
	private Boolean terminosCondiciones;
	private Boolean politicaPrivacidad;
}
