package repository;

import model.paquete.Paquete;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaqueteRepository extends JpaRepository<Paquete,Long> {

    List<Paquete> findByPesoBetween(double pesoMin, double pesoMax);
    List<Paquete> findByVolumenBetween(double volumenMin, double volumenMax);
    List<Paquete> findByClassPaqueteRefrigerados();
    List<Paquete> findByClassPaqueteFragil();

}
