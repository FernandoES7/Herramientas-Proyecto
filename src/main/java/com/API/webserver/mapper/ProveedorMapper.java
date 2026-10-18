package com.api.webserver.mapper;

import com.api.webserver.dto.ProveedorResponse;
import com.api.webserver.model.Proveedor;
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