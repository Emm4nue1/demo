package com.example.demo.model.paquete;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue(value="PR")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PaqueteRefrigerado extends Paquete {

    //Sin @PositiveOrZero porque las temperaturas pueden ser negativas
    @Column(nullable = true)
    private Double temperaturaObjetivo;

    //@PositiveOrZero
    @Column(nullable = true)
    private Double rangoMinimo;

    //@PositiveOrZero
    @Column(nullable = true)
    private Double rangoMaximo;

    @PositiveOrZero
    @Column(nullable = true)
    private Integer maxHsFueraFrio;
}
