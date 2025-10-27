package demo.repository;
import demo.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    Optional<Vehiculo> findByPatente(String patente);
    List<Vehiculo> findByCapPesoGreaterThanEqual(Double peso);
    List<Vehiculo> findByCapVolumenGreaterThanEqual(Double volumen);
    List<Vehiculo> findByRefrigeradoTrue();
}