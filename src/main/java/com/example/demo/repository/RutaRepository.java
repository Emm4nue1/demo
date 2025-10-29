package com.example.demo.repository;
import com.example.demo.model.Ruta;
import com.example.demo.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RutaRepository extends JpaRepository<Ruta, Long> {

    List<Ruta> findByFecha(LocalDate fecha);
    List<Ruta> findByFechaBetween(LocalDate fechaInicio, LocalDate fechaFin);
    List<Ruta> findByVehiculo(Vehiculo vehiculo);
    Optional<Ruta> findByVehiculoAndFecha(Vehiculo vehiculo, LocalDate fecha);

    @Query("SELECT r FROM Ruta r WHERE SIZE(r.envios) > 0")
    List<Ruta> findRutasConEnvios();
    @Query("SELECT r FROM Ruta r WHERE r.fecha = :fecha AND SIZE(r.envios) > 0")
    List<Ruta> findByFechaConEnvios(@Param("fecha") LocalDate fecha);
}