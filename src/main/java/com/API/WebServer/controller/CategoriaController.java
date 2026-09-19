package com.API.WebServer.controller;

import com.API.WebServer.dto.CategoriaResponse;
import com.API.WebServer.model.Categoria;
import com.API.WebServer.service.CategoriaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public List<CategoriaResponse> listar() {
        return categoriaService.listar();
    }
    @GetMapping("/{id}")
    public CategoriaResponse buscarPorId(@PathVariable Integer id) {
        return categoriaService.buscarPorId(id);
    }

    @PostMapping
    public int guardar(@RequestBody Categoria categoria) {
        return categoriaService.guardar(categoria);
    }

    @PutMapping("/{id}")
    public int actualizar(@PathVariable Integer id, @RequestBody Categoria categoria) {
        return categoriaService.actualizar(id, categoria);
    }

    @DeleteMapping("/{id}")
    public int eliminar(@PathVariable Integer id) {
        return categoriaService.eliminar(id);
    }
}