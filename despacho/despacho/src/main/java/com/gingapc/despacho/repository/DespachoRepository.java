package com.gingapc.despacho.repository;

import com.gingapc.despacho.model.Despacho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DespachoRepository extends JpaRepository<Despacho, Long> {

    // buscar despacho por id de pedido
    Optional<Despacho> findByPedidoId(Long pedidoId);

    // filtrar despachos por estado
    List<Despacho> findByEstado(Despacho.EstadoDespacho estado);

    // despachos asignados a un repartidor
    List<Despacho> findByNombreRepartidor(String nombreRepartidor);
}
