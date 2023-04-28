package com.mx.consultaya.model;

import java.util.Collection;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class Usuario implements UserDetails {
	
	@Id
	private String id;

	private String nombre;
	private String apellidos;
	private String email;
	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}
	@Override
	public String getPassword() {
		return password;
	}
	
	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;	
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;	
	}
	@Override
	public boolean isEnabled() {
		return true;	
	}

}
