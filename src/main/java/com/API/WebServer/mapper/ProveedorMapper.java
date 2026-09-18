package com.API.WebServer.mapper;

import com.API.WebServer.dto.ProveedorResponse;
import com.API.WebServer.model.Proveedor;
import org.springframework.stereotype.Component;

@Component
public class ProveedorMapper {

    public ProveedorResponse toResponse(Proveedor proveedor) {
        return new ProveedorResponse(
                proveedor.idProveedor(),
                proveedor.razonSocial(),
                proveedor.ruc(),
                proveedor.telefono(),
                proveedor.direccion()
        );
    }
}