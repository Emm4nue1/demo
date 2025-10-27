package demo.model.packet;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import demo.model.enums.NivelFragilidad;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue(value="PF")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PaqueteFragil extends Paquete {

    @Enumerated(EnumType.STRING)
    private NivelFragilidad nivelFragilidad;

    @NotNull
    @Column(nullable = false)
    private Boolean seguroAdicional;
}
