package com.example.demo.repository;

import com.example.demo.model.Envio;
import com.example.demo.model.HistorialEstadoEnvio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HistorialEstadoEnvioRepository extends JpaRepository<HistorialEstadoEnvio,Long> {
    List<HistorialEstadoEnvio> findByEnvio(HistorialEstadoEnvio envio);
}
