package com.example.demo.dto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class VehiculoDTO {
    private Long id;
    private String patente;
    private Double capPeso;
    private Double capVolumen;
    private Boolean refrigerado;
    private Double rangTempMin;
    private Double rangTempMax;
}
