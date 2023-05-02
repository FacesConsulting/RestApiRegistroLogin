package com.mx.consultaya.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mx.consultaya.model.Usuario;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

	private String id;

	private String nombre;

    private String apellidos;

	private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(String id, String nombre,String apellidos, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.nombre = nombre;
        this.apellidos=apellidos;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	public static UserDetailsImpl build(Usuario user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getNameRole().name()))
				.collect(Collectors.toList());

		return new UserDetailsImpl(
				user.getId(), 
				user.getNombre(), 
                user.getApellidos(),
				user.getEmail(),
				user.getPassword(), 
				authorities);
	}
    @Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

    public String getEmail(){
        return email;
    }

    @Override
    public String getPassword() {
        return password;    
    }

    @Override
    public String getUsername () {
        return nombre;
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
