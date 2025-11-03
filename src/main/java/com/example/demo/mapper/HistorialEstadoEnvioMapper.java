package com.example.demo.mapper;

import com.example.demo.dto.ClienteDTO;
import com.example.demo.dto.EnvioDTO;
import com.example.demo.dto.HistorialEstadoEnvioDTO;
import com.example.demo.model.Cliente;
import com.example.demo.model.HistorialEstadoEnvio;
import com.example.demo.model.enviostate.*;
import com.example.demo.util.Utils;

import java.time.LocalDateTime;

public class HistorialEstadoEnvioMapper {
    //convertimos de una entidad HistorialEstadoEnvio en DTO
    public static HistorialEstadoEnvioDTO toDto(HistorialEstadoEnvio historialEstadoEnvio){
        HistorialEstadoEnvioDTO historialEstadoEnvioDTO = new HistorialEstadoEnvioDTO();
        historialEstadoEnvioDTO.setId(historialEstadoEnvio.getId());
        historialEstadoEnvioDTO.setEnvio(EnvioMapper.toDto(historialEstadoEnvio.getEnvio()));
        historialEstadoEnvioDTO.setEstadoAnterior(historialEstadoEnvio.getEstadoAnterior().getNombreEstado());
        historialEstadoEnvioDTO.setEstadoNuevo(historialEstadoEnvio.getEstadoNuevo().getNombreEstado());
        historialEstadoEnvioDTO.setFecha(historialEstadoEnvio.getFecha().toString());
        return historialEstadoEnvioDTO;
    }

    //convertimos de DTO a entidad HistorialEstadoEnvio
    public static HistorialEstadoEnvio toEntity(HistorialEstadoEnvioDTO historialEstadoEnvioDTO){
        HistorialEstadoEnvio historialEstadoEnvio = new HistorialEstadoEnvio();
        historialEstadoEnvio.setId(historialEstadoEnvioDTO.getId());
        historialEstadoEnvio.setEnvio(EnvioMapper.toEntity(historialEstadoEnvioDTO.getEnvio()));
        // CORRECCIÓN: Validar que estadoAnterior no sea null antes de parsear
        if (historialEstadoEnvioDTO.getEstadoAnterior() != null && !historialEstadoEnvioDTO.getEstadoAnterior().isEmpty()) {
            historialEstadoEnvio.setEstadoAnterior(Utils.crearEstadoDesdeNombre(historialEstadoEnvioDTO.getEstadoAnterior()));
        }

        if (historialEstadoEnvioDTO.getEstadoNuevo() != null) {
            historialEstadoEnvio.setEstadoNuevo(Utils.crearEstadoDesdeNombre(historialEstadoEnvioDTO.getEstadoNuevo()));
        }

        // CORRECCIÓN: Validar que la fecha no sea null antes de parsear
        if (historialEstadoEnvioDTO.getFecha() != null && !historialEstadoEnvioDTO.getFecha().isEmpty()) {
            historialEstadoEnvio.setFecha(LocalDateTime.parse(historialEstadoEnvioDTO.getFecha()));
        }
        // Si la fecha es null, se asignará en el service con LocalDateTime.now()

        historialEstadoEnvio.setObservacion(historialEstadoEnvioDTO.getObservacion());
        return historialEstadoEnvio;
    }

}
