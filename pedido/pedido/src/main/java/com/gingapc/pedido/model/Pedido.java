package com.gingapc.pedido.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // numero unico generado automaticamente: GINGA-2025-0001
    @Column(unique = true)
    private String numeroPedido;

    @Column(nullable = false)
    private Long usuarioId;

    // id del pago aprobado que genero este pedido
    @Column(nullable = false)
    private Long pagoId;

    // monto total que viene del pago (ya incluye iva)
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal montoTotal;

    // la direccion se guarda en la misma tabla usando @Embedded
    @Embedded
    private DireccionEntrega direccion;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado = EstadoPedido.CONFIRMADO;

    private LocalDateTime creadoEn;

    @PrePersist
    public void prePersist() {
        this.creadoEn = LocalDateTime.now();
    }

    public enum EstadoPedido {
        CONFIRMADO,
        EN_PREPARACION,
        DESPACHADO,
        ENTREGADO
    }

    public Pedido() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumeroPedido() { return numeroPedido; }
    public void setNumeroPedido(String numeroPedido) { this.numeroPedido = numeroPedido; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public Long getPagoId() { return pagoId; }
    public void setPagoId(Long pagoId) { this.pagoId = pagoId; }

    public BigDecimal getMontoTotal() { return montoTotal; }
    public void setMontoTotal(BigDecimal montoTotal) { this.montoTotal = montoTotal; }

    public DireccionEntrega getDireccion() { return direccion; }
    public void setDireccion(DireccionEntrega direccion) { this.direccion = direccion; }

    public EstadoPedido getEstado() { return estado; }
    public void setEstado(EstadoPedido estado) { this.estado = estado; }

    public LocalDateTime getCreadoEn() { return creadoEn; }
    public void setCreadoEn(LocalDateTime creadoEn) { this.creadoEn = creadoEn; }
}
