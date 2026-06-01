package com.gingapc.pago.service;

import com.gingapc.pago.client.CarritoClient;
import com.gingapc.pago.dto.CarritoResponse;
import com.gingapc.pago.dto.PagoRequest;
import com.gingapc.pago.exception.PagoNotFoundException;
import com.gingapc.pago.model.Pago;
import com.gingapc.pago.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    // feign client que se comunica con carrito-service
    @Autowired
    private CarritoClient carritoClient;

    // procesar el pago del carrito
    public Pago procesarPago(PagoRequest request) {

        // llamada feign al carrito-service para obtener los montos
        CarritoResponse carrito = carritoClient.obtenerCarrito(request.getUsuarioId());

        // regla de negocio: no se puede pagar si el carrito esta vacio
        if (carrito.getTotal() == null || carrito.getTotal().signum() == 0) {
            throw new RuntimeException("No se puede procesar el pago, el carrito esta vacio");
        }

        // crear el registro del pago con los montos que vienen del carrito
        Pago pago = new Pago();
        pago.setUsuarioId(request.getUsuarioId());
        pago.setCarritoId(carrito.getId());
        pago.setSubtotal(carrito.getSubtotal());
        pago.setIva(carrito.getIva());
        pago.setTotal(carrito.getTotal());
        pago.setMetodoPago(request.getMetodoPago());

        // para la demo el pago se aprueba automaticamente
        // en produccion aqui iria la integracion con webpay u otro gateway
        pago.setEstado(Pago.EstadoPago.APROBADO);

        Pago guardado = pagoRepository.save(pago);

        // una vez aprobado el pago se vacia el carrito
        carritoClient.vaciarCarrito(request.getUsuarioId());

        return guardado;
    }

    // buscar pago por id, lo usa pedido-service para verificar que el pago fue aprobado
    public Pago obtenerPorId(Long id) {
        return pagoRepository.findById(id)
                .orElseThrow(() -> new PagoNotFoundException("Pago no encontrado con id: " + id));
    }

    // historial de pagos del usuario
    public List<Pago> obtenerPorUsuario(Long usuarioId) {
        return pagoRepository.findByUsuarioId(usuarioId);
    }

    // listar todos los pagos
    public List<Pago> obtenerTodos() {
        return pagoRepository.findAll();
    }

    // cambiar estado del pago manualmente (APROBADO o RECHAZADO)
    public Pago cambiarEstado(Long id, String estado) {
        Pago pago = obtenerPorId(id);

        try {
            pago.setEstado(Pago.EstadoPago.valueOf(estado.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Estado invalido. Los valores permitidos son: PENDIENTE, APROBADO, RECHAZADO");
        }

        return pagoRepository.save(pago);
    }
}
