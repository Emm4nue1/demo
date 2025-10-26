package dto.paquete;

import lombok.Data;

@Data
public class PaqueteRefrigeradoDTO extends dto.paquete.PaqueteDTO{

    private double temperaturaObjetivo;
    private double rangoMinimo;
    private double rangoMaximo;
    private int maxHsFueraFrio;

}
