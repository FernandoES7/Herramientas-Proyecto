package com.API.WebServer.dto;

public record SucursalResponse(
        Long idSucursal,
        String nombre,
        String direccion,
        String telefono,
        String ciudad
        ){

}