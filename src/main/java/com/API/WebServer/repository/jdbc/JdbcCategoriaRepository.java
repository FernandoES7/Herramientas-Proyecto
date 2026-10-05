package com.API.WebServer.repository.jdbc;

import com.API.WebServer.model.Categoria;
import com.API.WebServer.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcCategoriaRepository implements CategoriaRepository {

    private final JdbcTemplate jdbcTemplate;

    // Convierte cada fila de la BD en un objeto Categoria
    private Categoria mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Categoria.builder()
                .idCategoria(rs.getInt("id_categoria"))
                .nombre(rs.getString("nombre"))
                .descripcion(rs.getString("descripcion"))
                .build();
    }

    // Lista todas las categorias
    @Override
    public List<Categoria> listar() {
        String sql = "SELECT * FROM categoria";

        return jdbcTemplate.query(sql, this::mapRow);
    }

    // Busca una categoria por su ID
    @Override
    public Optional<Categoria> buscarPorId(Integer idCategoria) {
        String sql = "SELECT * FROM categoria WHERE id_categoria = ?";

        List<Categoria> categorias = jdbcTemplate.query(
                sql,
                this::mapRow,
                idCategoria
        );

        return categorias.stream().findFirst();
    }

    // Registra una nueva categoria
    @Override
    public Categoria guardar(Categoria categoria) {
        String sql = """
                INSERT INTO categoria
                (nombre, descripcion)
                VALUES (?, ?)
                """;

        jdbcTemplate.update(
                sql,
                categoria.getNombre(),
                categoria.getDescripcion()
        );

        return categoria;
    }

    // Actualiza los datos de una categoria
    @Override
    public Categoria actualizar(Categoria categoria) {
        String sql = """
                UPDATE categoria
                SET nombre = ?,
                    descripcion = ?
                WHERE id_categoria = ?
                """;

        jdbcTemplate.update(
                sql,
                categoria.getNombre(),
                categoria.getDescripcion(),
                categoria.getIdCategoria()
        );

        return categoria;
    }

    // Elimina una categoria
    @Override
    public void eliminar(Integer idCategoria) {
        String sql = "DELETE FROM categoria WHERE id_categoria = ?";

        jdbcTemplate.update(sql, idCategoria);
    }
}