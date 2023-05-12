package com.mx.consultaya.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The Class LoginInput
 * 
 * @author
 *
 */
@Setter
@Getter
@AllArgsConstructor
@ToString
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
	private String correoElectronico;

	@NotNull
	@Size(min = 8, max = 20)
	private String password;

	@NotNull
	private Boolean terminos;

	@NotNull
	private Boolean politicas;

	@NotNull
	private Boolean verificado;

	@NotNull
	private String codigoVerificacion;
	private Date creadoEn;
	private String rol;
}
