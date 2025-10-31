package com.example.demo.mapper;

import com.example.demo.dto.ClienteDTO;
import com.example.demo.dto.EnvioDTO;
import com.example.demo.dto.HistorialEstadoEnvioDTO;
import com.example.demo.model.Cliente;
import com.example.demo.model.HistorialEstadoEnvio;
import com.example.demo.model.enviostate.*;

import java.time.LocalDateTime;

public class HistorialEstadoEnvioMapper {
    //convertimos de una entidad HistorialEstadoEnvio en DTO
    public static HistorialEstadoEnvioDTO toDto(HistorialEstadoEnvio historialEstadoEnvio){
        HistorialEstadoEnvioDTO historialEstadoEnvioDTO = new HistorialEstadoEnvioDTO();
        historialEstadoEnvioDTO.setId(historialEstadoEnvio.getId());
        historialEstadoEnvioDTO.setEnvio(EnvioMapper.toDto(historialEstadoEnvio.getEnvio()));
        historialEstadoEnvioDTO.setEstadoAnterior(historialEstadoEnvio.getEstadoAnterior().getClass().getSimpleName());
        historialEstadoEnvioDTO.setEstadoNuevo(historialEstadoEnvio.getEstadoNuevo().getClass().getSimpleName());
        historialEstadoEnvioDTO.setFecha(historialEstadoEnvio.getFecha().toString());
        return historialEstadoEnvioDTO;
    }

    //convertimos de DTO a entidad HistorialEstadoEnvio
    public static HistorialEstadoEnvio toEntity(HistorialEstadoEnvioDTO historialEstadoEnvioDTO){
        HistorialEstadoEnvio historialEstadoEnvio = new HistorialEstadoEnvio();
        historialEstadoEnvio.setId(historialEstadoEnvioDTO.getId());
        historialEstadoEnvio.setEnvio(EnvioMapper.toEntity(historialEstadoEnvioDTO.getEnvio()));
        historialEstadoEnvio.setEstadoAnterior(crearEstadoDesdeNombre(historialEstadoEnvioDTO.getEstadoAnterior()));
        historialEstadoEnvio.setEstadoNuevo(crearEstadoDesdeNombre(historialEstadoEnvioDTO.getEstadoNuevo()));
        historialEstadoEnvio.setFecha(LocalDateTime.parse(historialEstadoEnvioDTO.getFecha()));
        return historialEstadoEnvio;
    }
    public static EnvioEstado crearEstadoDesdeNombre(String nombre){
        if (nombre == null)
            throw new IllegalArgumentException("El nombre del estado no puede ser nulo");
        if(nombre.equalsIgnoreCase("Generado"))
            return new Generado();
        else if (nombre.equalsIgnoreCase("EnAlmacen"))
            return new EnAlmacen();
        else if (nombre.equalsIgnoreCase("Cancelado"))
            return new Cancelado();
        else if (nombre.equalsIgnoreCase("Devuelto"))
            return new Devuelto();
        else if (nombre.equalsIgnoreCase("EnRuta"))
            return new EnRuta();
        else if (nombre.equalsIgnoreCase("Entregado"))
            return new Entregado();
        else
            throw new NullPointerException("Nombre invalido de conversion: " + nombre);
    }
}
