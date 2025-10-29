package com.example.demo.model.paquete;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.experimental.SuperBuilder;
import com.example.demo.model.enums.NivelFragilidad;
import lombok.*;

@Entity
@DiscriminatorValue(value="PF")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PaqueteFragil extends Paquete {

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private NivelFragilidad nivelFragilidad;

    @Column(nullable = true)
    private Boolean seguroAdicional;
}
