package com.gingapc.pago.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PagoRequest {

    @NotNull(message = "El usuarioId es obligatorio")
    private Long usuarioId;

    // el metodo de pago puede ser WEBPAY, TRANSFERENCIA o EFECTIVO
    @NotBlank(message = "El metodo de pago es obligatorio")
    private String metodoPago;

    public PagoRequest() {}

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
}
