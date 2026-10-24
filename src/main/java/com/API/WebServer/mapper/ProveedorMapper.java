package com.API.WebServer.mapper;

import com.API.WebServer.dto.ProveedorResponse;
import com.API.WebServer.model.Proveedor;
import org.springframework.stereotype.Component;

@Component
public class ProveedorMapper {

    public ProveedorResponse toResponse(Proveedor proveedor) {
        return new ProveedorResponse(
                proveedor.getIdProveedor(),
                proveedor.getRazonSocial(),
                proveedor.getRuc(),
                proveedor.getTelefono(),
                proveedor.getCorreo(),
                proveedor.getDireccion()
        );
    }
}