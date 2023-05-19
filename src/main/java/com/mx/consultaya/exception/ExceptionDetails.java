package com.mx.consultaya.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionDetails {
    private String userMessage;
    private String severity;

}
