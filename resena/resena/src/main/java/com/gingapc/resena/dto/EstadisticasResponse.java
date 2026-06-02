package com.gingapc.resena.dto;

// dto para devolver el promedio y total de resenas de un producto
public class EstadisticasResponse {

    private Long productoId;
    private Double promedio;
    private Long totalResenas;

    public EstadisticasResponse() {}

    public EstadisticasResponse(Long productoId, Double promedio, Long totalResenas) {
        this.productoId = productoId;
        this.promedio = promedio;
        this.totalResenas = totalResenas;
    }

    public Long getProductoId() { return productoId; }
    public void setProductoId(Long productoId) { this.productoId = productoId; }

    public Double getPromedio() { return promedio; }
    public void setPromedio(Double promedio) { this.promedio = promedio; }

    public Long getTotalResenas() { return totalResenas; }
    public void setTotalResenas(Long totalResenas) { this.totalResenas = totalResenas; }
}
