package com.API.WebServer.repository;

import com.API.WebServer.model.Categoria;
import java.util.List;

public interface CategoriaRepository {
    List<Categoria> listar();
    int guardar(Categoria categoria);
    Categoria buscarPorId(Integer id);
    int actualizar(Integer id, Categoria categoria);
    int eliminar(Integer id);
}