package dto;
import lombok.*;
import model.enums.NivelFragilidad;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaqueteDTO {
    private Long id;
    private String codigo;
    private Double peso;
    private Double volumen;
    private String tipo; // "FRAGIL" o "REFRIGERADO"

    private NivelFragilidad nivelFragilidad;
    private Boolean seguroAdicional;

    private Double temperaturaObjetivo;
    private Double rangoMinimo;
    private Double rangoMaximo;
    private Integer maxHsFueraFrio;
}
