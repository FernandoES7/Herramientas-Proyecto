package com.API.WebServer.repository.jdbc;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.API.WebServer.model.Proveedor;
import com.API.WebServer.repository.ProveedorRepository;

@Repository
public class JdbcProveedorRepository implements ProveedorRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcProveedorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Proveedor> listar() {
        return jdbcTemplate.query("""
                SELECT id_proveedor, razon_social, ruc, telefono, direccion
                FROM proveedor
                ORDER BY id_proveedor
                """, (rs, rowNum) -> new Proveedor(
                rs.getInt("id_proveedor"),
                rs.getString("razon_social"),
                rs.getString("ruc"),
                rs.getString("telefono"),
                rs.getString("direccion")
        ));
    }
}