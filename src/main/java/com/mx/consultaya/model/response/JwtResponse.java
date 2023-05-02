package com.mx.consultaya.model.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class JwtResponse {
    private String token;
	private String type = "Bearer";
	private String id;
	private String nombre;
    private String apellidos;
	private String email;
	private List<String> roles;

	public JwtResponse(String accessToken, String id, String nombre,String apellidos, String email, List<String> roles) {
		this.token = accessToken;
		this.id = id;
		this.nombre = nombre;
		this.apellidos= apellidos;
		this.email = email;
		this.roles = roles;
	}
}
