package com.API.WebServer.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.API.WebServer.dto.ProveedorRequest;
import com.API.WebServer.dto.ProveedorResponse;
import com.API.WebServer.service.ProveedorService;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @GetMapping
    public List<ProveedorResponse> listar() {
        return proveedorService.listar();
    }

    @GetMapping(params = "ruc")
    public List<ProveedorResponse> filtrarPorRuc(@RequestParam("ruc") String ruc) {
        return proveedorService.filtrarPorRuc(ruc);
    }

    @GetMapping("/{idProveedor}")
    public ProveedorResponse buscarPorId(@PathVariable("idProveedor") Integer idProveedor) {
        return proveedorService.buscarPorId(idProveedor);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProveedorResponse insertar(@RequestBody ProveedorRequest request) {
        return proveedorService.insertar(request);
    }

    @PutMapping("/{idProveedor}")
    public ProveedorResponse actualizar(@PathVariable("idProveedor") Integer idProveedor,
                                        @RequestBody ProveedorRequest request) {
        return proveedorService.actualizar(idProveedor, request);
    }

    @DeleteMapping("/{idProveedor}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable("idProveedor") Integer idProveedor) {
        proveedorService.eliminar(idProveedor);
    }
}