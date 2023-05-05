package com.mx.consultaya.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EncryptedData {
    private String data;
    private String key;
    private String iv;
}
