package com.example.RaquetZone.exception;

public class EmpresaNotFoundException extends RuntimeException {

    public EmpresaNotFoundException() {
        super();
    }

    public EmpresaNotFoundException(String message) {
        super(message);
    }

    public EmpresaNotFoundException(long id) {
        super("Empresa not found: " + id);
    }
}
