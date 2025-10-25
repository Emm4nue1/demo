package model.paquete;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import model.enums.NivelFragilidad;
import lombok.*;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PaqueteFragil extends Paquete {

    @Enumerated(EnumType.STRING)
    private NivelFragilidad nivelFragilidad;

    @NotNull
    @Column(nullable = false)
    private Boolean seguroAdicional;
}
