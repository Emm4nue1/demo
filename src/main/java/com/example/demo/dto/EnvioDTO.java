package com.example.demo.dto;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class EnvioDTO {
    private Long id;
    private String remitente;
    private String destinatario;
    private String direccionEntrega;
    private String estadoEnvio;
    private boolean comprobanteEntrega;
    private List<PaqueteDTO> paquetes;
}
