package com.example.demo.repository;

import com.example.demo.model.Envio;
import com.example.demo.model.enums.EstadoEnvio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnvioRepository extends JpaRepository<Envio,Long> {
    List<Envio> findByRemitente(String remitente);
    List<Envio> findByDestinatario(String destinatario);
    List<Envio> findByEstadoEnvio(EstadoEnvio estado);
}
