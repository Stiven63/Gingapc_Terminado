package com.gingapc.resena.controller;

import com.gingapc.resena.dto.EstadisticasResponse;
import com.gingapc.resena.dto.ResenaRequest;
import com.gingapc.resena.model.Resena;
import com.gingapc.resena.service.ResenaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/resenas")
public class ResenaController {

    @Autowired
    private ResenaService resenaService;

    // POST /api/resenas — dejar una resena de un producto
    // body: { "usuarioId": 1, "productoId": 3, "calificacion": 5, "comentario": "muy buena calidad", "nombreUsuario": "Juan" }
    @PostMapping
    public ResponseEntity<Resena> crear(@Valid @RequestBody ResenaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(resenaService.crear(request));
    }

    // GET /api/resenas — ver todas las resenas
    @GetMapping
    public ResponseEntity<List<Resena>> listar() {
        return ResponseEntity.ok(resenaService.obtenerTodas());
    }

    // GET /api/resenas/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Resena> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(resenaService.obtenerPorId(id));
    }

    // GET /api/resenas/producto/{productoId} — resenas de un producto especifico
    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<Resena>> porProducto(@PathVariable Long productoId) {
        return ResponseEntity.ok(resenaService.obtenerPorProducto(productoId));
    }

    // GET /api/resenas/producto/{productoId}/estadisticas — promedio y total
    @GetMapping("/producto/{productoId}/estadisticas")
    public ResponseEntity<EstadisticasResponse> estadisticas(@PathVariable Long productoId) {
        return ResponseEntity.ok(resenaService.obtenerEstadisticas(productoId));
    }

    // GET /api/resenas/usuario/{usuarioId} — resenas que dejo un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Resena>> porUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(resenaService.obtenerPorUsuario(usuarioId));
    }

    // DELETE /api/resenas/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        return ResponseEntity.ok(resenaService.eliminar(id));
    }
}
