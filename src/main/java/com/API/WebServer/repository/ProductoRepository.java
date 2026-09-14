package com.API.WebServer.repository;

import com.API.WebServer.model.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository {

    // Obtener todos los productos
    List<Producto> listar();

    // Buscar un producto por su ID
    Optional<Producto> buscarPorId(Integer idProducto);

    // Registrar un nuevo producto
    Producto guardar(Producto producto);

    // Actualizar un producto existente
    Producto actualizar(Producto producto);

    // Eliminar un producto por su ID
    void eliminar(Integer idProducto);
}