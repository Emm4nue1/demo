package com.example.demo.dto;

import com.example.demo.model.Envio;
import com.example.demo.model.enviostate.EnvioEstado;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class HistorialEstadoEnvioDTO {
    private Long id;
    private EnvioDTO envio;
    private String estadoAnterior;
    private String estadoNuevo;
    private String fecha;

    private String observacion;
}
