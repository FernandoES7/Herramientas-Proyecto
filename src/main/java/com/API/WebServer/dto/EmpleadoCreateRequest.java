package com.API.WebServer.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record EmpleadoCreateRequest(
        @NotNull(message = "La sucursal no puede ser nula")
        @Positive(message = "El id de sucursal debe ser positivo")
        Integer idSucursal,

        @NotBlank(message = "El nombre no puede estar vacio")
        @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
        String nombres,

        @NotBlank(message = "El apellido no puede estar vacio")
        @Size(min = 3, max = 100, message = "El apellido debe tener entre 3 y 100 caracteres")
        String apellidos,

        @NotNull(message = "El DNI no puede ser nulo")
        @Pattern(regexp = "[0-9]{8}", message = "El DNI debe tener 8 digitos")
        String dni,

        @Size(max = 100, message = "El cargo excede los 100 caracteres")
        String cargo,

        @DecimalMin(value = "0.00", message = "El sueldo no puede ser negativo")
        @Digits(integer = 8, fraction = 2, message = "El sueldo admite hasta 8 enteros y 2 decimales")
        BigDecimal sueldo,

        @Size(min = 7, max = 20, message = "El telefono debe tener entre 7 y 20 caracteres")
        String telefono,

        @Email(message = "El correo es invalido")
        @Size(max = 150, message = "El correo excede los 150 caracteres")
        String correo
) {
}
