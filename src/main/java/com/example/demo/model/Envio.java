package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import com.example.demo.model.enums.EstadoEnvio;
import com.example.demo.model.paquete.Paquete;

import java.util.List;
@Entity
@Table(name = "envios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false,length = 100)
    private String remitente;

    @NotBlank
    @Column(nullable = false,length = 100)
    private String destinatario;

    @NotBlank
    @Column(nullable = false,length = 100)
    private String direccionEntrega;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 100)
    private EstadoEnvio estadoEnvio;

    @Column(nullable = false)
    private Boolean comprobanteEntrega;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "envio_id")
    private List<Paquete> paquetes;
}
