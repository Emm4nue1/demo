package com.example.demo.service;

import com.example.demo.dto.HistorialEstadoEnvioDTO;
import com.example.demo.mapper.HistorialEstadoEnvioMapper;
import com.example.demo.model.HistorialEstadoEnvio;
import com.example.demo.model.enviostate.EnvioEstado;
import com.example.demo.repository.EnvioRepository;
import com.example.demo.repository.HistorialEstadoEnvioRepository;
import jakarta.persistence.Entity;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class HistorialEstadoEnvioService {
    private final HistorialEstadoEnvioRepository historialEstadoEnvioRepository;
    private final EnvioRepository envioRepository;

    public HistorialEstadoEnvioService(HistorialEstadoEnvioRepository historialEstadoEnvioRepository, EnvioRepository envioRepository) {
        this.historialEstadoEnvioRepository = historialEstadoEnvioRepository;
        this.envioRepository = envioRepository;
    }

    @Transactional
    public HistorialEstadoEnvioDTO crearHistorialEstadoEnvio(HistorialEstadoEnvioDTO historialEstadoEnvioDTO, EnvioEstado estadoAnterior) {
        validacionCrearHistorialEstadoEnvio(historialEstadoEnvioDTO);
        HistorialEstadoEnvio historialEstadoEnvio = HistorialEstadoEnvioMapper.toEntity(historialEstadoEnvioDTO);
        historialEstadoEnvio.setEstadoAnterior(estadoAnterior);
        if(historialEstadoEnvio.getFecha()==null)
            historialEstadoEnvio.setFecha(LocalDateTime.now());
        historialEstadoEnvioRepository.save(historialEstadoEnvio);
        return HistorialEstadoEnvioMapper.toDto(historialEstadoEnvio);
    }

    private void validacionCrearHistorialEstadoEnvio(HistorialEstadoEnvioDTO historialEstadoEnvioDTO){
        if(historialEstadoEnvioDTO.getEnvio() == null)
            throw new IllegalArgumentException("El envío asociado al historial no puede ser nulo");
    }

}
