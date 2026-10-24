package com.API.WebServer.dto;

public record ProveedorRequest(
        String razonSocial,
        String ruc,
        String telefono,
        String direccion
) {
}