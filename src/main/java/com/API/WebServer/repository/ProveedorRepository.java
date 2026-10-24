package com.API.WebServer.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.API.WebServer.model.Proveedor;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {

    Optional<Proveedor> findByRuc(String ruc);

    boolean existsByRuc(String ruc);

    @Query("SELECT p FROM Proveedor p WHERE LOWER(p.razonSocial) LIKE LOWER(CONCAT('%', :razonSocial, '%')) ORDER BY p.razonSocial")
    List<Proveedor> buscarPorRazonSocial(@Param("razonSocial") String razonSocial);
}
