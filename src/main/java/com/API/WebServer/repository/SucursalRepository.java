package com.API.WebServer.repository;
import com.API.WebServer.model.Sucursal;

import java.util.List;
import java.util.Optional;

public interface SucursalRepository {
    List<Sucursal> findAll();
    Optional<Sucursal> findById(Integer id);
    Sucursal save(Sucursal sucursal);
    void deleteById(Integer id);
}
