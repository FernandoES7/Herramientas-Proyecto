package com.API.WebServer.controller;

import com.API.WebServer.dto.EmpleadoRequest;
import com.API.WebServer.dto.EmpleadoResponse;
import com.API.WebServer.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    public ResponseEntity<List<EmpleadoResponse>> listarEmpleados() {
        return ResponseEntity.ok(empleadoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseEntity<EmpleadoResponse>> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ResponseEntity.ok(empleadoService.buscarPorId(id)));
    }

    @PostMapping
    public ResponseEntity<EmpleadoResponse> crearEmpleado(@RequestBody EmpleadoRequest request) {
        EmpleadoResponse nuevoEmpleado = empleadoService.guardar(request);
        return new ResponseEntity<>(nuevoEmpleado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponse> actualizarEmpleado(@PathVariable Integer id, @RequestBody EmpleadoRequest request) {
        return ResponseEntity.ok(empleadoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable Integer id) {
        empleadoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}