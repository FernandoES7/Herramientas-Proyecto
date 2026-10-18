package com.api.webserver.mapper;

import com.api.webserver.dto.CategoriaResponse;
import com.api.webserver.model.Categoria;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoriaMapper {

    public CategoriaResponse toResponse(Categoria categoria) {
        return CategoriaResponse.builder()
                .idCategoria(categoria.getIdCategoria())
                .nombre(categoria.getNombre())
                .descripcion(categoria.getDescripcion())
                .build();
    }


}