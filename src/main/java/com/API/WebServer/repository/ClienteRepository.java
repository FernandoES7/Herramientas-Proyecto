package com.API.WebServer.repository;

import com.API.WebServer.model.Cliente;
import java.util.List;

public interface ClienteRepository {

    List<Cliente> listar();

    List<Cliente> filtrarPorDni(String dni);

    int eliminar(Integer idCliente);

    int actualizar(Cliente cliente);
}
