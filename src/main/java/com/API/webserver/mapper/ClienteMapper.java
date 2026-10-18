package com.api.webserver.mapper;

import com.api.webserver.dto.ClienteResponse;
import com.api.webserver.model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public ClienteResponse toResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.idCliente(), cliente.nombres(), cliente.apellidos(),
                cliente.dni(), cliente.telefono(), cliente.direccion(),
                cliente.fechaRegistro()
        );
    }
}
