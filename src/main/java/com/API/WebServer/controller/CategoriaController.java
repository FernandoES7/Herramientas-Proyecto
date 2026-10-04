package com.API.WebServer.controller;

import com.API.WebServer.dto.CategoriaResponse;
import com.API.WebServer.mapper.CategoriaMapper;
import com.API.WebServer.model.Categoria;
import com.API.WebServer.service.CategoriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final CategoriaMapper categoriaMapper;

    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> listar() {
        List<CategoriaResponse> categorias = categoriaService.listar()
                .stream()
                .map(categoriaMapper::toResponse)
                .toList();
        return ResponseEntity.ok(categorias);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> buscarPorId(@PathVariable Integer id) {
        return categoriaService.buscarPorId(id)
                .map(categoriaMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> guardar(@RequestBody Categoria categoria) {
        Categoria categoriaGuardada = categoriaService.guardar(categoria);
        return ResponseEntity.ok(categoriaMapper.toResponse(categoriaGuardada));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponse> actualizar(
            @PathVariable Integer id,
            @RequestBody Categoria categoria) {

        categoria.setIdCategoria(id);
        Categoria categoriaActualizada = categoriaService.actualizar(categoria);
        return ResponseEntity.ok(categoriaMapper.toResponse(categoriaActualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        categoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}