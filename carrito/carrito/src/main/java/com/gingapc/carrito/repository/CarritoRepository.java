package com.gingapc.carrito.repository;

import com.gingapc.carrito.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Long> {

    // buscar el carrito de un usuario especifico
    Optional<Carrito> findByUsuarioId(Long usuarioId);
}
