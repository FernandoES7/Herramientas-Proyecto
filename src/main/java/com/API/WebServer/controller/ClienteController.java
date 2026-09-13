package com.API.WebServer.controller;

import com.API.WebServer.dto.ClienteResponse;
import com.API.WebServer.service.ClienteService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<ClienteResponse> listar() {
        return clienteService.listar();
    }

    @GetMapping(params = "dni")
    public List<ClienteResponse> filtrarPorDni(@RequestParam("dni") String dni) {
        return clienteService.filtrarPorDni(dni);
    }
}
