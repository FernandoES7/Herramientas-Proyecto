package com.api.webserver.mapper;
import com.api.webserver.dto.SucursalResponse;
import com.api.webserver.model.Sucursal;
import org.springframework.stereotype.Component;

@Component
public class SucursalMapper {

    private SucursalMapper() {

    }

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
