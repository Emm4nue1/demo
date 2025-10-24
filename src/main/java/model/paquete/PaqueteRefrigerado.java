package model.paquete;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName = "id")

public class PaqueteRefrigerado extends Paquete {
    @PositiveOrZero
    @Column(nullable = false)
    private Double temperaturaObjetivo;

    @PositiveOrZero
    @Column(nullable = false)
    private Double rangoMinimo;

    @PositiveOrZero
    @Column(nullable = false)
    private Double rangoMaximo;

    @PositiveOrZero
    @Column(nullable = false)
    private Integer maxHsFueraFrio;
}
