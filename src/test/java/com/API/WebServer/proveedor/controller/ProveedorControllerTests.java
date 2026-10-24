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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProveedorControllerTests {

    private static final String PROVEEDOR_JSON = """
            {"ruc":"20123456789","razonSocial":"Proveedor SAC",
             "telefono":"999111222",
             "correo":"ana@proveedor.com","direccion":"Av. Lima 123"}
            """;

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

    private Proveedor proveedor(Integer id) {
        return new Proveedor(id, "Proveedor SAC", "20123456789",
                "999111222", "ana@proveedor.com", "Av. Lima 123");
    }

    @Test
    void creaProveedor() throws Exception {
        ProveedorRequest request = new ProveedorRequest(
                "20123456789", "Proveedor SAC", "999111222", "ana@proveedor.com", "Av. Lima 123");
        Proveedor proveedor = new Proveedor(1, request.razonSocial(), request.ruc(),
                request.telefono(), request.correo(), request.direccion());
        when(repository.existsByRuc(request.ruc())).thenReturn(false);
        when(repository.saveAndFlush(any(Proveedor.class))).thenReturn(proveedor);

        mvc.perform(post("/api/proveedores")
                        .contentType("application/json")
                        .content(PROVEEDOR_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idProveedor").value(1))
                .andExpect(jsonPath("$.ruc").value("20123456789"));
    }

    @Test
    void listaProveedores() throws Exception {
        when(repository.findAll()).thenReturn(List.of(proveedor(1)));

        mvc.perform(get("/api/proveedores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].razonSocial").value("Proveedor SAC"));
    }

    @Test
    void buscaPorId() throws Exception {
        when(repository.findById(1)).thenReturn(Optional.of(proveedor(1)));

        mvc.perform(get("/api/proveedores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProveedor").value(1));
    }

    @Test
    void buscaPorRuc() throws Exception {
        when(repository.findByRuc("20123456789")).thenReturn(Optional.of(proveedor(1)));

        mvc.perform(get("/api/proveedores/ruc/20123456789"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ruc").value("20123456789"));
    }

    @Test
    void buscaPorRazonSocial() throws Exception {
        when(repository.buscarPorRazonSocial("prov")).thenReturn(List.of(proveedor(1)));

        mvc.perform(get("/api/proveedores/buscar").param("razonSocial", "prov"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].razonSocial").value("Proveedor SAC"));
    }

    @Test
    void devuelveConflictoCuandoElRucEstaDuplicado() throws Exception {
        when(repository.existsByRuc("20123456789")).thenReturn(true);

        mvc.perform(post("/api/proveedores")
                        .contentType("application/json")
                        .content("""
                                {"ruc":"20123456789","razonSocial":"Proveedor SAC"}
                                """))
                .andExpect(status().isConflict());
    }

    @Test
    void devuelveConflictoCuandoLaBaseDeDatosRechazaLosDatos() throws Exception {
        when(repository.existsByRuc("20123456789")).thenReturn(false);
        when(repository.saveAndFlush(any(Proveedor.class)))
                .thenThrow(new DataIntegrityViolationException("Duplicate entry para correo"));

        mvc.perform(post("/api/proveedores")
                        .contentType("application/json")
                        .content(PROVEEDOR_JSON))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void rechazaRucQueNoTieneOnceDigitos() throws Exception {
        mvc.perform(post("/api/proveedores")
                        .contentType("application/json")
                        .content("""
                                {"ruc":"2012345678","razonSocial":"Proveedor SAC"}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errores.ruc").exists());

        verify(repository, never()).saveAndFlush(any(Proveedor.class));
    }

    @Test
    void rechazaRazonSocialVacia() throws Exception {
        mvc.perform(post("/api/proveedores")
                        .contentType("application/json")
                        .content("""
                                {"ruc":"20123456789","razonSocial":" "}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errores.razonSocial").exists());
    }

    @Test
    void rechazaCorreoConFormatoInvalido() throws Exception {
        mvc.perform(post("/api/proveedores")
                        .contentType("application/json")
                        .content("""
                                {"ruc":"20123456789","razonSocial":"Proveedor SAC","correo":"no-es-correo"}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errores.correo").exists());
    }

    @Test
    void devuelveNoEncontradoCuandoElProveedorNoExiste() throws Exception {
        when(repository.findById(99)).thenReturn(Optional.empty());

        mvc.perform(get("/api/proveedores/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void actualizaProveedor() throws Exception {
        Proveedor proveedor = new Proveedor(1, "Proveedor Actualizado", "20123456789",
                "999111222", "ana@proveedor.com", "Av. Lima 123");
        when(repository.findById(1)).thenReturn(Optional.of(proveedor));
        when(repository.findByRuc(proveedor.getRuc())).thenReturn(Optional.of(proveedor));
        when(repository.saveAndFlush(any(Proveedor.class))).thenReturn(proveedor);

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
    void devuelveNoEncontradoAlActualizarProveedorInexistente() throws Exception {
        when(repository.findById(99)).thenReturn(Optional.empty());

        mvc.perform(put("/api/proveedores/99")
                        .contentType("application/json")
                        .content(PROVEEDOR_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void devuelveConflictoAlActualizarConElRucDeOtroProveedor() throws Exception {
        when(repository.findById(1)).thenReturn(Optional.of(proveedor(1)));
        when(repository.findByRuc("20123456789")).thenReturn(Optional.of(proveedor(2)));

        mvc.perform(put("/api/proveedores/1")
                        .contentType("application/json")
                        .content(PROVEEDOR_JSON))
                .andExpect(status().isConflict());

        verify(repository, never()).saveAndFlush(any(Proveedor.class));
    }

    @Test
    void eliminaProveedor() throws Exception {
        when(repository.existsById(1)).thenReturn(true);

        mvc.perform(delete("/api/proveedores/1"))
                .andExpect(status().isNoContent());

        verify(repository).deleteById(1);
    }

    @Test
    void devuelveNoEncontradoAlEliminarProveedorInexistente() throws Exception {
        when(repository.existsById(99)).thenReturn(false);

        mvc.perform(delete("/api/proveedores/99"))
                .andExpect(status().isNotFound());

        verify(repository, never()).deleteById(99);
    }

    @Test
    void devuelveConflictoAlEliminarProveedorConRegistrosAsociados() throws Exception {
        when(repository.existsById(1)).thenReturn(true);
        doThrow(new DataIntegrityViolationException("FK compra_proveedor"))
                .when(repository).flush();

        mvc.perform(delete("/api/proveedores/1"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.error").exists());
    }
}
