package com.API.WebServer.repository;

import com.API.WebServer.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
    Optional<Cliente> findByDni(String dni);
}
