package com.API.WebServer.dto;

import java.math.BigDecimal;

public record ProductoResponse(
        Long idProducto,
        String nombre,
        String descripcion,
        BigDecimal precio,
        Integer stock,
        Long idCategoria
) {
}