package com.API.WebServer.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Table(name = "sucursal")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

public class Sucursal {
       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Long idSucursal;

       @Column(nullable = false, length = 50)
       private String nombre;

       @Column(length = 200)
       private String direccion;

       @Column(length = 9)
       private String telefono;

       @Column(length = 100)
       private String ciudad;
}
