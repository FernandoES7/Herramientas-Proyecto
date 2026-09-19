package com.API.WebServer.mapper;

import com.API.WebServer.dto.CategoriaResponse;
import com.API.WebServer.model.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public CategoriaResponse toResponse(Categoria categoria) {
        return new CategoriaResponse(
                categoria.idCategoria(),
                categoria.nombre(),
                categoria.descripcion()
        );
    }
}