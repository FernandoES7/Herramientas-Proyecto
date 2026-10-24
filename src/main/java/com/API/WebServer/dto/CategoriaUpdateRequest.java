package com.API.WebServer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoriaUpdateRequest(
        @NotBlank(message = "El nombre no puede estar vacio")
        @Size(max = 50, message = "El nombre no puede superar los 50 caracteres")
        String nombre,

        @NotBlank(message = "La descripcion no puede estar vacia")
        @Size(max = 50, message = "La descripcion no puede superar los 50 caracteres")
        String descripcion
) {
}