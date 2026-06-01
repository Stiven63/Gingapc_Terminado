package com.gingapc.carrito.controller;

import com.gingapc.carrito.dto.AgregarItemRequest;
import com.gingapc.carrito.dto.CarritoResponse;
import com.gingapc.carrito.service.CarritoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carrito")
public class CarritoController {

    @Autowired
    private CarritoService carritoService;

    // GET /api/carrito/{usuarioId}
    // devuelve el carrito con subtotal, iva (19%) y total
    @GetMapping("/{usuarioId}")
    public ResponseEntity<CarritoResponse> obtener(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(carritoService.obtenerCarrito(usuarioId));
    }

    // POST /api/carrito/{usuarioId}/agregar
    // body: { "productoId": 1, "cantidad": 2 }
    // llama al catalogo-service para verificar stock antes de agregar
    @PostMapping("/{usuarioId}/agregar")
    public ResponseEntity<CarritoResponse> agregar(@PathVariable Long usuarioId,
                                                   @Valid @RequestBody AgregarItemRequest request) {
        return ResponseEntity.ok(carritoService.agregarProducto(usuarioId, request));
    }

    // DELETE /api/carrito/{usuarioId}/item/{itemId}
    // elimina un producto especifico del carrito
    @DeleteMapping("/{usuarioId}/item/{itemId}")
    public ResponseEntity<CarritoResponse> eliminarItem(@PathVariable Long usuarioId,
                                                        @PathVariable Long itemId) {
        return ResponseEntity.ok(carritoService.eliminarItem(usuarioId, itemId));
    }

    // DELETE /api/carrito/{usuarioId}/vaciar
    // vacia todo el carrito, lo usa pago-service despues de cobrar
    @DeleteMapping("/{usuarioId}/vaciar")
    public ResponseEntity<String> vaciar(@PathVariable Long usuarioId) {
        carritoService.vaciarCarrito(usuarioId);
        return ResponseEntity.ok("Carrito vaciado correctamente");
    }
}
