package com.gingapc.carrito.service;

import com.gingapc.carrito.client.CatalogoClient;
import com.gingapc.carrito.dto.AgregarItemRequest;
import com.gingapc.carrito.dto.CarritoResponse;
import com.gingapc.carrito.dto.ProductoResponse;
import com.gingapc.carrito.exception.CarritoNotFoundException;
import com.gingapc.carrito.model.Carrito;
import com.gingapc.carrito.model.ItemCarrito;
import com.gingapc.carrito.repository.CarritoRepository;
import com.gingapc.carrito.repository.ItemCarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CarritoService {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ItemCarritoRepository itemCarritoRepository;

    // feign client que se comunica con catalogo-service
    @Autowired
    private CatalogoClient catalogoClient;

    // obtener el carrito del usuario, si no existe se crea uno vacio
    public CarritoResponse obtenerCarrito(Long usuarioId) {
        Carrito carrito = carritoRepository.findByUsuarioId(usuarioId)
                .orElseGet(() -> crearCarritoVacio(usuarioId));

        return new CarritoResponse(carrito.getId(), carrito.getUsuarioId(),
                carrito.getItems(), carrito.getCreadoEn());
    }

    // agregar producto al carrito
    // aqui es donde se comunica con catalogo-service para verificar el stock
    @Transactional
    public CarritoResponse agregarProducto(Long usuarioId, AgregarItemRequest request) {

        // llamada feign al catalogo-service para obtener los datos del producto
        ProductoResponse producto = catalogoClient.obtenerProducto(request.getProductoId());

        // regla de negocio: verificar que hay stock suficiente
        if (producto.getStock() < request.getCantidad()) {
            throw new RuntimeException("Stock insuficiente para '" + producto.getNombre()
                    + "'. Stock disponible: " + producto.getStock());
        }

        // regla de negocio: no se puede agregar un producto desactivado
        if (producto.getActivo() == null || !producto.getActivo()) {
            throw new RuntimeException("El producto '" + producto.getNombre()
                    + "' no esta disponible en el catalogo");
        }

        Carrito carrito = carritoRepository.findByUsuarioId(usuarioId)
                .orElseGet(() -> crearCarritoVacio(usuarioId));

        // si el producto ya esta en el carrito se suma la cantidad
        Optional<ItemCarrito> itemExistente = itemCarritoRepository
                .findByCarritoIdAndProductoId(carrito.getId(), request.getProductoId());

        if (itemExistente.isPresent()) {
            ItemCarrito item = itemExistente.get();
            item.setCantidad(item.getCantidad() + request.getCantidad());
            itemCarritoRepository.save(item);
        } else {
            // si no existe se crea un item nuevo
            ItemCarrito nuevoItem = new ItemCarrito();
            nuevoItem.setCarrito(carrito);
            nuevoItem.setProductoId(producto.getId());
            nuevoItem.setNombreProducto(producto.getNombre());
            nuevoItem.setPrecioUnitario(producto.getPrecio());
            nuevoItem.setCantidad(request.getCantidad());
            itemCarritoRepository.save(nuevoItem);
        }

        // recargar el carrito actualizado desde la bd
        Carrito actualizado = carritoRepository.findById(carrito.getId()).get();
        return new CarritoResponse(actualizado.getId(), actualizado.getUsuarioId(),
                actualizado.getItems(), actualizado.getCreadoEn());
    }

    // eliminar un item especifico del carrito
    @Transactional
    public CarritoResponse eliminarItem(Long usuarioId, Long itemId) {
        Carrito carrito = carritoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new CarritoNotFoundException(
                        "No se encontro carrito para el usuario: " + usuarioId));

        ItemCarrito item = itemCarritoRepository.findById(itemId)
                .orElseThrow(() -> new CarritoNotFoundException(
                        "Item no encontrado con id: " + itemId));

        // verificar que el item pertenece al carrito del usuario
        if (!item.getCarrito().getId().equals(carrito.getId())) {
            throw new RuntimeException("El item no pertenece al carrito del usuario");
        }

        itemCarritoRepository.delete(item);

        Carrito actualizado = carritoRepository.findById(carrito.getId()).get();
        return new CarritoResponse(actualizado.getId(), actualizado.getUsuarioId(),
                actualizado.getItems(), actualizado.getCreadoEn());
    }

    // vaciar todos los items del carrito
    @Transactional
    public void vaciarCarrito(Long usuarioId) {
        Carrito carrito = carritoRepository.findByUsuarioId(usuarioId)
                .orElseThrow(() -> new CarritoNotFoundException(
                        "No se encontro carrito para el usuario: " + usuarioId));

        carrito.getItems().clear();
        carritoRepository.save(carrito);
    }

    // helper para crear un carrito vacio cuando el usuario no tiene uno
    private Carrito crearCarritoVacio(Long usuarioId) {
        Carrito nuevo = new Carrito();
        nuevo.setUsuarioId(usuarioId);
        return carritoRepository.save(nuevo);
    }
}
