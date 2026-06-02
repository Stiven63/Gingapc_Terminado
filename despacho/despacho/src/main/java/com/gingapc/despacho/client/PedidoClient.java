package com.gingapc.despacho.client;

import com.gingapc.despacho.dto.PedidoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

// feign client que llama al pedido-service
// se usa para verificar que el pedido exista y para actualizar su estado
@FeignClient(name = "pedido-service")
public interface PedidoClient {

    // verificar que el pedido existe antes de crear el despacho
    @GetMapping("/api/pedidos/{id}")
    PedidoResponse obtenerPedido(@PathVariable("id") Long id);

    // actualizar el estado del pedido cuando cambia el estado del despacho
    @PatchMapping("/api/pedidos/{id}/estado")
    PedidoResponse cambiarEstadoPedido(@PathVariable("id") Long id,
                                       @RequestParam("estado") String estado);
}
