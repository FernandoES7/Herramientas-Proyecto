package com.API.WebServer.mapper;

import com.API.WebServer.dto.ProductoResponse;
import com.API.WebServer.model.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    // Convierte Producto en ProductoResponse
    public ProductoResponse toResponse(Producto producto) {

        return ProductoResponse.builder()
                .idProducto(producto.getIdProducto())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .precio(producto.getPrecio())
                .stock(producto.getStock())
                .idCategoria(producto.getIdCategoria())
                .build();
    }
}