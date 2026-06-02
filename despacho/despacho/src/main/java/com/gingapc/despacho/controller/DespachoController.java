package com.gingapc.despacho.controller;

import com.gingapc.despacho.dto.DespachoRequest;
import com.gingapc.despacho.model.Despacho;
import com.gingapc.despacho.service.DespachoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/despacho")
public class DespachoController {

    @Autowired
    private DespachoService despachoService;

    // POST /api/despacho — crear despacho para un pedido
    // body: { "pedidoId": 1, "nombreDestinatario": "Juan", "direccion": "...", "ciudad": "Santiago" }
    // automaticamente cambia el estado del pedido a DESPACHADO
    @PostMapping
    public ResponseEntity<Despacho> crear(@Valid @RequestBody DespachoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(despachoService.crearDespacho(request));
    }

    // GET /api/despacho — ver todos los despachos
    @GetMapping
    public ResponseEntity<List<Despacho>> listar() {
        return ResponseEntity.ok(despachoService.obtenerTodos());
    }

    // GET /api/despacho/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Despacho> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(despachoService.obtenerPorId(id));
    }

    // GET /api/despacho/pedido/{pedidoId} — buscar despacho por pedido
    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<Despacho> porPedido(@PathVariable Long pedidoId) {
        return ResponseEntity.ok(despachoService.obtenerPorPedido(pedidoId));
    }

    // GET /api/despacho/estado?estado=EN_RUTA — filtrar por estado
    @GetMapping("/estado")
    public ResponseEntity<List<Despacho>> porEstado(@RequestParam String estado) {
        return ResponseEntity.ok(despachoService.obtenerPorEstado(estado));
    }

    // PATCH /api/despacho/{id}/estado?estado=EN_RUTA — cambiar estado del despacho
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Despacho> cambiarEstado(@PathVariable Long id,
                                                  @RequestParam String estado) {
        return ResponseEntity.ok(despachoService.cambiarEstado(id, estado));
    }

    // PATCH /api/despacho/{id}/confirmar-entrega
    // confirma que el repartidor entrego el pedido al cliente
    // cambia el pedido a ENTREGADO automaticamente en pedido-service
    @PatchMapping("/{id}/confirmar-entrega")
    public ResponseEntity<Despacho> confirmarEntrega(@PathVariable Long id) {
        return ResponseEntity.ok(despachoService.confirmarEntrega(id));
    }
}
