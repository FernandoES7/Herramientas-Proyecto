package com.api.webserver.dto;

public record ProveedorResponse(
        Integer idProveedor,
        String razonSocial,
        String ruc,
        String telefono,
        String direccion
) {
}