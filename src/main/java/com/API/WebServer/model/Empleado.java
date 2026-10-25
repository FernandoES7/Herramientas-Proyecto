package com.API.WebServer.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Empleado")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_empleado")
    private Integer idEmpleado;

    @Column(name = "id_sucursal", nullable = false)
    private Integer idSucursal;

    @Column(nullable = false, length = 100)
    private String nombres;

    @Column(nullable = false, length = 100)
    private String apellidos;

    @Column(nullable = false, unique = true, length = 8)
    private String dni;

    @Column(length = 100)
    private String cargo;

    @Column(precision = 10, scale = 2)
    private BigDecimal sueldo;

    @Column(length = 20)
    private String telefono;

    @Column(length = 150)
    private String correo;
}