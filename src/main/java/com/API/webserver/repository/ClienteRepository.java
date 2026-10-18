package com.api.webserver.repository;

import com.api.webserver.model.Cliente;
import java.util.List;

public interface ClienteRepository {

    List<Cliente> listar();

    List<Cliente> filtrarPorDni(String dni);

    int eliminar(Integer idCliente);

    int actualizar(Cliente cliente);

    Cliente insertar(Cliente cliente);
}
