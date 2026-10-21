package com.API.WebServer.repository.jdbc;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
                SELECT id_proveedor, razon_social, ruc, telefono, correo, direccion
                FROM proveedor
                ORDER BY id_proveedor
                """, this::mapRow);
    }

    @Override
    public Optional<Proveedor> buscarPorId(Integer idProveedor) {
        return consultar("""
                SELECT id_proveedor, razon_social, ruc, telefono, correo, direccion
                FROM proveedor
                WHERE id_proveedor = ?
                """, idProveedor).stream().findFirst();
    }

    @Override
    public Optional<Proveedor> buscarPorRuc(String ruc) {
        return consultar("""
                SELECT id_proveedor, razon_social, ruc, telefono, correo, direccion
                FROM proveedor
                WHERE ruc = ?
                """, ruc).stream().findFirst();
    }

    @Override
    public List<Proveedor> buscarPorRazonSocial(String razonSocial) {
        String criterio = "%" + razonSocial.toLowerCase() + "%";
        return jdbcTemplate.query("""
                SELECT id_proveedor, razon_social, ruc, telefono, correo, direccion
                FROM proveedor
                WHERE LOWER(razon_social) LIKE ?
                ORDER BY razon_social
                """, this::mapRow, criterio);
    }

    @Override
    public boolean existePorRuc(String ruc) {
        Integer cantidad = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM proveedor WHERE ruc = ?",
                Integer.class, ruc);
        return cantidad != null && cantidad > 0;
    }

    @Override
    public Proveedor guardar(Proveedor proveedor) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            var statement = connection.prepareStatement("""
                    INSERT INTO proveedor
                    (razon_social, ruc, telefono, correo, direccion)
                    VALUES (?, ?, ?, ?, ?)
                    """, new String[]{"id_proveedor"});
            statement.setString(1, proveedor.getRazonSocial());
            statement.setString(2, proveedor.getRuc());
            statement.setString(3, proveedor.getTelefono());
            statement.setString(4, proveedor.getCorreo());
            statement.setString(5, proveedor.getDireccion());
            return statement;
        }, keyHolder);

        Number generatedKey = keyHolder.getKey();
        if (generatedKey != null) {
            proveedor.setIdProveedor(generatedKey.intValue());
        }
        return proveedor;
    }

    @Override
    public Proveedor actualizar(Proveedor proveedor) {
        jdbcTemplate.update("""
                UPDATE proveedor
                SET razon_social = ?, ruc = ?, telefono = ?, correo = ?, direccion = ?
                WHERE id_proveedor = ?
                """, proveedor.getRazonSocial(), proveedor.getRuc(), proveedor.getTelefono(),
                proveedor.getCorreo(), proveedor.getDireccion(), proveedor.getIdProveedor());
        return proveedor;
    }

    @Override
    public int eliminar(Integer idProveedor) {
        return jdbcTemplate.update("DELETE FROM proveedor WHERE id_proveedor = ?", idProveedor);
    }

    private List<Proveedor> consultar(String sql, Object parametro) {
        return jdbcTemplate.query(sql, this::mapRow, parametro);
    }

    private Proveedor mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        return new Proveedor(
                rs.getInt("id_proveedor"),
                rs.getString("razon_social"),
                rs.getString("ruc"),
                rs.getString("telefono"),
                rs.getString("correo"),
                rs.getString("direccion")
        );
    }
}