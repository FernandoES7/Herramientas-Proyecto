package com.API.WebServer.model;

public record Sucursal(
        Integer idSucursal,
        String nombre,
        String direccion,
        String telefono
) {
}
