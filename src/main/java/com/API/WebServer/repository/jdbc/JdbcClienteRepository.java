package com.API.WebServer.repository.jdbc;

import com.API.WebServer.model.Cliente;
import com.API.WebServer.repository.ClienteRepository;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcClienteRepository implements ClienteRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcClienteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Cliente> listar() {
        return jdbcTemplate.query("""
                SELECT id_cliente, nombres, apellidos, dni, telefono, direccion, fecha_registro
                FROM Cliente
                ORDER BY id_cliente
                """, (rs, rowNum) -> new Cliente(
                rs.getInt("id_cliente"),
                rs.getString("nombres"),
                rs.getString("apellidos"),
                rs.getString("dni"),
                rs.getString("telefono"),
                rs.getString("direccion"),
                rs.getObject("fecha_registro", java.time.LocalDate.class)
        ));
    }
}
