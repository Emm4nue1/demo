package model.paquete;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import model.enums.NivelFragilidad;
import lombok.*;

@Entity
@DiscriminatorValue(value="PF")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaqueteFragil extends Paquete {

    @Enumerated(EnumType.STRING)
    private NivelFragilidad nivelFragilidad;

    @NotNull
    @Column(nullable = false)
    private Boolean seguroAdicional;
}
