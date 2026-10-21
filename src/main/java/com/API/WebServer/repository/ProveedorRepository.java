package com.API.WebServer.repository;

import java.util.List;
import java.util.Optional;

import com.API.WebServer.model.Proveedor;

public interface ProveedorRepository {

    List<Proveedor> listar();

    Optional<Proveedor> buscarPorId(Integer idProveedor);

    Optional<Proveedor> buscarPorRuc(String ruc);

    List<Proveedor> buscarPorRazonSocial(String razonSocial);

    boolean existePorRuc(String ruc);

    Proveedor guardar(Proveedor proveedor);

    Proveedor actualizar(Proveedor proveedor);

    int eliminar(Integer idProveedor);
}