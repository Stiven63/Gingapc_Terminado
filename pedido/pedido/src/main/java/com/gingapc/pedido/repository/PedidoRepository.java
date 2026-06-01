package com.gingapc.pedido.repository;

import com.gingapc.pedido.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // buscar pedido por numero unico tipo GINGA-2025-0001
    Optional<Pedido> findByNumeroPedido(String numeroPedido);

    // historial de pedidos de un usuario
    List<Pedido> findByUsuarioId(Long usuarioId);

    // filtrar pedidos por estado
    List<Pedido> findByEstado(Pedido.EstadoPedido estado);
}
