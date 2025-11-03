package com.example.demo.model;

import com.example.demo.model.enviostate.EnvioEstado;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Date;
@Data
@Table(name="HistorialEstadoEnvios")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class HistorialEstadoEnvio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Cambio de @OneToOne a @ManyToOne porque un envío puede tener múltiples cambios de estado
    @ManyToOne
    @JoinColumn(name = "envio_id", nullable = false)
    private Envio envio;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "nombreEstado", column = @Column(name = "estado_anterior_nombre"))
    })
    private EnvioEstado estadoAnterior;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "nombreEstado", column = @Column(name = "estado_actual_nombre"))
    })
    private EnvioEstado estadoNuevo;

    @PastOrPresent
    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = true, length = 50)
    private String observacion;
}
