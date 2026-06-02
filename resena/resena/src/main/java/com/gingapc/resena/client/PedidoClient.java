package com.gingapc.resena.client;

import com.gingapc.resena.dto.PedidoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

// feign client que llama al pedido-service
// se usa para verificar si el usuario tiene pedidos antes de dejar una resena
@FeignClient(name = "pedido-service")
public interface PedidoClient {

    // trae todos los pedidos del usuario para saber si compro algo
    @GetMapping("/api/pedidos/usuario/{usuarioId}")
    List<PedidoResponse> obtenerPedidosPorUsuario(@PathVariable("usuarioId") Long usuarioId);
}
