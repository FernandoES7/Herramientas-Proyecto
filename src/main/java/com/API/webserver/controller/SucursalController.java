package com.api.webserver.controller;
import com.api.webserver.dto.SucursalResponse;
import com.api.webserver.model.Sucursal;
import com.api.webserver.service.SucursalService;
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
