package com.mx.consultaya.model.request;
import java.util.Set;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class SignupRequest{
	
	@NotBlank
  	@Size(min=8, max = 20)
	private String nombre;

	@NotBlank
  	@Size(max = 20)
	private String apellidos;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
  	@Size(min=8,max = 16)
	private String password;

	private Set<String> roles;

	}
