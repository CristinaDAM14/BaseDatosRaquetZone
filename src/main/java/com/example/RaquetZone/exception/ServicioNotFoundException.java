package com.example.RaquetZone.exception;

public class ServicioNotFoundException extends RuntimeException{

    public ServicioNotFoundException() {
        super();
    }

    public ServicioNotFoundException(String message) {
        super(message);
    }

    public ServicioNotFoundException(long id) {
        super("Servicio no encontrado: " + id);
    }
}
