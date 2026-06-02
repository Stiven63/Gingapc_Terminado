package com.gingapc.notificacion.service;

import com.gingapc.notificacion.dto.NotificacionRequest;
import com.gingapc.notificacion.exception.NotificacionNotFoundException;
import com.gingapc.notificacion.model.Notificacion;
import com.gingapc.notificacion.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    // crear y guardar una notificacion nueva
    // en produccion aqui iria el envio real por email con JavaMailSender
    // por ahora solo se guarda en la bd
    public Notificacion enviar(NotificacionRequest request) {
        Notificacion notificacion = new Notificacion();
        notificacion.setUsuarioId(request.getUsuarioId());
        notificacion.setCorreoDestino(request.getCorreoDestino());
        notificacion.setTipo(request.getTipo());
        notificacion.setAsunto(request.getAsunto());
        notificacion.setMensaje(request.getMensaje());

        return notificacionRepository.save(notificacion);
    }

    // obtener todas las notificaciones de un usuario
    public List<Notificacion> obtenerPorUsuario(Long usuarioId) {
        return notificacionRepository.findByUsuarioId(usuarioId);
    }

    // obtener solo las notificaciones no leidas de un usuario
    public List<Notificacion> obtenerNoLeidas(Long usuarioId) {
        return notificacionRepository.findByUsuarioIdAndLeida(usuarioId, false);
    }

    // filtrar por tipo: PEDIDO_CONFIRMADO, OFERTA, etc
    public List<Notificacion> obtenerPorTipo(String tipo) {
        try {
            Notificacion.TipoNotificacion tipoEnum =
                    Notificacion.TipoNotificacion.valueOf(tipo.toUpperCase());
            return notificacionRepository.findByTipo(tipoEnum);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Tipo invalido. Los valores permitidos son: "
                    + "PEDIDO_CONFIRMADO, PEDIDO_EN_CAMINO, PEDIDO_ENTREGADO, OFERTA, ALERTA_STOCK");
        }
    }

    // listar todas las notificaciones
    public List<Notificacion> obtenerTodas() {
        return notificacionRepository.findAll();
    }

    // marcar una notificacion como leida
    public Notificacion marcarComoLeida(Long id) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new NotificacionNotFoundException(
                        "Notificacion no encontrada con id: " + id));

        notificacion.setLeida(true);
        return notificacionRepository.save(notificacion);
    }

    // marcar todas las notificaciones de un usuario como leidas
    public String marcarTodasLeidas(Long usuarioId) {
        List<Notificacion> noLeidas = notificacionRepository
                .findByUsuarioIdAndLeida(usuarioId, false);

        noLeidas.forEach(n -> n.setLeida(true));
        notificacionRepository.saveAll(noLeidas);

        return "Se marcaron " + noLeidas.size() + " notificaciones como leidas";
    }

    // eliminar una notificacion
    public String eliminar(Long id) {
        Notificacion notificacion = notificacionRepository.findById(id)
                .orElseThrow(() -> new NotificacionNotFoundException(
                        "Notificacion no encontrada con id: " + id));

        notificacionRepository.delete(notificacion);
        return "Notificacion eliminada correctamente";
    }
}
