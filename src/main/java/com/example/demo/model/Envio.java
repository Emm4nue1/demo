package com.example.demo.model;

import com.example.demo.model.enviostate.EnvioEstado;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
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

    @ManyToOne
    @JoinColumn(name="remitente_id",nullable = false)
    private Cliente remitente;

    @ManyToOne
    @JoinColumn(name="destinatario_id",nullable = false)
    private Cliente destinatario;

    @NotBlank
    @Column(nullable = false,length = 100)
    private String direccionEntrega;

    @Transient
    private EnvioEstado estadoEnvio;

    @Column(nullable = false)
    private Boolean comprobanteEntrega;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "envio_id")
    private List<Paquete> paquetes;

    @NotBlank
    @Column(nullable = false,length = 100)
    private String codPostal;

    public void cambiarEstado(EnvioEstado estado) {
        estadoEnvio.procesar(this);
        this.estadoEnvio = estado;
    }
}
