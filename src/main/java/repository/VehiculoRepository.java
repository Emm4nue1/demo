package repository;
import model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {

    Optional<Vehiculo> findByPatente(String patente);
    List<Vehiculo> findByRefrigerado(Boolean refrigerado);

    @Query("SELECT v FROM Vehiculo v WHERE v.capPeso >= :pesoMinimo")
    List<Vehiculo> buscarPorCapacidadPeso(@Param("pesoMinimo") Double pesoMinimo);

    @Query("SELECT v FROM Vehiculo v WHERE v.capVolumen >= :volumenMinimo")
    List<Vehiculo> buscarPorCapacidadVolumen(@Param("volumenMinimo") Double volumenMinimo);

    @Query("SELECT v FROM Vehiculo v WHERE v.capPeso >= :peso AND v.capVolumen >= :volumen")
    List<Vehiculo> buscarPorCapacidadSuficiente(@Param("peso") Double peso, @Param("volumen") Double volumen);

    @Query("SELECT v FROM Vehiculo v WHERE v.refrigerado = true AND v.capPeso >= :peso AND v.capVolumen >= :volumen")
    List<Vehiculo> buscarRefrigeradosConCapacidad(@Param("peso") Double peso, @Param("volumen") Double volumen);
}