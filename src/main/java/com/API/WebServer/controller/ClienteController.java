package com.API.WebServer.controller;

import com.API.WebServer.dto.ClienteResponse;
import com.API.WebServer.dto.ClienteCreateRequest;
import com.API.WebServer.dto.ClienteUpdateRequest;
import com.API.WebServer.service.ClienteService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @DeleteMapping("/{idCliente}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable("idCliente") Integer idCliente) {
        clienteService.eliminar(idCliente);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteResponse insertar(@RequestBody ClienteCreateRequest request) {
        return clienteService.insertar(request);
    }

    @PutMapping("/{idCliente}")
    public ClienteResponse actualizar(@PathVariable("idCliente") Integer idCliente,
                                      @RequestBody ClienteUpdateRequest request) {
        return clienteService.actualizar(idCliente, request);
    }
}
