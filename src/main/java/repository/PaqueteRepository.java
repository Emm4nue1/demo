package repository;

import model.paquete.Paquete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaqueteRepository extends JpaRepository<Paquete,Long> {

    List<Paquete> findByPesoBetween(double pesoMin, double pesoMax);
    List<Paquete> findByVolumenBetween(double volumenMin, double volumenMax);
    Optional<Paquete> findByCodigo(String codigo);
}
