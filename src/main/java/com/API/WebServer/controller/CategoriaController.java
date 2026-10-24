package com.API.WebServer.controller;

import com.API.WebServer.dto.CategoriaCreateRequest;
import com.API.WebServer.dto.CategoriaResponse;
import com.API.WebServer.dto.CategoriaUpdateRequest;
import com.API.WebServer.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public List<CategoriaResponse> listar() {
        return categoriaService.listar();
    }

    @GetMapping(params = "nombre")
    public List<CategoriaResponse> buscarPorNombre(@RequestParam("nombre") String nombre) {
        return categoriaService.buscarPorNombre(nombre);
    }

    @GetMapping("/{idCategoria}")
    public CategoriaResponse buscarPorId(@PathVariable("idCategoria") Integer idCategoria) {
        return categoriaService.buscarPorId(idCategoria);
    }

    @GetMapping("/exacto/{nombre}")
    public CategoriaResponse buscarPorNombreExacto(@PathVariable("nombre") String nombre) {
        return categoriaService.buscarPorNombreExacto(nombre);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaResponse insertar(@Valid @RequestBody CategoriaCreateRequest request) {
        return categoriaService.insertar(request);
    }

    @PutMapping("/{idCategoria}")
    public CategoriaResponse actualizar(@PathVariable("idCategoria") Integer idCategoria,
                                        @Valid @RequestBody CategoriaUpdateRequest request) {
        return categoriaService.actualizar(idCategoria, request);
    }

    @DeleteMapping("/{idCategoria}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable("idCategoria") Integer idCategoria) {
        categoriaService.eliminar(idCategoria);
    }
}