package com.API.WebServer.cliente.controller;

import com.API.WebServer.controller.ClienteController;
import com.API.WebServer.mapper.ClienteMapper;
import com.API.WebServer.model.Cliente;
import com.API.WebServer.repository.ClienteRepository;
import com.API.WebServer.service.ClienteService;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ClienteControllerTests {

    private ClienteRepository repository;
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        repository = mock(ClienteRepository.class);
        ClienteService service = new ClienteService(repository, new ClienteMapper());
        mvc = MockMvcBuilders.standaloneSetup(new ClienteController(service)).build();
    }

    @Test
    void devuelveClientesConLosCamposDeLaTabla() throws Exception {
        when(repository.listar()).thenReturn(List.of(new Cliente(
                1, "Ana", "Perez", "00123456", "999111222", "Av. Lima 123",
                LocalDate.of(2026, 9, 8))));

        mvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(content().json("""
                        [{"idCliente":1,"nombres":"Ana","apellidos":"Perez",
                          "dni":"00123456","telefono":"999111222",
                          "direccion":"Av. Lima 123","fechaRegistro":"2026-09-08"}]
                        """));
    }

    @Test
    void devuelveListaVaciaCuandoNoHayClientes() throws Exception {
        when(repository.listar()).thenReturn(List.of());

        mvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
