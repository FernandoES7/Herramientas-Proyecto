package com.api.webserver.mapper;

import com.api.webserver.dto.ProductoResponse;
import com.api.webserver.model.Producto;
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