package com.API.WebServer.mapper;
import com.API.WebServer.dto.SucursalResponse;
import com.API.WebServer.model.Sucursal;
public class SucursalMapper {
    public static SucursalResponse toResponse(Sucursal sucursal) {
        return new SucursalResponse(
                sucursal.idSucursal(),
                sucursal.nombre(),
                sucursal.direccion(),
                sucursal.telefono(),
                sucursal.ciudad()
        );
    }
}
