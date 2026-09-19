package com.API.WebServer.controller;

import com.API.WebServer.dto.ProductoResponse;
import com.API.WebServer.mapper.ProductoMapper;
import com.API.WebServer.model.Producto;
import com.API.WebServer.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;
    private final ProductoMapper productoMapper;

    // Listar todos los productos
    @GetMapping
    public ResponseEntity<List<ProductoResponse>> listar() {

        List<ProductoResponse> productos = productoService.listar()
                .stream()
                .map(productoMapper::toResponse)
                .toList();

        return ResponseEntity.ok(productos);
    }

    // Buscar producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> buscarPorId(@PathVariable Integer id) {

        return productoService.buscarPorId(id)
                .map(productoMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Registrar producto
    @PostMapping
    public ResponseEntity<ProductoResponse> guardar(@RequestBody Producto producto) {

        Producto productoGuardado = productoService.guardar(producto);

        return ResponseEntity.ok(
                productoMapper.toResponse(productoGuardado)
        );
    }

    // Actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> actualizar(
            @PathVariable Integer id,
            @RequestBody Producto producto) {

        producto.setIdProducto(id);

        Producto productoActualizado = productoService.actualizar(producto);

        return ResponseEntity.ok(
                productoMapper.toResponse(productoActualizado)
        );
    }

    // Eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {

        productoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}