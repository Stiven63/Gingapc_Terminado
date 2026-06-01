package com.gingapc.pago.exception;

public class PagoNotFoundException extends RuntimeException {

    public PagoNotFoundException(String mensaje) {
        super(mensaje);
    }
}
