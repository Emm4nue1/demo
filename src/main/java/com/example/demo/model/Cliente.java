package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table( name = "clientes",uniqueConstraints = {
        @UniqueConstraint(name = "cl_clientes_dni", columnNames = "dni"),
        @UniqueConstraint(name = "cl_clientes_email", columnNames = "email")
}
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
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
