package com.API.WebServer.service;

import com.API.WebServer.dto.CategoriaCreateRequest;
import com.API.WebServer.dto.CategoriaResponse;
import com.API.WebServer.dto.CategoriaUpdateRequest;
import com.API.WebServer.mapper.CategoriaMapper;
import com.API.WebServer.model.Categoria;
import com.API.WebServer.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    public List<CategoriaResponse> listar() {
        return categoriaRepository.findAll().stream()
                .map(categoriaMapper::toResponse)
                .toList();
    }

    public CategoriaResponse buscarPorId(Integer idCategoria) {
        return categoriaMapper.toResponse(buscar(idCategoria));
    }

    public CategoriaResponse buscarPorNombreExacto(String nombre) {
        return categoriaRepository.findByNombre(nombre)
                .map(categoriaMapper::toResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada"));
    }

    public List<CategoriaResponse> buscarPorNombre(String nombre) {
        return categoriaRepository.findByNombreContainingIgnoreCaseOrderByNombreAsc(nombre).stream()
                .map(categoriaMapper::toResponse)
                .toList();
    }

    @Transactional
    public CategoriaResponse insertar(CategoriaCreateRequest request) {
        Categoria categoria = new Categoria(null, request.nombre(), request.descripcion());
        return categoriaMapper.toResponse(guardar(categoria));
    }

    @Transactional
    public CategoriaResponse actualizar(Integer idCategoria, CategoriaUpdateRequest request) {
        Categoria categoria = buscar(idCategoria);
        categoria.setNombre(request.nombre());
        categoria.setDescripcion(request.descripcion());
        return categoriaMapper.toResponse(guardar(categoria));
    }

    @Transactional
    public void eliminar(Integer idCategoria) {
        Categoria categoria = buscar(idCategoria);
        try {
            categoriaRepository.delete(categoria);
            categoriaRepository.flush();
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se puede eliminar la categoría porque tiene productos asociados", ex);
        }
    }

    private Categoria buscar(Integer idCategoria) {
        return categoriaRepository.findById(idCategoria)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada"));
    }

    private Categoria guardar(Categoria categoria) {
        try {
            return categoriaRepository.saveAndFlush(categoria);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "El nombre de la categoría ya existe o los datos son inválidos", ex);
        }
    }
}