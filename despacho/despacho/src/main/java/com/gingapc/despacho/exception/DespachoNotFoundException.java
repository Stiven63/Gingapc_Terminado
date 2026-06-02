package com.gingapc.despacho.exception;

public class DespachoNotFoundException extends RuntimeException {

    public DespachoNotFoundException(String mensaje) {
        super(mensaje);
    }
}
