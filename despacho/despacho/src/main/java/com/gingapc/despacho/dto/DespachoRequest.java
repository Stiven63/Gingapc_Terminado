package com.gingapc.despacho.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class DespachoRequest {

    // id del pedido que se va a despachar
    @NotNull(message = "El pedidoId es obligatorio")
    private Long pedidoId;

    @NotBlank(message = "El nombre del destinatario es obligatorio")
    private String nombreDestinatario;

    @NotBlank(message = "La direccion es obligatoria")
    private String direccion;

    private String comuna;

    @NotBlank(message = "La ciudad es obligatoria")
    private String ciudad;

    private String region;

    private String nombreRepartidor;
    private String telefonoRepartidor;
    private LocalDateTime fechaEstimadaEntrega;

    public DespachoRequest() {}

    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }

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

    public LocalDateTime getFechaEstimadaEntrega() { return fechaEstimadaEntrega; }
    public void setFechaEstimadaEntrega(LocalDateTime fechaEstimadaEntrega) { this.fechaEstimadaEntrega = fechaEstimadaEntrega; }
}
