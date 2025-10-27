package demo.repository;
import demo.model.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RutaRepository extends JpaRepository<Ruta, Long> {
    List<Ruta> findByFecha(LocalDate fecha);
    Optional<Ruta> findByVehiculoId(Long vehiculoId);
}