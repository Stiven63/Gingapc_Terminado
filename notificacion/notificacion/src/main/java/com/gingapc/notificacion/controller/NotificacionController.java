package com.gingapc.notificacion.controller;

import com.gingapc.notificacion.dto.NotificacionRequest;
import com.gingapc.notificacion.model.Notificacion;
import com.gingapc.notificacion.service.NotificacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    // POST /api/notificaciones/enviar — crear una notificacion nueva
    // body: { "usuarioId": 1, "tipo": "PEDIDO_CONFIRMADO", "asunto": "...", "mensaje": "..." }
    @PostMapping("/enviar")
    public ResponseEntity<Notificacion> enviar(@Valid @RequestBody NotificacionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(notificacionService.enviar(request));
    }

    // GET /api/notificaciones — ver todas las notificaciones
    @GetMapping
    public ResponseEntity<List<Notificacion>> listar() {
        return ResponseEntity.ok(notificacionService.obtenerTodas());
    }

    // GET /api/notificaciones/usuario/{usuarioId} — notificaciones de un usuario
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Notificacion>> porUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(notificacionService.obtenerPorUsuario(usuarioId));
    }

    // GET /api/notificaciones/usuario/{usuarioId}/no-leidas
    @GetMapping("/usuario/{usuarioId}/no-leidas")
    public ResponseEntity<List<Notificacion>> noLeidas(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(notificacionService.obtenerNoLeidas(usuarioId));
    }

    // GET /api/notificaciones/tipo?tipo=OFERTA — filtrar por tipo
    @GetMapping("/tipo")
    public ResponseEntity<List<Notificacion>> porTipo(@RequestParam String tipo) {
        return ResponseEntity.ok(notificacionService.obtenerPorTipo(tipo));
    }

    // PATCH /api/notificaciones/{id}/leer — marcar una como leida
    @PatchMapping("/{id}/leer")
    public ResponseEntity<Notificacion> marcarLeida(@PathVariable Long id) {
        return ResponseEntity.ok(notificacionService.marcarComoLeida(id));
    }

    // PATCH /api/notificaciones/usuario/{usuarioId}/leer-todas
    @PatchMapping("/usuario/{usuarioId}/leer-todas")
    public ResponseEntity<String> marcarTodasLeidas(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(notificacionService.marcarTodasLeidas(usuarioId));
    }

    // DELETE /api/notificaciones/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        return ResponseEntity.ok(notificacionService.eliminar(id));
    }
}
