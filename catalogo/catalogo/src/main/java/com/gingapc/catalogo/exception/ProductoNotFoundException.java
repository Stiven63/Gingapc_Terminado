package com.gingapc.catalogo.exception;

// excepcion personalizada cuando no se encuentra un producto
public class ProductoNotFoundException extends RuntimeException {

    public ProductoNotFoundException(String mensaje) {
        super(mensaje);
    }
}
