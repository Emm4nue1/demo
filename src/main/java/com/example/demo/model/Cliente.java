package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table( name = "clientes",uniqueConstraints = {
        @UniqueConstraint(name = "cl_clientes_dni", columnNames = "dnis"),
        @UniqueConstraint(name = "cl_clientes_email", columnNames = "emails")
}
)
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String nombre;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String apellido;

    @NotBlank
    @Column(nullable = false, length = 50,unique = true)
    private String dni;

    @NotBlank
    @Column(nullable = false, length = 50,unique = true)
    private String email;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String calle;

    @PositiveOrZero
    @Column(nullable = false, length = 50)
    private Long numCasa;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String codPostal;

}
