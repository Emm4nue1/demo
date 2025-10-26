package model.paquete;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(
        name = "paquetes",
        uniqueConstraints = @UniqueConstraint(name = "pq_paquetes_codigo", columnNames = "codigo")
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type")
public class Paquete implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length = 100, nullable = false, unique = true)
    private String codigo;

    @PositiveOrZero
    @Column(nullable = false)
    private Double peso;

    @PositiveOrZero
    @Column(nullable = false)
    private Double volumen;
}
