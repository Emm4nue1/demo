package com.example.demo.dto;
import lombok.*;

import java.util.List;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EnvioDTO {
    private Long id;
    private String remitente;
    private String destinatario;
    private String direccionEntrega;
    private String estadoEnvio;
    private boolean comprobanteEntrega;
    private List<PaqueteDTO> paquetes;


}
