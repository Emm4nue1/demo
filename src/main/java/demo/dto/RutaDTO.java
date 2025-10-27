package demo.dto;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class RutaDTO {
    private Long id;
    private LocalDate fecha;
    private VehiculoDTO vehiculo;
    private List<EnvioDTO> envios;

    private Integer cantidadEnvios;
    private Double pesoTotalCargado;
    private Double volumenTotalCargado;
    private Double porcentajeCapacidadPeso;
    private Double porcentajeCapacidadVolumen;
}
