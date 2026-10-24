package com.API.WebServer.mapper;

import com.API.WebServer.dto.ProductoResponse;
import com.API.WebServer.model.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public ProductoResponse toResponse(Producto producto) {
        return new ProductoResponse(
                producto.getIdProducto(),
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getIdCategoria()
        );
    }
}