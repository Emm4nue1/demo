package com.example.demo.dto;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PaqueteDTO {
    private Long id;
    private String codigo;
    private double peso;
    private double volumen;
    //paqueteFragil
    private String nivelFragilidad;
    private boolean seguroAdicional;
    //paqueteRefrigerado
    private double temperaturaObjetivo;
    private double rangoMinimo;
    private double rangoMaximo;
    private int maxHsFueraFrio;
    //typo
    private String tipo;
}
