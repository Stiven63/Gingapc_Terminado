package com.gingapc.catalogo.service;

import com.gingapc.catalogo.dto.ProductoDto;
import com.gingapc.catalogo.exception.ProductoNotFoundException;
import com.gingapc.catalogo.model.Producto;
import com.gingapc.catalogo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // obtener todos los productos activos del catalogo
    public List<Producto> obtenerTodos() {
        return productoRepository.findByActivoTrue();
    }

    // buscar producto por id
    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con id: " + id));
    }

    // productos con stock disponible para comprar
    public List<Producto> obtenerDisponibles() {
        return productoRepository.findByActivoTrueAndStockGreaterThan(0);
    }

    // productos que se agotaron (stock = 0)
    public List<Producto> obtenerAgotados() {
        return productoRepository.findByActivoTrueAndStock(0);
    }

    // filtrar productos por categoria (GPU, CPU, RAM, etc)
    public List<Producto> obtenerPorCategoria(String categoria) {
        return productoRepository.findByActivoTrueAndCategoria(categoria);
    }

    // filtrar por marca
    public List<Producto> obtenerPorMarca(String marca) {
        return productoRepository.findByActivoTrueAndMarca(marca);
    }

    // crear producto nuevo
    public Producto crear(ProductoDto dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setCategoria(dto.getCategoria());
        producto.setMarca(dto.getMarca());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        return productoRepository.save(producto);
    }

    // actualizar producto existente
    public Producto actualizar(Long id, ProductoDto dto) {
        Producto producto = obtenerPorId(id);
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setCategoria(dto.getCategoria());
        producto.setMarca(dto.getMarca());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        return productoRepository.save(producto);
    }

    // actualizar solo el stock, lo usa el carrito cuando agrega productos
    // cantidad positiva = agregar stock, negativa = descontar
    public Producto actualizarStock(Long id, Integer cantidad) {
        Producto producto = obtenerPorId(id);

        int stockNuevo = producto.getStock() + cantidad;

        // regla de negocio: el stock no puede quedar negativo
        if (stockNuevo < 0) {
            throw new RuntimeException("Stock insuficiente. Stock actual: " + producto.getStock()
                    + ", cantidad solicitada: " + Math.abs(cantidad));
        }

        producto.setStock(stockNuevo);
        return productoRepository.save(producto);
    }

    // eliminar producto (se desactiva, no se borra de la bd)
    public String eliminar(Long id) {
        Producto producto = obtenerPorId(id);
        producto.setActivo(false);
        productoRepository.save(producto);
        return "Producto desactivado correctamente";
    }
}
