package com.API.WebServer.repository;

import com.API.WebServer.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    Optional<Categoria> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
    List<Categoria> findByNombreContainingIgnoreCaseOrderByNombreAsc(String nombre);
}