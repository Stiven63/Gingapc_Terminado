package com.gingapc.resena.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
// la restriccion unique evita que el mismo usuario resene el mismo producto dos veces
@Table(name = "resenas", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"usuario_id", "producto_id"},
                      name = "uk_usuario_producto")
})
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(name = "producto_id", nullable = false)
    private Long productoId;

    // calificacion de 1 a 5 estrellas
    @Min(value = 1, message = "La calificacion minima es 1 estrella")
    @Max(value = 5, message = "La calificacion maxima es 5 estrellas")
    @Column(nullable = false)
    private Integer calificacion;

    // comentario del cliente sobre el producto
    @Size(min = 5, max = 500, message = "El comentario debe tener entre 5 y 500 caracteres")
    @Column(columnDefinition = "TEXT")
    private String comentario;

    // nombre del usuario para mostrarlo en la resena
    private String nombreUsuario;

    // se marca como true si el usuario tiene pedidos entregados
    private Boolean compraVerificada = false;

    private LocalDateTime creadoEn;

    @PrePersist
    public void prePersist() {
        this.creadoEn = LocalDateTime.now();
    }

    public Resena() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public Integer getCalificacion() { return calificacion; }
    public void setCalificacion(Integer calificacion) { this.calificacion = calificacion; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public Boolean getCompraVerificada() { return compraVerificada; }
    public void setCompraVerificada(Boolean compraVerificada) { this.compraVerificada = compraVerificada; }

    public LocalDateTime getCreadoEn() { return creadoEn; }
    public void setCreadoEn(LocalDateTime creadoEn) { this.creadoEn = creadoEn; }
}
