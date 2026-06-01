package com.gingapc.pago.client;

import com.gingapc.pago.dto.CarritoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// feign client que se comunica con el carrito-service
// name tiene que coincidir con spring.application.name del carrito
@FeignClient(name = "carrito-service")
public interface CarritoClient {

    // obtener el carrito con subtotal, iva y total ya calculados
    @GetMapping("/api/carrito/{usuarioId}")
    CarritoResponse obtenerCarrito(@PathVariable("usuarioId") Long usuarioId);

    // vaciar el carrito despues de que el pago sea exitoso
    @DeleteMapping("/api/carrito/{usuarioId}/vaciar")
    void vaciarCarrito(@PathVariable("usuarioId") Long usuarioId);
}
