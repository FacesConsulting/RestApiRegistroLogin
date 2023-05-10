package com.mx.consultaya.exception;

import com.mx.consultaya.utils.EnumSeverity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorRestLogin {
    private EnumSeverity severity;
    private String title;
    private String message;
}
