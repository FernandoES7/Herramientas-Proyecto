package com.api.webserver.repository;

import java.util.List;

import com.api.webserver.model.Proveedor;

public interface ProveedorRepository {

    List<Proveedor> listar();
}