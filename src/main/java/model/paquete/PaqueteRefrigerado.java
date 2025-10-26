package model.paquete;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.*;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

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
