package com.gingapc.pedido.dto;

import com.gingapc.pedido.model.DireccionEntrega;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class CrearPedidoRequest {

    @NotNull(message = "El usuarioId es obligatorio")
    private Long usuarioId;

    // id del pago que se hizo previamente, tiene que estar APROBADO
    @NotNull(message = "El pagoId es obligatorio")
    private Long pagoId;

    // @Valid para que tambien valide los campos de la direccion
    @NotNull(message = "La direccion de entrega es obligatoria")
    @Valid
    private DireccionEntrega direccion;

    public CrearPedidoRequest() {}

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public Long getPagoId() { return pagoId; }
    public void setPagoId(Long pagoId) { this.pagoId = pagoId; }

    public DireccionEntrega getDireccion() { return direccion; }
    public void setDireccion(DireccionEntrega direccion) { this.direccion = direccion; }
}
