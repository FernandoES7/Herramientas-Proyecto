package com.API.WebServer.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorRequest {

    @NotBlank(message = "El RUC es obligatorio")
    @Pattern(regexp = "\\d{11}", message = "El RUC debe tener 11 dígitos")
    private String ruc;

    @NotBlank(message = "La razón social es obligatoria")
    private String razonSocial;

    @Pattern(regexp = "^\\+?[0-9 ()-]{7,20}$", message = "El teléfono no tiene un formato válido")
    private String telefono;

    @Email(message = "El correo no tiene un formato válido")
    private String correo;

    private String direccion;

    public String ruc() {
        return ruc;
    }

    public String razonSocial() {
        return razonSocial;
    }

    public String telefono() {
        return telefono;
    }

    public String correo() {
        return correo;
    }

    public String direccion() {
        return direccion;
    }
}
