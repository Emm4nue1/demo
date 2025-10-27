package dto;
import dto.paquete.PaqueteDTO;
import lombok.*;
import model.enums.EstadoEnvio;
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
