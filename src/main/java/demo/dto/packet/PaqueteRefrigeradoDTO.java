package demo.dto.packet;

import lombok.Data;

@Data
public class PaqueteRefrigeradoDTO extends PaqueteDTO {

    private double temperaturaObjetivo;
    private double rangoMinimo;
    private double rangoMaximo;
    private int maxHsFueraFrio;

}
