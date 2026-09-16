package com.API.WebServer.repository.jdbc;

import com.API.WebServer.model.Cliente;
import com.API.WebServer.repository.ClienteRepository;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcClienteRepository implements ClienteRepository {

    private static final RowMapper<Cliente> CLIENTE_ROW_MAPPER = (rs, rowNum) -> new Cliente(
            rs.getInt("id_cliente"),
            rs.getString("nombres"),
            rs.getString("apellidos"),
            rs.getString("dni"),
            rs.getString("telefono"),
            rs.getString("direccion"),
            rs.getObject("fecha_registro", java.time.LocalDate.class)
    );

    private final JdbcTemplate jdbcTemplate;

    @Override
    public int eliminar(Integer idCliente) {
        return jdbcTemplate.update("DELETE FROM Cliente WHERE id_cliente = ?", idCliente);
    }

    @Override
    public int actualizar(Cliente cliente) {
        return jdbcTemplate.update("""
                UPDATE Cliente
                SET nombres = ?, apellidos = ?, dni = ?, telefono = ?, direccion = ?, fecha_registro = ?
                WHERE id_cliente = ?
                """, cliente.nombres(), cliente.apellidos(), cliente.dni(), cliente.telefono(),
                cliente.direccion(), cliente.fechaRegistro(), cliente.idCliente());
    }

    public JdbcClienteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Cliente> listar() {
        return jdbcTemplate.query("""
                SELECT id_cliente, nombres, apellidos, dni, telefono, direccion, fecha_registro
                FROM Cliente
                ORDER BY id_cliente
                """, CLIENTE_ROW_MAPPER);
    }

    @Override
    public List<Cliente> filtrarPorDni(String dni) {
        return jdbcTemplate.query("""
                SELECT id_cliente, nombres, apellidos, dni, telefono, direccion, fecha_registro
                FROM Cliente
                WHERE dni = ?
                ORDER BY id_cliente
                """, CLIENTE_ROW_MAPPER, dni);
    }
}
