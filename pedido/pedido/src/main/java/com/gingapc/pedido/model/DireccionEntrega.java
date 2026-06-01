package com.gingapc.pedido.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

// @Embeddable significa que los campos de esta clase
// se guardan directamente en la tabla pedidos, no en una tabla aparte
@Embeddable
public class DireccionEntrega {

    @NotBlank(message = "El nombre del destinatario es obligatorio")
    private String nombreDestinatario;

    @NotBlank(message = "La calle es obligatoria")
    private String calle;

    private String numero;

    @NotBlank(message = "La comuna es obligatoria")
    private String comuna;

    @NotBlank(message = "La ciudad es obligatoria")
    private String ciudad;

    // region de chile, ej: Region Metropolitana
    private String region;

    public DireccionEntrega() {}

    public String getNombreDestinatario() { return nombreDestinatario; }
    public void setNombreDestinatario(String nombreDestinatario) { this.nombreDestinatario = nombreDestinatario; }

    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getComuna() { return comuna; }
    public void setComuna(String comuna) { this.comuna = comuna; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }
}
