package com.api.webserver.dto;

public record SucursalResponse(
        Integer idSucursal,
        String nombre,
        String direccion,
        String telefono,
        String ciudad
) {
}