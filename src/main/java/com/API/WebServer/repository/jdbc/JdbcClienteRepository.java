package com.API.WebServer.repository.jdbc;

import com.API.WebServer.model.Cliente;
import com.API.WebServer.repository.ClienteRepository;
import java.util.List;
import java.sql.Statement;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.support.GeneratedKeyHolder;
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
    public Cliente insertar(Cliente cliente) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            var statement = connection.prepareStatement("""
                    INSERT INTO Cliente (nombres, apellidos, dni, telefono, direccion, fecha_registro)
                    VALUES (?, ?, ?, ?, ?, COALESCE(?, CURRENT_DATE))
                    """, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, cliente.nombres());
            statement.setString(2, cliente.apellidos());
            statement.setString(3, cliente.dni());
            statement.setString(4, cliente.telefono());
            statement.setString(5, cliente.direccion());
            statement.setDate(6, cliente.fechaRegistro() == null
                    ? null : java.sql.Date.valueOf(cliente.fechaRegistro()));
            return statement;
        }, keyHolder);
        Number id = keyHolder.getKey();
        if (id == null) {
            throw new DataRetrievalFailureException("No se obtuvo el ID del cliente insertado");
        }
        return jdbcTemplate.queryForObject("""
                SELECT id_cliente, nombres, apellidos, dni, telefono, direccion, fecha_registro
                FROM Cliente WHERE id_cliente = ?
                """, CLIENTE_ROW_MAPPER, id.intValue());
    }

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
