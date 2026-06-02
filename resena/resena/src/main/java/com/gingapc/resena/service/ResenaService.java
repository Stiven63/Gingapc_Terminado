package com.gingapc.resena.service;

import com.gingapc.resena.client.PedidoClient;
import com.gingapc.resena.dto.EstadisticasResponse;
import com.gingapc.resena.dto.PedidoResponse;
import com.gingapc.resena.dto.ResenaRequest;
import com.gingapc.resena.exception.ResenaNotFoundException;
import com.gingapc.resena.model.Resena;
import com.gingapc.resena.repository.ResenaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResenaService {

    @Autowired
    private ResenaRepository resenaRepository;

    // feign client que llama al pedido-service
    @Autowired
    private PedidoClient pedidoClient;

    // crear una resena nueva
    public Resena crear(ResenaRequest request) {

        // regla de negocio: un usuario no puede resear el mismo producto dos veces
        boolean yaReseno = resenaRepository
                .findByUsuarioIdAndProductoId(request.getUsuarioId(), request.getProductoId())
                .isPresent();

        if (yaReseno) {
            throw new RuntimeException("Ya dejaste una resena para este producto. "
                    + "Solo se permite una resena por producto");
        }

        // llamada feign al pedido-service para verificar si el usuario compro
        // si el servicio no responde igual se acepta la resena pero sin verificacion
        boolean compraVerificada = false;
        try {
            List<PedidoResponse> pedidos = pedidoClient
                    .obtenerPedidosPorUsuario(request.getUsuarioId());
            compraVerificada = pedidos != null && !pedidos.isEmpty();
        } catch (Exception e) {
            // si pedido-service no esta disponible no bloqueamos la resena
            System.out.println("No se pudo verificar la compra del usuario: " + e.getMessage());
        }

        Resena resena = new Resena();
        resena.setUsuarioId(request.getUsuarioId());
        resena.setProductoId(request.getProductoId());
        resena.setCalificacion(request.getCalificacion());
        resena.setComentario(request.getComentario());
        resena.setNombreUsuario(request.getNombreUsuario());
        resena.setCompraVerificada(compraVerificada);

        return resenaRepository.save(resena);
    }

    // obtener todas las resenas de un producto
    public List<Resena> obtenerPorProducto(Long productoId) {
        return resenaRepository.findByProductoId(productoId);
    }

    // obtener todas las resenas que dejo un usuario
    public List<Resena> obtenerPorUsuario(Long usuarioId) {
        return resenaRepository.findByUsuarioId(usuarioId);
    }

    // calcular promedio y total de resenas de un producto
    public EstadisticasResponse obtenerEstadisticas(Long productoId) {
        Double promedio = resenaRepository.calcularPromedioPorProducto(productoId);
        long total = resenaRepository.countByProductoId(productoId);

        // si no hay resenas el promedio es 0
        double promedioFinal = promedio != null ? Math.round(promedio * 10.0) / 10.0 : 0.0;

        return new EstadisticasResponse(productoId, promedioFinal, total);
    }

    // listar todas las resenas
    public List<Resena> obtenerTodas() {
        return resenaRepository.findAll();
    }

    // buscar resena por id
    public Resena obtenerPorId(Long id) {
        return resenaRepository.findById(id)
                .orElseThrow(() -> new ResenaNotFoundException(
                        "Resena no encontrada con id: " + id));
    }

    // eliminar resena
    public String eliminar(Long id) {
        Resena resena = obtenerPorId(id);
        resenaRepository.delete(resena);
        return "Resena eliminada correctamente";
    }
}
