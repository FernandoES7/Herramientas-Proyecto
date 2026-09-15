package com.API.WebServer.service;

import com.API.WebServer.model.Producto;
import com.API.WebServer.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    // Lista todos los productos
    public List<Producto> listar() {
        return productoRepository.listar();
    }

    // Busca un producto por ID
    public Optional<Producto> buscarPorId(Integer idProducto) {
        return productoRepository.buscarPorId(idProducto);
    }

    // Registra un nuevo producto
    public Producto guardar(Producto producto) {
        return productoRepository.guardar(producto);
    }

    // Actualiza un producto
    public Producto actualizar(Producto producto) {
        return productoRepository.actualizar(producto);
    }

    // Elimina un producto
    public void eliminar(Integer idProducto) {
        productoRepository.eliminar(idProducto);
    }
}