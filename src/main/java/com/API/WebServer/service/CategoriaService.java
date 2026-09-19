package com.API.WebServer.service;

import com.API.WebServer.dto.CategoriaResponse;
import com.API.WebServer.mapper.CategoriaMapper;
import com.API.WebServer.model.Categoria;
import com.API.WebServer.repository.CategoriaRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    public CategoriaService(CategoriaRepository categoriaRepository, CategoriaMapper categoriaMapper) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaMapper = categoriaMapper;
    }

    public List<CategoriaResponse> listar() {
        return categoriaRepository.listar().stream()
                .map(categoriaMapper::toResponse)
                .toList();
    }
    public CategoriaResponse buscarPorId(Integer id) {
        Categoria categoria = categoriaRepository.buscarPorId(id);
        return categoria != null ? categoriaMapper.toResponse(categoria) : null;
    }

    @Transactional
    public int guardar(Categoria categoria) {
        return categoriaRepository.guardar(categoria);
    }

    @Transactional
    public int actualizar(Integer id, Categoria categoria) {
        return categoriaRepository.actualizar(id, categoria);
    }

    @Transactional
    public int eliminar(Integer id) {
        return categoriaRepository.eliminar(id);
    }


}