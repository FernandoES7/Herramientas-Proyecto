package com.API.WebServer.repository.jdbc;

import com.API.WebServer.model.Producto;
import com.API.WebServer.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcProductoRepository implements ProductoRepository {

    private final JdbcTemplate jdbcTemplate;

    // Convierte cada fila de la BD en un objeto Producto
    private Producto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Producto.builder()
                .idProducto(rs.getInt("id_producto"))
                .nombre(rs.getString("nombre"))
                .descripcion(rs.getString("descripcion"))
                .precio(rs.getBigDecimal("precio"))
                .stock(rs.getInt("stock"))
                .idCategoria(rs.getInt("id_categoria"))
                .build();
    }

    // Lista todos los productos
    @Override
    public List<Producto> listar() {
        String sql = "SELECT * FROM producto";

        return jdbcTemplate.query(sql, this::mapRow);
    }

    // Busca un producto por su ID
    @Override
    public Optional<Producto> buscarPorId(Integer idProducto) {
        String sql = "SELECT * FROM producto WHERE id_producto = ?";

        List<Producto> productos = jdbcTemplate.query(
                sql,
                this::mapRow,
                idProducto
        );

        return productos.stream().findFirst();
    }

    // Registra un nuevo producto
    @Override
    public Producto guardar(Producto producto) {
        String sql = """
                INSERT INTO producto
                (nombre, descripcion, precio, stock, id_categoria)
                VALUES (?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getIdCategoria()
        );

        return producto;
    }

    // Actualiza los datos de un producto
    @Override
    public Producto actualizar(Producto producto) {
        String sql = """
                UPDATE producto
                SET nombre = ?,
                    descripcion = ?,
                    precio = ?,
                    stock = ?,
                    id_categoria = ?
                WHERE id_producto = ?
                """;

        jdbcTemplate.update(
                sql,
                producto.getNombre(),
                producto.getDescripcion(),
                producto.getPrecio(),
                producto.getStock(),
                producto.getIdCategoria(),
                producto.getIdProducto()
        );

        return producto;
    }

    // Elimina un producto
    @Override
    public void eliminar(Integer idProducto) {
        String sql = "DELETE FROM producto WHERE id_producto = ?";

        jdbcTemplate.update(sql, idProducto);
    }
}