package demo.dto;
import demo.dto.packet.PaqueteDTO;
import lombok.*;

import java.util.List;

@Data
public class EnvioDTO {

    private String remitente;
    private String destinatario;
    private String direccionEntrega;
    private String estadoEnvio;
    private boolean comprobanteEntrega;
    private List<PaqueteDTO> paquetes;


}
