package com.gingapc.notificacion.repository;

import com.gingapc.notificacion.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    // todas las notificaciones de un usuario
    List<Notificacion> findByUsuarioId(Long usuarioId);

    // solo las no leidas de un usuario
    List<Notificacion> findByUsuarioIdAndLeida(Long usuarioId, Boolean leida);

    // filtrar por tipo de notificacion
    List<Notificacion> findByTipo(Notificacion.TipoNotificacion tipo);
}
