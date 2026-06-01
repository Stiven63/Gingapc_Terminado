package com.gingapc.pedido.exception;

public class PedidoNotFoundException extends RuntimeException {

    public PedidoNotFoundException(String mensaje) {
        super(mensaje);
    }
}
