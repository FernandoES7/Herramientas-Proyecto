package com.API.WebServer.producto.controller;

import com.API.WebServer.controller.ProductoController;
import com.API.WebServer.exception.GlobalExceptionHandler;
import com.API.WebServer.mapper.ProductoMapper;
import com.API.WebServer.model.Producto;
import com.API.WebServer.repository.ProductoRepository;
import com.API.WebServer.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductoControllerTests {

    private ProductoRepository repository;
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        repository = mock(ProductoRepository.class);
        ProductoService service = new ProductoService(repository, new ProductoMapper());
        mvc = MockMvcBuilders.standaloneSetup(new ProductoController(service))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    private Producto productoDePrueba() {
        return new Producto(1L, "Arroz Costeno 5kg", "Arroz extra",
                new BigDecimal("24.90"), 50, 1L);
    }

    @Test
    void listaProductos() throws Exception {
        when(repository.findAll()).thenReturn(List.of(productoDePrueba()));

        mvc.perform(get("/api/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idProducto").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Arroz Costeno 5kg"))
                .andExpect(jsonPath("$[0].stock").value(50));
    }

    @Test
    void buscaProductoPorId() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(productoDePrueba()));

        mvc.perform(get("/api/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProducto").value(1))
                .andExpect(jsonPath("$.idCategoria").value(1));
    }

    @Test
    void devuelve404SiElProductoNoExiste() throws Exception {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        mvc.perform(get("/api/productos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void creaProducto() throws Exception {
        when(repository.saveAndFlush(any(Producto.class))).thenReturn(productoDePrueba());

        mvc.perform(post("/api/productos")
                        .contentType("application/json")
                        .content("""
                                {"nombre":"Arroz Costeno 5kg","descripcion":"Arroz extra",
                                 "precio":24.90,"stock":50,"idCategoria":1}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idProducto").value(1))
                .andExpect(jsonPath("$.nombre").value("Arroz Costeno 5kg"));
    }

    @Test
    void rechazaProductoConDatosInvalidos() throws Exception {
        mvc.perform(post("/api/productos")
                        .contentType("application/json")
                        .content("""
                                {"nombre":"","precio":-5,"stock":-1}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errores.nombre").exists())
                .andExpect(jsonPath("$.errores.precio").exists())
                .andExpect(jsonPath("$.errores.stock").exists())
                .andExpect(jsonPath("$.errores.idCategoria").exists());

        verify(repository, never()).saveAndFlush(any(Producto.class));
    }

    @Test
    void actualizaProducto() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(productoDePrueba()));
        when(repository.saveAndFlush(any(Producto.class))).thenAnswer(inv -> inv.getArgument(0));

        mvc.perform(put("/api/productos/1")
                        .contentType("application/json")
                        .content("""
                                {"nombre":"Arroz Costeno 5kg","descripcion":"Arroz extra",
                                 "precio":26.50,"stock":40,"idCategoria":1}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.precio").value(26.50))
                .andExpect(jsonPath("$.stock").value(40));
    }

    @Test
    void eliminaProducto() throws Exception {
        Producto producto = productoDePrueba();
        when(repository.findById(1L)).thenReturn(Optional.of(producto));

        mvc.perform(delete("/api/productos/1"))
                .andExpect(status().isNoContent());

        verify(repository).delete(producto);
    }
}