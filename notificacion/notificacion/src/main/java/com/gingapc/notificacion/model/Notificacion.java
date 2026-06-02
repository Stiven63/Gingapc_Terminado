package com.gingapc.notificacion.model;

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
@Table(name = "notificaciones")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long usuarioId;

    // correo al que se enviaria la notificacion
    private String correoDestino;

    // tipo de notificacion para clasificarla
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoNotificacion tipo;

    @Column(nullable = false)
    private String asunto;

    // mensaje completo de la notificacion
    @Column(columnDefinition = "TEXT")
    private String mensaje;

    // false = no leida, true = ya la vio el usuario
    private Boolean leida = false;

    private LocalDateTime enviadoEn;

    @PrePersist
    public void prePersist() {
        this.enviadoEn = LocalDateTime.now();
        if (this.leida == null) {
            this.leida = false;
        }
    }

    // tipos de notificacion que maneja el sistema
    public enum TipoNotificacion {
        PEDIDO_CONFIRMADO,
        PEDIDO_EN_CAMINO,
        PEDIDO_ENTREGADO,
        OFERTA,
        ALERTA_STOCK
    }

    public Notificacion() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getCorreoDestino() { return correoDestino; }
    public void setCorreoDestino(String correoDestino) { this.correoDestino = correoDestino; }

    public TipoNotificacion getTipo() { return tipo; }
    public void setTipo(TipoNotificacion tipo) { this.tipo = tipo; }

    public String getAsunto() { return asunto; }
    public void setAsunto(String asunto) { this.asunto = asunto; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public Boolean getLeida() { return leida; }
    public void setLeida(Boolean leida) { this.leida = leida; }

    public LocalDateTime getEnviadoEn() { return enviadoEn; }
    public void setEnviadoEn(LocalDateTime enviadoEn) { this.enviadoEn = enviadoEn; }
}
