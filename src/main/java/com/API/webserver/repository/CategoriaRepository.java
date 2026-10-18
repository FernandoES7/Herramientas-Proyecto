package com.api.webserver.repository;

import com.api.webserver.model.Categoria;
import java.util.List;
import java.util.Optional;

public interface CategoriaRepository {

    List<Categoria> listar();
    Optional<Categoria> buscarPorId(Integer idCategoria);
    Categoria guardar(Categoria categoria);
    Categoria actualizar(Categoria categoria);
    void eliminar(Integer idCategoria);
}