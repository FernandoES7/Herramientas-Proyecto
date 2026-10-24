package com.API.WebServer.proveedor.controller;

import com.API.WebServer.controller.ProveedorController;
import com.API.WebServer.mapper.ProveedorMapper;
import com.API.WebServer.model.Proveedor;
import com.API.WebServer.repository.ProveedorRepository;
import com.API.WebServer.service.ProveedorService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProveedorControllerTests {

    private static final String PROVEEDOR_JSON = """
            {"razonSocial": "Proveedor SAC", "ruc": "20123456789",
             "telefono": "999111222", "direccion": "Av. Lima 123"}
            """;

    private ProveedorRepository repository;
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        repository = mock(ProveedorRepository.class);
        ProveedorService service = new ProveedorService(repository, new ProveedorMapper());
        mvc = MockMvcBuilders.standaloneSetup(new ProveedorController(service)).build();
    }

    private Proveedor proveedor(Integer id) {
        return new Proveedor(id, "Proveedor SAC", "20123456789", "999111222", "Av. Lima 123");
    }

    @Test
    void listaProveedoresConLosCamposDeLaTabla() throws Exception {
        when(repository.listar()).thenReturn(List.of(proveedor(1)));

        mvc.perform(get("/api/proveedores"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$[0].idProveedor").value(1))
                .andExpect(jsonPath("$[0].razonSocial").value("Proveedor SAC"))
                .andExpect(jsonPath("$[0].ruc").value("20123456789"))
                .andExpect(jsonPath("$[0].telefono").value("999111222"))
                .andExpect(jsonPath("$[0].direccion").value("Av. Lima 123"));
    }

    @Test
    void buscaProveedorPorId() throws Exception {
        when(repository.buscarPorId(1)).thenReturn(Optional.of(proveedor(1)));

        mvc.perform(get("/api/proveedores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProveedor").value(1));
    }

    @Test
    void devuelveNoEncontradoSiElIdNoExiste() throws Exception {
        when(repository.buscarPorId(99)).thenReturn(Optional.empty());

        mvc.perform(get("/api/proveedores/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void filtraProveedoresPorRuc() throws Exception {
        when(repository.filtrarPorRuc("20123456789")).thenReturn(List.of(proveedor(1)));

        mvc.perform(get("/api/proveedores").param("ruc", "20123456789"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].ruc").value("20123456789"));
    }

    @Test
    void creaProveedorYDevuelveElIdGenerado() throws Exception {
        when(repository.insertar(any(Proveedor.class))).thenReturn(proveedor(1));

        mvc.perform(post("/api/proveedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PROVEEDOR_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idProveedor").value(1));
    }

    @Test
    void rechazaRucQueNoTieneOnceDigitos() throws Exception {
        mvc.perform(post("/api/proveedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"razonSocial": "Proveedor SAC", "ruc": "2012345678"}
                                """))
                .andExpect(status().isBadRequest());

        verify(repository, never()).insertar(any(Proveedor.class));
    }

    @Test
    void rechazaRazonSocialVacia() throws Exception {
        mvc.perform(post("/api/proveedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"razonSocial": " ", "ruc": "20123456789"}
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    void devuelveConflictoSiElRucEstaDuplicado() throws Exception {
        when(repository.insertar(any(Proveedor.class)))
                .thenThrow(new DataIntegrityViolationException("Duplicate entry"));

        mvc.perform(post("/api/proveedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PROVEEDOR_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    void actualizaProveedor() throws Exception {
        when(repository.actualizar(any(Proveedor.class))).thenReturn(1);

        mvc.perform(put("/api/proveedores/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PROVEEDOR_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idProveedor").value(1))
                .andExpect(jsonPath("$.razonSocial").value("Proveedor SAC"));
    }

    @Test
    void devuelveNoEncontradoAlActualizarProveedorInexistente() throws Exception {
        when(repository.actualizar(any(Proveedor.class))).thenReturn(0);

        mvc.perform(put("/api/proveedores/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(PROVEEDOR_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminaProveedor() throws Exception {
        when(repository.eliminar(1)).thenReturn(1);

        mvc.perform(delete("/api/proveedores/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void devuelveNoEncontradoAlEliminarProveedorInexistente() throws Exception {
        when(repository.eliminar(99)).thenReturn(0);

        mvc.perform(delete("/api/proveedores/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void devuelveConflictoAlEliminarProveedorConRegistrosAsociados() throws Exception {
        when(repository.eliminar(1)).thenThrow(new DataIntegrityViolationException("FK"));

        mvc.perform(delete("/api/proveedores/1"))
                .andExpect(status().isConflict());
    }
}