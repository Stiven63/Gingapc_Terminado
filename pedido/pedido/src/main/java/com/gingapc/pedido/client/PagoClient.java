package com.gingapc.pedido.client;

import com.gingapc.pedido.dto.PagoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// feign client que llama al pago-service
// se usa para verificar que el pago exista y este aprobado antes de crear el pedido
@FeignClient(name = "pago-service")
public interface PagoClient {

    @GetMapping("/api/pago/{id}")
    PagoResponse obtenerPago(@PathVariable("id") Long id);
}
