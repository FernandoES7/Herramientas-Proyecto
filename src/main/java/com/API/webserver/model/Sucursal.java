package com.api.webserver.model;

public record Sucursal(
        Integer idSucursal,
        String nombre,
        String direccion,
        String telefono,
        String ciudad
) {
}