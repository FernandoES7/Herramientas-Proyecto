package com.API.WebServer.controller;
import com.API.WebServer.dto.SucursalResponse;
import com.API.WebServer.model.Sucursal;
import com.API.WebServer.service.SucursalService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sucursales")

public class SucursalController {
    private final SucursalService sucursalService;

    public SucursalController(SucursalService sucursalService) {
        this.sucursalService = sucursalService;
    }

    @GetMapping
    public List<SucursalResponse> listar() {
        return sucursalService.listar();
    }

    @PostMapping
    public SucursalResponse crear(@RequestBody Sucursal sucursal) {
        return sucursalService.crear(sucursal);
    }
}
