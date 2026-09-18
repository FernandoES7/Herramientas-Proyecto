package com.API.WebServer.dto;

public record ProveedorResponse(
        Integer idProveedor,
        String razonSocial,
        String ruc,
        String telefono,
        String direccion
) {
}