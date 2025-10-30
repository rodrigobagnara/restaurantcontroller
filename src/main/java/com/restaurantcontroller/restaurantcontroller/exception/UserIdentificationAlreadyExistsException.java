package com.restaurantcontroller.restaurantcontroller.exception;

public class UserIdentificationAlreadyExistsException extends RuntimeException {
    
    public UserIdentificationAlreadyExistsException(String userIdentification) {
        super("Identificação do usuário já está em uso: " + userIdentification);
    }
    
    public UserIdentificationAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
