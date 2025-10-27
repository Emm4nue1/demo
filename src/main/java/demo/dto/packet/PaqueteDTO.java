package demo.dto.packet;
import lombok.*;

@Data
public abstract class PaqueteDTO {
    private Long id;
    private String codigo;
    private double peso;
    private double volumen;
}
