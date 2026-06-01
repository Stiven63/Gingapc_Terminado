package com.gingapc.carrito.exception;

public class CarritoNotFoundException extends RuntimeException {

    public CarritoNotFoundException(String mensaje) {
        super(mensaje);
    }
}
