package com.API.WebServer.service;

import com.API.WebServer.model.Categoria;
import com.API.WebServer.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public List<Categoria> listar() {
        return categoriaRepository.listar();
    }

    public Optional<Categoria> buscarPorId(Integer idCategoria) {
        return categoriaRepository.buscarPorId(idCategoria);
    }

    public Categoria guardar(Categoria categoria) {
        return categoriaRepository.guardar(categoria);
    }

    public Categoria actualizar(Categoria categoria) {
        return categoriaRepository.actualizar(categoria);
    }

    public void eliminar(Integer idCategoria) {
        categoriaRepository.eliminar(idCategoria);
    }
}