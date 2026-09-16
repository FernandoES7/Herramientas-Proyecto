package com.API.WebServer.repository.jdbc;

import com.API.WebServer.model.Categoria;
import com.API.WebServer.repository.CategoriaRepository;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcCategoriaRepository implements CategoriaRepository {

    private final JdbcTemplate jdbcTemplate;

    // Inyección por constructor (estándar de tu equipo)
    public JdbcCategoriaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Categoria> listar() {
        return jdbcTemplate.query("""
                SELECT id_categoria, nombre, descripcion
                FROM CATEGORIA
                ORDER BY id_categoria
                """, (rs, rowNum) -> new Categoria(
                rs.getInt("id_categoria"),
                rs.getString("nombre"),
                rs.getString("descripcion")
        ));
    }

    @Override
    public int guardar(Categoria categoria) {
        return jdbcTemplate.update("""
                INSERT INTO CATEGORIA (nombre, descripcion)
                VALUES (?, ?)
                """, categoria.nombre(), categoria.descripcion());
    }

    @Override
    public Categoria buscarPorId(Integer id) {
        return jdbcTemplate.query("""
                SELECT id_categoria, nombre, descripcion
                FROM CATEGORIA
                WHERE id_categoria = ?
                """, (rs, rowNum) -> new Categoria(
                rs.getInt("id_categoria"),
                rs.getString("nombre"),
                rs.getString("descripcion")
        ), id).stream().findFirst().orElse(null);
    }

    @Override
    public int actualizar(Integer id, Categoria categoria) {
        return jdbcTemplate.update("""
                UPDATE CATEGORIA
                SET nombre = ?, descripcion = ?
                WHERE id_categoria = ?
                """, categoria.nombre(), categoria.descripcion(), id);
    }

    @Override
    public int eliminar(Integer id) {
        return jdbcTemplate.update("""
                DELETE FROM CATEGORIA
                WHERE id_categoria = ?
                """, id);
    }
}