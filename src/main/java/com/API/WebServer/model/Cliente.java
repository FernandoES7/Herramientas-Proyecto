package com.API.WebServer.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="`Cliente`")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCliente;
    private String nombres;
    private String apellidos;
    @Column(unique = true)
    private String dni;
    private String telefono;
    private String direccion;
    private LocalDate fechaRegistro;
}
