package com.API.WebServer.model;

public record Proveedor(
        Integer idProveedor,
        String razonSocial,
        String ruc,
        String telefono,
        String direccion
) {
}
