package com.API.WebServer.controller;

import java.util.List;

import com.API.WebServer.dto.CategoriaCreateRequest;
import com.API.WebServer.dto.CategoriaResponse;
import com.API.WebServer.dto.CategoriaUpdateRequest;
import com.API.WebServer.service.CategoriaService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> listar() {
        return ResponseEntity.ok(categoriaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(categoriaService.buscarPorId(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<CategoriaResponse>> buscarPorNombre(
            @RequestParam String nombre) {
        return ResponseEntity.ok(categoriaService.buscarPorNombre(nombre));
    }

    @GetMapping("/exacto/{nombre}")
    public ResponseEntity<CategoriaResponse> buscarPorNombreExacto(@PathVariable String nombre) {
        return ResponseEntity.ok(categoriaService.buscarPorNombreExacto(nombre));
    }

    @PostMapping
    public ResponseEntity<CategoriaResponse> crear(@Valid @RequestBody CategoriaCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.insertar(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponse> actualizar(
            @PathVariable Integer id, @Valid @RequestBody CategoriaUpdateRequest request) {
        return ResponseEntity.ok(categoriaService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        categoriaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}