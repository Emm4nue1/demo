package com.example.demo.model;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(
        name = "vehiculos",
        uniqueConstraints = @UniqueConstraint(name = "uk_vehiculos_patente", columnNames = "patente")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String patente;

    @NotNull
    @Column(name = "cap_peso", nullable = false)
    private Double capPeso;

    @NotNull
    @Column(name = "cap_volumen", nullable = false)
    private Double capVolumen;

    @NotNull
    @Column(nullable = false)
    private Boolean refrigerado;

    //Un vehículo no refrigerado NO debería tener temperatura por eso saco el @NotNull
    @Column(name = "rang_temp_min")
    private Double rangTempMin;

    //Un vehículo no refrigerado NO debería tener temperatura por eso saco el @NotNull
    @Column(name = "rang_temp_max")
    private Double rangTempMax;
}