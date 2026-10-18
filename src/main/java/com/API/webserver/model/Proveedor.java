package com.api.webserver.model;

public record Proveedor(
        Integer idProveedor,
        String razonSocial,
        String ruc,
        String telefono,
        String direccion
) {
}
