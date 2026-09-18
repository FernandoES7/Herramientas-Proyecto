package com.API.WebServer.dto;

public record SucursalResponse(
        Integer idSucursal,
        String nombre,
        String direccion,
        String telefono
) {
}