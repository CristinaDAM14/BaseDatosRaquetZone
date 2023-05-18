package com.example.RaquetZone.exception;

public class Producto_CompraNotFoundException extends RuntimeException{
    public Producto_CompraNotFoundException() {
        super();
    }

    public Producto_CompraNotFoundException(String message) {
        super(message);
    }

    public Producto_CompraNotFoundException(long id) {
        super("Producto_compra not found: " + id);
    }
}
