package com.API.WebServer.controller;

import com.API.WebServer.dto.ProductoCreateRequest;
import com.API.WebServer.dto.ProductoResponse;
import com.API.WebServer.dto.ProductoUpdateRequest;
import com.API.WebServer.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public List<ProductoResponse> listar() {
        return productoService.listar();
    }

    @GetMapping(params = "idCategoria")
    public List<ProductoResponse> listarPorCategoria(@RequestParam("idCategoria") Long idCategoria) {
        return productoService.listarPorCategoria(idCategoria);
    }

    @GetMapping("/{idProducto}")
    public ProductoResponse buscarPorId(@PathVariable("idProducto") Long idProducto) {
        return productoService.buscarPorId(idProducto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductoResponse insertar(@Valid @RequestBody ProductoCreateRequest request) {
        return productoService.insertar(request);
    }

    @PutMapping("/{idProducto}")
    public ProductoResponse actualizar(@PathVariable("idProducto") Long idProducto,
                                       @Valid @RequestBody ProductoUpdateRequest request) {
        return productoService.actualizar(idProducto, request);
    }

    @DeleteMapping("/{idProducto}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable("idProducto") Long idProducto) {
        productoService.eliminar(idProducto);
    }
}