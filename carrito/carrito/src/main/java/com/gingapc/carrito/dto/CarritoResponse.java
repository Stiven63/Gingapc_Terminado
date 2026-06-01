package com.gingapc.carrito.dto;

import com.gingapc.carrito.model.ItemCarrito;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

// este dto calcula el subtotal, iva y total del carrito
// el iva en chile es el 19%
public class CarritoResponse {

    private Long id;
    private Long usuarioId;
    private List<ItemCarrito> items;
    private BigDecimal subtotal;
    private BigDecimal iva;
    private BigDecimal total;
    private LocalDateTime creadoEn;

    public CarritoResponse() {}

    public CarritoResponse(Long id, Long usuarioId, List<ItemCarrito> items, LocalDateTime creadoEn) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.items = items;
        this.creadoEn = creadoEn;

        // calcular subtotal sumando el subtotal de cada item
        this.subtotal = items.stream()
                .map(ItemCarrito::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // iva = 19% del subtotal, redondeado al peso chileno
        this.iva = this.subtotal
                .multiply(new BigDecimal("0.19"))
                .setScale(0, RoundingMode.HALF_UP);

        // total final que paga el cliente
        this.total = this.subtotal.add(this.iva);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public List<ItemCarrito> getItems() { return items; }
    public void setItems(List<ItemCarrito> items) { this.items = items; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public BigDecimal getIva() { return iva; }
    public void setIva(BigDecimal iva) { this.iva = iva; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public LocalDateTime getCreadoEn() { return creadoEn; }
    public void setCreadoEn(LocalDateTime creadoEn) { this.creadoEn = creadoEn; }
}
