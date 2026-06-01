package com.gingapc.pago.dto;

import java.math.BigDecimal;

// dto que recibe la respuesta del carrito-service via feign
public class CarritoResponse {

    private Long id;
    private Long usuarioId;
    private BigDecimal subtotal;
    private BigDecimal iva;
    private BigDecimal total;

    public CarritoResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public BigDecimal getIva() { return iva; }
    public void setIva(BigDecimal iva) { this.iva = iva; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
}
