package com.gingapc.pago.repository;

import com.gingapc.pago.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {

    // historial de pagos de un usuario
    List<Pago> findByUsuarioId(Long usuarioId);

    // buscar pagos por estado (PENDIENTE, APROBADO, RECHAZADO)
    List<Pago> findByEstado(Pago.EstadoPago estado);
}
