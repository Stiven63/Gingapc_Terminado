package com.gingapc.resena.dto;

// dto que mapea la respuesta que viene del pedido-service via feign
public class PedidoResponse {

    private Long id;
    private String numeroPedido;
    private Long usuarioId;
    private String estado;

    public PedidoResponse() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNumeroPedido() { return numeroPedido; }
    public void setNumeroPedido(String numeroPedido) { this.numeroPedido = numeroPedido; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
