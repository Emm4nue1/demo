package com.example.demo.mapper;
import com.example.demo.dto.EnvioDTO;
import com.example.demo.dto.PaqueteDTO;
import com.example.demo.model.Envio;
import com.example.demo.model.enviostate.*;
import com.example.demo.model.paquete.Paquete;
import com.example.demo.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class EnvioMapper {

    //convertimos de una entidad Envio en DTO
    public static EnvioDTO toDto(Envio envio){

        EnvioDTO dto= new EnvioDTO();
        dto.setId(envio.getId());
        dto.setRemitente(ClienteMapper.toDto(envio.getRemitente()));
        dto.setDestinatario(ClienteMapper.toDto(envio.getDestinatario()));
        dto.setDireccionEntrega(envio.getDireccionEntrega());
        dto.setEstadoEnvio(envio.getEstadoEnvio().getNombreEstado());
        dto.setComprobanteEntrega(envio.getComprobanteEntrega());
        // Nuevo agregado de identificador unico + requiere frio
        dto.setCodPostal(envio.getCodPostal());
        dto.setIdentificadorUnico(envio.getIdentificadorUnico());
        dto.setRequiereFrio(envio.getRequiereFrio());

        if (envio.getPaquetes() != null) {
            List<PaqueteDTO> paquetesDTO = new ArrayList<>();
            for (Paquete p : envio.getPaquetes()) {
                paquetesDTO.add(PaqueteMapper.toDto(p));
            }
            dto.setPaquetes(paquetesDTO);
        }

        return dto;
    }

    //convertimos de DTO a entidad Envio
    public static Envio toEntity(EnvioDTO dto){

        Envio envio= new Envio();
        envio.setRemitente(ClienteMapper.toEntity(dto.getRemitente()));
        envio.setDestinatario(ClienteMapper.toEntity(dto.getDestinatario()));
        envio.setDireccionEntrega(dto.getDireccionEntrega());
        envio.setEstadoEnvio(Utils.crearEstadoDesdeNombre(dto.getEstadoEnvio()));
        envio.setComprobanteEntrega(dto.isComprobanteEntrega());

        // NUEVOS
        if (dto.getCodPostal() != null) {
            envio.setCodPostal(dto.getCodPostal());
        }
        if (dto.getIdentificadorUnico() != null) {
            envio.setIdentificadorUnico(dto.getIdentificadorUnico());
        }
        if (dto.getRequiereFrio() != null) {
            envio.setRequiereFrio(dto.getRequiereFrio());
        } else {
            envio.setRequiereFrio(false);
        }


        envio.setCodPostal(dto.getCodPostal());
        if (dto.getPaquetes() != null) {
            List<Paquete> paquetes = new ArrayList<>();
            for (PaqueteDTO pDto : dto.getPaquetes()) {
                paquetes.add(PaqueteMapper.toEntity(pDto));
            }
            envio.setPaquetes(paquetes);
        }
        return envio;
    }
}
