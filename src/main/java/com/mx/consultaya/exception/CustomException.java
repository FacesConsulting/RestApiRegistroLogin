package com.mx.consultaya.exception;

public class CustomException extends Exception {

    /**
     * Crea una nueva instancia de CustomException con el mensaje de error
     * especificado
     * y la causa subyacente.
     *
     * @param message el mensaje de error que se va a asociar con esta excepción.
     * @param cause   la causa subyacente de esta excepción.
     */
    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
