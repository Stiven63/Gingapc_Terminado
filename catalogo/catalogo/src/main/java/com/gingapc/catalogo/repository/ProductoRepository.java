package com.gingapc.catalogo.repository;

import com.gingapc.catalogo.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    // productos que estan activos en el catalogo
    List<Producto> findByActivoTrue();

    // productos con stock disponible (stock mayor a cero)
    List<Producto> findByActivoTrueAndStockGreaterThan(int stock);

    // productos agotados (stock igual a cero)
    List<Producto> findByActivoTrueAndStock(int stock);

    // filtrar por categoria
    List<Producto> findByActivoTrueAndCategoria(String categoria);

    // filtrar por marca
    List<Producto> findByActivoTrueAndMarca(String marca);
}
