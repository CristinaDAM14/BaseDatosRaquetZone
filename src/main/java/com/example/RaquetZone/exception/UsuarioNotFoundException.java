package com.example.RaquetZone.exception;

public class UsuarioNotFoundException extends RuntimeException{

    public UsuarioNotFoundException() {
        super();
    }

    public UsuarioNotFoundException(String message) {
        super(message);
    }

    public UsuarioNotFoundException(long id) {
        super("Usuario no encontrado: " + id);
    }
}
