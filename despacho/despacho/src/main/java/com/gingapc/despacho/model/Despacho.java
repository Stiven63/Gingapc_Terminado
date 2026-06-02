package com.gingapc.despacho.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "despachos")
public class Despacho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // id del pedido asociado, no pueden haber dos despachos del mismo pedido
    @Column(unique = true, nullable = false)
    private Long pedidoId;

    // numero de pedido tipo GINGA-2025-0001 para mostrarlo en pantalla
    private String numeroPedido;

    // datos del destinatario
    private String nombreDestinatario;
    private String direccion;
    private String comuna;
    private String ciudad;
    private String region;

    // repartidor asignado al despacho
    private String nombreRepartidor;
    private String telefonoRepartidor;

    @Enumerated(EnumType.STRING)
    private EstadoDespacho estado = EstadoDespacho.PENDIENTE;

    private LocalDateTime fechaEstimadaEntrega;

    // se registra cuando el repartidor confirma la entrega
    private LocalDateTime fechaEntregaReal;

    private LocalDateTime creadoEn;

    @PrePersist
    public void prePersist() {
        this.creadoEn = LocalDateTime.now();
    }

    public enum EstadoDespacho {
        PENDIENTE,
        ASIGNADO,
        EN_RUTA,
        ENTREGADO,
        NO_ENTREGADO
    }

    public Despacho() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }

    public String getNumeroPedido() { return numeroPedido; }
    public void setNumeroPedido(String numeroPedido) { this.numeroPedido = numeroPedido; }

    public String getNombreDestinatario() { return nombreDestinatario; }
    public void setNombreDestinatario(String nombreDestinatario) { this.nombreDestinatario = nombreDestinatario; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getComuna() { return comuna; }
    public void setComuna(String comuna) { this.comuna = comuna; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getNombreRepartidor() { return nombreRepartidor; }
    public void setNombreRepartidor(String nombreRepartidor) { this.nombreRepartidor = nombreRepartidor; }

    public String getTelefonoRepartidor() { return telefonoRepartidor; }
    public void setTelefonoRepartidor(String telefonoRepartidor) { this.telefonoRepartidor = telefonoRepartidor; }

    public EstadoDespacho getEstado() { return estado; }
    public void setEstado(EstadoDespacho estado) { this.estado = estado; }

    public LocalDateTime getFechaEstimadaEntrega() { return fechaEstimadaEntrega; }
    public void setFechaEstimadaEntrega(LocalDateTime fechaEstimadaEntrega) { this.fechaEstimadaEntrega = fechaEstimadaEntrega; }

    public LocalDateTime getFechaEntregaReal() { return fechaEntregaReal; }
    public void setFechaEntregaReal(LocalDateTime fechaEntregaReal) { this.fechaEntregaReal = fechaEntregaReal; }

    public LocalDateTime getCreadoEn() { return creadoEn; }
    public void setCreadoEn(LocalDateTime creadoEn) { this.creadoEn = creadoEn; }
}
