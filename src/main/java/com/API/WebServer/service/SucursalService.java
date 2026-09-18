package com.API.WebServer.service;

import com.API.WebServer.dto.SucursalResponse;
import com.API.WebServer.model.Sucursal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SucursalService {

    private final List<Sucursal> sucursales = new ArrayList<>();

    public List<SucursalResponse> listar() {
        return sucursales.stream()
                .map(this::toResponse)
                .toList();
    }

    public SucursalResponse crear(Sucursal sucursal) {
        sucursales.add(sucursal);
        return toResponse(sucursal);
    }

    private SucursalResponse toResponse(Sucursal sucursal) {
        return new SucursalResponse(
                sucursal.idSucursal(),
                sucursal.nombre(),
                sucursal.direccion(),
                sucursal.telefono()
        );
    }
}
