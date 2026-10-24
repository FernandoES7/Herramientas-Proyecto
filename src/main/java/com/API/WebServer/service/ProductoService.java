package com.API.WebServer.service;

import com.API.WebServer.dto.ProductoCreateRequest;
import com.API.WebServer.dto.ProductoResponse;
import com.API.WebServer.dto.ProductoUpdateRequest;
import com.API.WebServer.mapper.ProductoMapper;
import com.API.WebServer.model.Producto;
import com.API.WebServer.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    public List<ProductoResponse> listar() {
        return productoRepository.findAll().stream()
                .map(productoMapper::toResponse)
                .toList();
    }

    public ProductoResponse buscarPorId(Long idProducto) {
        return productoMapper.toResponse(buscar(idProducto));
    }

    public List<ProductoResponse> listarPorCategoria(Long idCategoria) {
        return productoRepository.findByIdCategoria(idCategoria).stream()
                .map(productoMapper::toResponse)
                .toList();
    }

    @Transactional
    public ProductoResponse insertar(ProductoCreateRequest request) {
        Producto producto = new Producto(null, request.nombre(), request.descripcion(),
                request.precio(), request.stock(), request.idCategoria());
        return productoMapper.toResponse(guardar(producto));
    }

    @Transactional
    public ProductoResponse actualizar(Long idProducto, ProductoUpdateRequest request) {
        Producto producto = buscar(idProducto);
        producto.setNombre(request.nombre());
        producto.setDescripcion(request.descripcion());
        producto.setPrecio(request.precio());
        producto.setStock(request.stock());
        producto.setIdCategoria(request.idCategoria());
        return productoMapper.toResponse(guardar(producto));
    }

    @Transactional
    public void eliminar(Long idProducto) {
        Producto producto = buscar(idProducto);
        try {
            productoRepository.delete(producto);
            productoRepository.flush();
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "No se puede eliminar el producto porque tiene registros asociados", ex);
        }
    }

    private Producto buscar(Long idProducto) {
        return productoRepository.findById(idProducto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }

    // saveAndFlush envia el INSERT/UPDATE en este momento, asi si la categoria no existe
    // (llave foranea) el error se captura aqui y se responde 400 en lugar de un 500.
    private Producto guardar(Producto producto) {
        try {
            return productoRepository.saveAndFlush(producto);
        } catch (DataIntegrityViolationException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La categoria " + producto.getIdCategoria() + " no existe", ex);
        }
    }
}