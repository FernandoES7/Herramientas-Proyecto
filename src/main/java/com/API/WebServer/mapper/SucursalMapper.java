package com.API.WebServer.mapper;

import com.API.WebServer.dto.SucursalResponse;
import com.API.WebServer.model.Sucursal;
import org.springframework.stereotype.Component;

@Component
public class SucursalMapper {

    public SucursalResponse toResponse(Sucursal sucursal) {
        return new SucursalResponse(
                sucursal.getIdSucursal(),
                sucursal.getNombre(),
                sucursal.getDireccion(),
                sucursal.getTelefono(),
                sucursal.getCiudad()
        );
    }
}
