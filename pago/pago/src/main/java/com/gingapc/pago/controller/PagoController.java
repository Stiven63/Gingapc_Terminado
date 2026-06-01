package com.gingapc.pago.controller;

import com.gingapc.pago.dto.PagoRequest;
import com.gingapc.pago.model.Pago;
import com.gingapc.pago.service.PagoService;
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
@RequestMapping("/api/pago")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    // POST /api/pago/procesar
    // body: { "usuarioId": 1, "metodoPago": "WEBPAY" }
    // llama al carrito-service para obtener los montos y luego lo vacia
    @PostMapping("/procesar")
    public ResponseEntity<Pago> procesar(@Valid @RequestBody PagoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pagoService.procesarPago(request));
    }

    // GET /api/pago — ver todos los pagos
    @GetMapping
    public ResponseEntity<List<Pago>> listar() {
        return ResponseEntity.ok(pagoService.obtenerTodos());
    }

    // GET /api/pago/{id} — ver un pago por id
    // este endpoint lo usa pedido-service para verificar si el pago fue aprobado
    @GetMapping("/{id}")
    public ResponseEntity<Pago> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.obtenerPorId(id));
    }

    // GET /api/pago/usuario/{usuarioId} — historial de pagos de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Pago>> porUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(pagoService.obtenerPorUsuario(usuarioId));
    }

    // PATCH /api/pago/{id}/estado?estado=APROBADO — cambiar estado del pago
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Pago> cambiarEstado(@PathVariable Long id,
                                               @RequestParam String estado) {
        return ResponseEntity.ok(pagoService.cambiarEstado(id, estado));
    }
}
