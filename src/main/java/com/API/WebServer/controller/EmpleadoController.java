package com.API.WebServer.controller;

import com.API.WebServer.dto.EmpleadoResponse;
import com.API.WebServer.dto.EmpleadoCreateRequest;
import com.API.WebServer.dto.EmpleadoUpdateRequest;
import com.API.WebServer.service.EmpleadoService;
import java.util.List;

import jakarta.validation.Valid;
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
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping
    public List<EmpleadoResponse> listar() {
        return empleadoService.listar();
    }

    @GetMapping(params = "dni")
    public EmpleadoResponse filtrarPorDni(@RequestParam("dni") String dni) {
        return empleadoService.filtrarPorDni(dni);
    }

    @DeleteMapping("/{idEmpleado}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable("idEmpleado") Integer idEmpleado) {
        empleadoService.eliminar(idEmpleado);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmpleadoResponse insertar(@Valid @RequestBody EmpleadoCreateRequest request) {
        return empleadoService.insertar(request);
    }

    @PutMapping("/{idEmpleado}")
    public EmpleadoResponse actualizar(@PathVariable("idEmpleado") Integer idEmpleado,
                                      @Valid @RequestBody EmpleadoUpdateRequest request) {
        return empleadoService.actualizar(idEmpleado, request);
    }
}

