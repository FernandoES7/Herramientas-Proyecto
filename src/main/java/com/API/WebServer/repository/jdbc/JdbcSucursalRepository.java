package com.API.WebServer.repository.jdbc;

import com.API.WebServer.model.Sucursal;
import com.API.WebServer.repository.SucursalRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcSucursalRepository implements SucursalRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcSucursalRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String SELECT_ALL =
            "SELECT id_sucursal, nombre, direccion, telefono, ciudad FROM sucursal";

    private final org.springframework.jdbc.core.RowMapper<Sucursal> rowMapper = (rs, rowNum) ->
            new Sucursal(
                    rs.getInt("id_sucursal"),
                    rs.getString("nombre"),
                    rs.getString("direccion"),
                    rs.getString("telefono"),
                    rs.getString("ciudad")
            );

    @Override
    public List<Sucursal> findAll() {
        return jdbcTemplate.query(SELECT_ALL, rowMapper);
    }

    @Override
    public Optional<Sucursal> findById(Integer id) {
        String sql = SELECT_ALL + " WHERE id_sucursal = ?";
        return jdbcTemplate.query(sql, rowMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public Sucursal save(Sucursal sucursal) {
        String sql = "INSERT INTO sucursal (nombre, direccion, telefono, ciudad) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, sucursal.nombre());
            ps.setString(2, sucursal.direccion());
            ps.setString(3, sucursal.telefono());
            ps.setString(4, sucursal.ciudad());
            return ps;
        }, keyHolder);

        Integer newId = keyHolder.getKey().intValue();
        return new Sucursal(newId, sucursal.nombre(), sucursal.direccion(), sucursal.telefono(), sucursal.ciudad());
    }

    @Override
    public void deleteById(Integer id) {
        jdbcTemplate.update("DELETE FROM sucursal WHERE id_sucursal = ?", id);
    }
}