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

    default List<Proveedor> listar() {
        return findAll();
    }

    default Optional<Proveedor> buscarPorId(Integer idProveedor) {
        return findById(idProveedor);
    }


    default Optional<Proveedor> buscarPorRuc(String ruc) {
        return findByRuc(ruc);
    }

    default boolean existePorRuc(String ruc) {
        return existsByRuc(ruc);
    }

    default Proveedor guardar(Proveedor proveedor) {
        return save(proveedor);
    }

    default Proveedor actualizar(Proveedor proveedor) {
        return save(proveedor);
    }

    default int eliminar(Integer idProveedor) {
        if (!existsById(idProveedor)) {
            return 0;
        }
        deleteById(idProveedor);
        return 1;
    }
}