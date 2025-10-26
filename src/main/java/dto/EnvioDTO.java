package dto;
import lombok.*;
import model.enums.EstadoEnvio;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class EnvioDTO {
    private Long id;
    private String remitente;
    private String destinatario;
    private String direccionEntrega;
    private EstadoEnvio estadoEnvio;
    private Boolean comprobanteEntrega;
    private List<PaqueteDTO> paquetes;

    private Integer cantidadPaquetes;
    private Double pesoTotal;
    private Double volumenTotal;
}
