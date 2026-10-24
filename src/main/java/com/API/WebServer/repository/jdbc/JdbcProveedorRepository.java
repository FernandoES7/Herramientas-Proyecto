package com.API.WebServer.repository.jdbc;

import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.API.WebServer.model.Proveedor;
import com.API.WebServer.repository.ProveedorRepository;

@Repository
public class JdbcProveedorRepository implements ProveedorRepository {

    private static final RowMapper<Proveedor> PROVEEDOR_ROW_MAPPER = (rs, rowNum) -> new Proveedor(
            rs.getInt("id_proveedor"),
            rs.getString("razon_social"),
            rs.getString("ruc"),
            rs.getString("telefono"),
            rs.getString("direccion")
    );

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
                """, PROVEEDOR_ROW_MAPPER);
    }

    @Override
    public Optional<Proveedor> buscarPorId(Integer idProveedor) {
        return jdbcTemplate.query("""
                SELECT id_proveedor, razon_social, ruc, telefono, direccion
                FROM proveedor
                WHERE id_proveedor = ?
                """, PROVEEDOR_ROW_MAPPER, idProveedor).stream().findFirst();
    }

    @Override
    public List<Proveedor> filtrarPorRuc(String ruc) {
        return jdbcTemplate.query("""
                SELECT id_proveedor, razon_social, ruc, telefono, direccion
                FROM proveedor
                WHERE ruc = ?
                ORDER BY id_proveedor
                """, PROVEEDOR_ROW_MAPPER, ruc);
    }

    @Override
    public Proveedor insertar(Proveedor proveedor) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            var statement = connection.prepareStatement("""
                    INSERT INTO proveedor (razon_social, ruc, telefono, direccion)
                    VALUES (?, ?, ?, ?)
                    """, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, proveedor.razonSocial());
            statement.setString(2, proveedor.ruc());
            statement.setString(3, proveedor.telefono());
            statement.setString(4, proveedor.direccion());
            return statement;
        }, keyHolder);
        Number id = keyHolder.getKey();
        if (id == null) {
            throw new DataRetrievalFailureException("No se obtuvo el ID del proveedor insertado");
        }
        return new Proveedor(id.intValue(), proveedor.razonSocial(), proveedor.ruc(),
                proveedor.telefono(), proveedor.direccion());
    }

    @Override
    public int actualizar(Proveedor proveedor) {
        return jdbcTemplate.update("""
                UPDATE proveedor
                SET razon_social = ?, ruc = ?, telefono = ?, direccion = ?
                WHERE id_proveedor = ?
                """, proveedor.razonSocial(), proveedor.ruc(), proveedor.telefono(),
                proveedor.direccion(), proveedor.idProveedor());
    }

    @Override
    public int eliminar(Integer idProveedor) {
        return jdbcTemplate.update("DELETE FROM proveedor WHERE id_proveedor = ?", idProveedor);
    }
}