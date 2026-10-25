package com.API.WebServer.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class EmpleadoResponse {
    private Integer idEmpleado;
    private Integer idSucursal;
    private String nombres;
    private String apellidos;
    private String dni;
    private String cargo;
    private BigDecimal sueldo;
    private String telefono;
    private String correo;
}