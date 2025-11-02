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
        historialEstadoEnvio.setEstadoAnterior(Utils.crearEstadoDesdeNombre(historialEstadoEnvioDTO.getEstadoAnterior()));
        historialEstadoEnvio.setEstadoNuevo(Utils.crearEstadoDesdeNombre(historialEstadoEnvioDTO.getEstadoNuevo()));
        historialEstadoEnvio.setFecha(LocalDateTime.parse(historialEstadoEnvioDTO.getFecha()));
        return historialEstadoEnvio;
    }

}
