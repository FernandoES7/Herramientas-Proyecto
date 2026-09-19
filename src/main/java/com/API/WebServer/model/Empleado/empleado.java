package com.API.WebServer.model;

public record Empleado(
    Integer idEmpleado,
    String usuario,
    String contrasena,
    String codigoEmpleado,
    String nombres,
    String apellidos
) {
}

