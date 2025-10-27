package demo.dto.packet;
import lombok.Data;

@Data

public class PaqueteFragilDTO extends PaqueteDTO {

    private String nivelFragilidad;
    private boolean seguroAdicional;

}
