package com.example.RaquetZone.exception;

public class CompraNotFoundException extends RuntimeException{

    public CompraNotFoundException() {
        super();
    }

    public CompraNotFoundException(String message) {
        super(message);
    }

    public CompraNotFoundException(long id) {
        super("Compra not found: " + id);
    }
}
