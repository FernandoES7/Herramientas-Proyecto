package com.API.WebServer.proveedor.controller;

import com.API.WebServer.controller.ProveedorController;
import com.API.WebServer.dto.ProveedorRequest;
import com.API.WebServer.exception.GlobalExceptionHandler;
import com.API.WebServer.mapper.ProveedorMapper;
import com.API.WebServer.model.Proveedor;
import com.API.WebServer.repository.ProveedorRepository;
import com.API.WebServer.service.ProveedorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProveedorControllerTests {

    private ProveedorRepository repository;
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        repository = mock(ProveedorRepository.class);
        ProveedorService service = new ProveedorService(repository, new ProveedorMapper());
        mvc = MockMvcBuilders.standaloneSetup(new ProveedorController(service))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void creaProveedor() throws Exception {
        ProveedorRequest request = new ProveedorRequest(
                "20123456789", "Proveedor SAC", "999111222", "ana@proveedor.com", "Av. Lima 123");
        Proveedor proveedor = new Proveedor(1, request.razonSocial(), request.ruc(),
                request.telefono(), request.correo(), request.direccion());
        when(repository.existePorRuc(request.ruc())).thenReturn(false);
        when(repository.guardar(any(Proveedor.class))).thenReturn(proveedor);

        mvc.perform(post("/api/proveedores")
                        .contentType("application/json")
                        .content("""
                                {"ruc":"20123456789","razonSocial":"Proveedor SAC",
                                 "telefono":"999111222",
                                 "correo":"ana@proveedor.com","direccion":"Av. Lima 123"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idProveedor").value(1))
                .andExpect(jsonPath("$.ruc").value("20123456789"));
    }

    @Test
    void listaProveedores() throws Exception {
        when(repository.listar()).thenReturn(List.of(
                new Proveedor(1, "Proveedor SAC", "20123456789",
                        "999111222", "ana@proveedor.com", "Av. Lima 123")));

        mvc.perform(get("/api/proveedores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].razonSocial").value("Proveedor SAC"));
    }

    @Test
    void buscaPorId() throws Exception {
        when(repository.buscarPorId(1)).thenReturn(Optional.of(
                new Proveedor(1, "Proveedor SAC", "20123456789",
                        "999111222", "ana@proveedor.com", "Av. Lima 123")));

        mvc.perform(get("/api/proveedores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProveedor").value(1));
    }

    @Test
    void buscaPorRuc() throws Exception {
        when(repository.buscarPorRuc("20123456789")).thenReturn(Optional.of(
                new Proveedor(1, "Proveedor SAC", "20123456789",
                        "999111222", "ana@proveedor.com", "Av. Lima 123")));

        mvc.perform(get("/api/proveedores/ruc/20123456789"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ruc").value("20123456789"));
    }

    @Test
    void devuelveConflictoCuandoElRucEstaDuplicado() throws Exception {
        when(repository.existePorRuc("20123456789")).thenReturn(true);

        mvc.perform(post("/api/proveedores")
                        .contentType("application/json")
                        .content("""
                                {"ruc":"20123456789","razonSocial":"Proveedor SAC"}
                                """))
                .andExpect(status().isConflict());
    }

    @Test
    void devuelveNoEncontradoCuandoElProveedorNoExiste() throws Exception {
        when(repository.buscarPorId(99)).thenReturn(Optional.empty());

        mvc.perform(get("/api/proveedores/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void actualizaProveedor() throws Exception {
        Proveedor proveedor = new Proveedor(1, "Proveedor Actualizado", "20123456789",
                "999111222", "ana@proveedor.com", "Av. Lima 123");
        when(repository.buscarPorId(1)).thenReturn(Optional.of(proveedor));
        when(repository.buscarPorRuc(proveedor.ruc())).thenReturn(Optional.of(proveedor));
        when(repository.actualizar(any(Proveedor.class))).thenReturn(proveedor);

        mvc.perform(put("/api/proveedores/1")
                        .contentType("application/json")
                        .content("""
                                {"ruc":"20123456789","razonSocial":"Proveedor Actualizado",
                                 "telefono":"999111222",
                                 "correo":"ana@proveedor.com","direccion":"Av. Lima 123"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.razonSocial").value("Proveedor Actualizado"));
    }

    @Test
    void eliminaProveedor() throws Exception {
        when(repository.eliminar(1)).thenReturn(1);

        mvc.perform(delete("/api/proveedores/1"))
                .andExpect(status().isNoContent());
    }
}
