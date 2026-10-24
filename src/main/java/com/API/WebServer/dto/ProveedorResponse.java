package com.API.WebServer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorResponse {

    private Integer idProveedor;
    private String razonSocial;
    private String ruc;
    private String telefono;
    private String correo;
    private String direccion;

}