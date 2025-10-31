package com.example.demo.model;

import com.example.demo.model.enviostate.EnvioEstado;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class HistorialEstadoEnvio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "envio_id", nullable = false)
    private Envio envio;

    @Transient
    private EnvioEstado estadoAnterior;

    @Transient
    private EnvioEstado estadoNuevo;

    @PastOrPresent
    @Column(nullable = false)
    private LocalDateTime fecha;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String observacion;
}
