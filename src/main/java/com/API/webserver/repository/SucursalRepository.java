package com.api.webserver.repository;
import com.api.webserver.model.Sucursal;

import java.util.List;
import java.util.Optional;

public interface SucursalRepository {
    List<Sucursal> findAll();
    Optional<Sucursal> findById(Integer id);
    Sucursal save(Sucursal sucursal);
    void deleteById(Integer id);
}
