package com.API.WebServer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    // Identificador único del producto
    private Integer idProducto;

    // Nombre del producto
    private String nombre;

    // Descripción del producto
    private String descripcion;

    // Precio de venta
    private BigDecimal precio;

    // Cantidad disponible
    private Integer stock;

    // Categoría a la que pertenece
    private Integer idCategoria;
}