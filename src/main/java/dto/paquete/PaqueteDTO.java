package dto.paquete;
import lombok.*;
import model.enums.NivelFragilidad;

@Data
public abstract class PaqueteDTO {
    private Long id;
    private String codigo;
    private double peso;
    private double volumen;
}
