package com.API.WebServer.controller;

import com.API.WebServer.dto.SucursalCreateRequest;
import com.API.WebServer.dto.SucursalResponse;
import com.API.WebServer.dto.SucursalUpDateRequest;
import com.API.WebServer.service.SucursalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
@RequiredArgsConstructor
public class SucursalController {

    private final SucursalService sucursalService;

    @GetMapping
    public List<SucursalResponse> listar() {
        return sucursalService.listar();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SucursalResponse insertar(@Valid @RequestBody com.API.WebServer.dto.SucursalCreateRequest request) {
        return sucursalService.insertar(request);
    }

    @PutMapping("/{idSucursal}")
    public SucursalResponse actualizar(@PathVariable("idSucursal") Long idSucursal,
                                       @Valid @RequestBody SucursalUpDateRequest request) {
        return sucursalService.actualizar(idSucursal, request);
    }

    @DeleteMapping("/{idSucursal}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable("idSucursal") Long idSucursal) {
        sucursalService.eliminar(idSucursal);
    }
}