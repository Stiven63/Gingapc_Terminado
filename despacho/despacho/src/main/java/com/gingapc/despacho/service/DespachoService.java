package com.gingapc.despacho.service;

import com.gingapc.despacho.client.PedidoClient;
import com.gingapc.despacho.dto.DespachoRequest;
import com.gingapc.despacho.dto.PedidoResponse;
import com.gingapc.despacho.exception.DespachoNotFoundException;
import com.gingapc.despacho.model.Despacho;
import com.gingapc.despacho.repository.DespachoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DespachoService {

    @Autowired
    private DespachoRepository despachoRepository;

    // feign client que se comunica con pedido-service
    @Autowired
    private PedidoClient pedidoClient;

    // crear un despacho nuevo para un pedido
    public Despacho crearDespacho(DespachoRequest request) {

        // llamada feign al pedido-service para verificar que el pedido existe
        PedidoResponse pedido = pedidoClient.obtenerPedido(request.getPedidoId());

        // regla de negocio: no se puede crear un despacho duplicado para el mismo pedido
        boolean yaExiste = despachoRepository.findByPedidoId(request.getPedidoId()).isPresent();
        if (yaExiste) {
            throw new RuntimeException("Ya existe un despacho para el pedido: "
                    + pedido.getNumeroPedido());
        }

        Despacho despacho = new Despacho();
        despacho.setPedidoId(request.getPedidoId());
        despacho.setNumeroPedido(pedido.getNumeroPedido());
        despacho.setNombreDestinatario(request.getNombreDestinatario());
        despacho.setDireccion(request.getDireccion());
        despacho.setComuna(request.getComuna());
        despacho.setCiudad(request.getCiudad());
        despacho.setRegion(request.getRegion());
        despacho.setNombreRepartidor(request.getNombreRepartidor());
        despacho.setTelefonoRepartidor(request.getTelefonoRepartidor());
        despacho.setFechaEstimadaEntrega(request.getFechaEstimadaEntrega());

        // actualizar el estado del pedido a DESPACHADO en pedido-service
        pedidoClient.cambiarEstadoPedido(request.getPedidoId(), "DESPACHADO");

        return despachoRepository.save(despacho);
    }

    // obtener despacho por id
    public Despacho obtenerPorId(Long id) {
        return despachoRepository.findById(id)
                .orElseThrow(() -> new DespachoNotFoundException(
                        "Despacho no encontrado con id: " + id));
    }

    // obtener despacho por id de pedido
    public Despacho obtenerPorPedido(Long pedidoId) {
        return despachoRepository.findByPedidoId(pedidoId)
                .orElseThrow(() -> new DespachoNotFoundException(
                        "No se encontro despacho para el pedido con id: " + pedidoId));
    }

    // listar todos los despachos
    public List<Despacho> obtenerTodos() {
        return despachoRepository.findAll();
    }

    // filtrar por estado
    public List<Despacho> obtenerPorEstado(String estado) {
        try {
            Despacho.EstadoDespacho estadoEnum =
                    Despacho.EstadoDespacho.valueOf(estado.toUpperCase());
            return despachoRepository.findByEstado(estadoEnum);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Estado invalido. Los valores permitidos son: "
                    + "PENDIENTE, ASIGNADO, EN_RUTA, ENTREGADO, NO_ENTREGADO");
        }
    }

    // cambiar el estado del despacho
    public Despacho cambiarEstado(Long id, String estado) {
        Despacho despacho = obtenerPorId(id);

        try {
            despacho.setEstado(Despacho.EstadoDespacho.valueOf(estado.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Estado invalido. Los valores permitidos son: "
                    + "PENDIENTE, ASIGNADO, EN_RUTA, ENTREGADO, NO_ENTREGADO");
        }

        // si el estado es EN_RUTA se actualiza el pedido a DESPACHADO
        if (despacho.getEstado() == Despacho.EstadoDespacho.EN_RUTA) {
            pedidoClient.cambiarEstadoPedido(despacho.getPedidoId(), "DESPACHADO");
        }

        return despachoRepository.save(despacho);
    }

    // confirmar que el pedido fue entregado al cliente
    // este es el endpoint final del flujo completo de ginga pc
    public Despacho confirmarEntrega(Long id) {
        Despacho despacho = obtenerPorId(id);

        despacho.setEstado(Despacho.EstadoDespacho.ENTREGADO);
        despacho.setFechaEntregaReal(LocalDateTime.now());

        // actualizar el pedido a ENTREGADO en pedido-service
        pedidoClient.cambiarEstadoPedido(despacho.getPedidoId(), "ENTREGADO");

        return despachoRepository.save(despacho);
    }
}
