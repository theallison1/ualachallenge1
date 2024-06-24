package com.challenge.uala.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("El ID " + id + " no corresponde a un usuario");
    }
}
