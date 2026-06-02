package com.gingapc.resena.repository;

import com.gingapc.resena.model.Resena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Long> {

    // todas las resenas de un producto
    List<Resena> findByProductoId(Long productoId);

    // todas las resenas que dejo un usuario
    List<Resena> findByUsuarioId(Long usuarioId);

    // buscar si el usuario ya reseno ese producto (para evitar duplicados)
    Optional<Resena> findByUsuarioIdAndProductoId(Long usuarioId, Long productoId);

    // contar cuantas resenas tiene un producto
    long countByProductoId(Long productoId);

    // calcular el promedio de calificacion de un producto
    @Query("SELECT AVG(r.calificacion) FROM Resena r WHERE r.productoId = :productoId")
    Double calcularPromedioPorProducto(Long productoId);
}
