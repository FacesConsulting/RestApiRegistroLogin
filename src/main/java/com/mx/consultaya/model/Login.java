package com.mx.consultaya.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Login {
    
    private String email;
    private String password;
//    private Boolean enabled;
}
