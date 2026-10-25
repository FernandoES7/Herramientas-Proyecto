package com.API.WebServer.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class EmpleadoRequest {
    private Integer idSucursal;
    private String nombres;
    private String apellidos;
    private String dni;
    private String cargo;
    private BigDecimal sueldo;
    private String telefono;
    private String correo;
}