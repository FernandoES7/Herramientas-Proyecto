package com.API.WebServer.repository;

import java.util.List;
import java.util.Optional;

import com.API.WebServer.model.Proveedor;

public interface ProveedorRepository {

    List<Proveedor> listar();

    Optional<Proveedor> buscarPorId(Integer idProveedor);

    List<Proveedor> filtrarPorRuc(String ruc);

    Proveedor insertar(Proveedor proveedor);

    int actualizar(Proveedor proveedor);

    int eliminar(Integer idProveedor);
}