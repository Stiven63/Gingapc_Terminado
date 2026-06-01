package com.gingapc.catalogo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gingapc.catalogo.dto.ProductoDto;
import com.gingapc.catalogo.model.Producto;
import com.gingapc.catalogo.service.ProductoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/catalogo")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // GET /api/catalogo — ver todos los productos activos
    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(productoService.obtenerTodos());
    }

    // GET /api/catalogo/{id} — ver un producto especifico
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    // productos con stock > 0
    @GetMapping("/disponibles")
    public ResponseEntity<List<Producto>> disponibles() {
        return ResponseEntity.ok(productoService.obtenerDisponibles());
    }

    // productos sin stock
    @GetMapping("/agotados")
    public ResponseEntity<List<Producto>> agotados() {
        return ResponseEntity.ok(productoService.obtenerAgotados());
    }

    //  filtrar por categoria
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Producto>> porCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(productoService.obtenerPorCategoria(categoria));
    }

    // GET /api/catalogo/marca/{marca} — filtrar por marca
    @GetMapping("/marca/{marca}")
    public ResponseEntity<List<Producto>> porMarca(@PathVariable String marca) {
        return ResponseEntity.ok(productoService.obtenerPorMarca(marca));
    }

    // POST /api/catalogo — crear producto nuevo
    @PostMapping
    public ResponseEntity<Producto> crear(@Valid @RequestBody ProductoDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productoService.crear(dto));
    }

    // PUT /api/catalogo/{id} — actualizar producto completo
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id,
                                               @Valid @RequestBody ProductoDto dto) {
        return ResponseEntity.ok(productoService.actualizar(id, dto));
    }

    // PATCH /api/catalogo/{id}/stock?cantidad=-2 — modificar stock
    // este endpoint lo usa el carrito-service cuando agrega o quita productos
    @PatchMapping("/{id}/stock")
    public ResponseEntity<Producto> actualizarStock(@PathVariable Long id,
                                                    @RequestParam Integer cantidad) {
        return ResponseEntity.ok(productoService.actualizarStock(id, cantidad));
    }

    // DELETE /api/catalogo/{id} — desactivar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.eliminar(id));
    }
}
