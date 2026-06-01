package com.gingapc.carrito.client;

import com.gingapc.carrito.dto.ProductoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

// este cliente feign llama directamente al catalogo-service
// el name tiene que coincidir exactamente con el spring.application.name del catalogo
@FeignClient(name = "catalogo-service")
public interface CatalogoClient {

    // obtener los datos del producto (precio, stock, nombre)
    @GetMapping("/api/catalogo/{id}")
    ProductoResponse obtenerProducto(@PathVariable("id") Long id);

    // actualizar el stock del producto cuando se agrega al carrito
    // si cantidad es negativa descuenta, si es positiva agrega
    @PatchMapping("/api/catalogo/{id}/stock")
    ProductoResponse actualizarStock(@PathVariable("id") Long id,
                                     @RequestParam("cantidad") Integer cantidad);
}
