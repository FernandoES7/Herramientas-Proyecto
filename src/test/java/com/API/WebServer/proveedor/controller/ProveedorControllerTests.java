package com.API.WebServer.proveedor.controller;

import com.API.WebServer.controller.ProveedorController;
import com.API.WebServer.mapper.ProveedorMapper;
import com.API.WebServer.model.Proveedor;
import com.API.WebServer.repository.ProveedorRepository;
import com.API.WebServer.service.ProveedorService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ProveedorControllerTests {

    private ProveedorRepository repository;
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        repository = mock(ProveedorRepository.class);
        ProveedorService service = new ProveedorService(repository, new ProveedorMapper());
        mvc = MockMvcBuilders.standaloneSetup(new ProveedorController(service)).build();
    }

    @Test
    void devuelveProveedoresConLosCamposDeLaTabla() throws Exception {
        when(repository.listar()).thenReturn(List.of(new Proveedor(
                1, "Distribuidora Lima", "20123456789", "999111222", "Av. Lima 123")));

        mvc.perform(get("/api/proveedores"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(content().json("""
                        [{"idProveedor":1,"razonSocial":"Distribuidora Lima",
                          "ruc":"20123456789","telefono":"999111222",
                          "direccion":"Av. Lima 123"}]
                        """));
    }

    @Test
    void devuelveListaVaciaCuandoNoHayProveedores() throws Exception {
        when(repository.listar()).thenReturn(List.of());

        mvc.perform(get("/api/proveedores"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
