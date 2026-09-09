package com.API.WebServer.mapper;

import com.API.WebServer.dto.ClienteResponse;
import com.API.WebServer.model.Cliente;
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
