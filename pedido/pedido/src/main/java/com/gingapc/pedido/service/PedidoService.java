package com.gingapc.pedido.service;

import com.gingapc.pedido.client.PagoClient;
import com.gingapc.pedido.dto.CrearPedidoRequest;
import com.gingapc.pedido.dto.PagoResponse;
import com.gingapc.pedido.exception.PedidoNotFoundException;
import com.gingapc.pedido.model.Pedido;
import com.gingapc.pedido.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    // feign client que se comunica con pago-service
    @Autowired
    private PagoClient pagoClient;

    // crear un pedido nuevo
    public Pedido crearPedido(CrearPedidoRequest request) {

        // llamada feign al pago-service para verificar que el pago existe
        PagoResponse pago = pagoClient.obtenerPago(request.getPagoId());

        // regla de negocio: solo se puede crear un pedido si el pago fue APROBADO
        if (!"APROBADO".equals(pago.getEstado())) {
            throw new RuntimeException("No se puede crear el pedido porque el pago no esta aprobado. "
                    + "Estado actual del pago: " + pago.getEstado());
        }

        // verificar que el pago pertenece al usuario
        if (!pago.getUsuarioId().equals(request.getUsuarioId())) {
            throw new RuntimeException("El pago no pertenece al usuario indicado");
        }

        Pedido pedido = new Pedido();
        pedido.setUsuarioId(request.getUsuarioId());
        pedido.setPagoId(request.getPagoId());
        pedido.setMontoTotal(pago.getTotal());
        pedido.setDireccion(request.getDireccion());

        // guardar primero para obtener el id generado
        Pedido guardado = pedidoRepository.save(pedido);

        // generar el numero de pedido con el id recien creado
        // formato: GINGA-AÑO-IDCONCEROSCALADO
        String numero = "GINGA-" + Year.now().getValue() + "-" + String.format("%04d", guardado.getId());
        guardado.setNumeroPedido(numero);

        return pedidoRepository.save(guardado);
    }

    // obtener pedido por id
    public Pedido obtenerPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido no encontrado con id: " + id));
    }

    // buscar por numero de pedido tipo GINGA-2025-0001
    public Pedido obtenerPorNumero(String numeroPedido) {
        return pedidoRepository.findByNumeroPedido(numeroPedido)
                .orElseThrow(() -> new PedidoNotFoundException("Pedido no encontrado: " + numeroPedido));
    }

    // historial de pedidos de un usuario
    public List<Pedido> obtenerPorUsuario(Long usuarioId) {
        return pedidoRepository.findByUsuarioId(usuarioId);
    }

    // listar todos los pedidos
    public List<Pedido> obtenerTodos() {
        return pedidoRepository.findAll();
    }

    // actualizar el estado del pedido
    // lo usa despacho-service cuando cambia el estado del envio
    public Pedido cambiarEstado(Long id, String estado) {
        Pedido pedido = obtenerPorId(id);

        try {
            pedido.setEstado(Pedido.EstadoPedido.valueOf(estado.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Estado invalido. Los valores permitidos son: "
                    + "CONFIRMADO, EN_PREPARACION, DESPACHADO, ENTREGADO");
        }

        return pedidoRepository.save(pedido);
    }
}
