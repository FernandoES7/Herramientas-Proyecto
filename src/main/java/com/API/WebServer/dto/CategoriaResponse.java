package com.API.WebServer.dto;

public record CategoriaResponse(
        Integer idCategoria,
        String nombre,
        String descripcion
) {
}