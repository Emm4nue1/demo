package dto.paquete;
import lombok.Data;

@Data

public class PaqueteFragilDTO extends dto.paquete.PaqueteDTO{

    private String nivelFragilidad;
    private boolean seguroAdicional;

}
