package com.example.demo.mapper;

import com.example.demo.dto.PaqueteDTO;
import com.example.demo.model.enums.NivelFragilidad;
import com.example.demo.model.paquete.Paquete;
import com.example.demo.model.paquete.PaqueteFragil;
import com.example.demo.model.paquete.PaqueteRefrigerado;

public class PaqueteMapper {

    //convertimos de entidad Paquete a DTO
    public static PaqueteDTO toDto(Paquete paquete){
        PaqueteDTO paqueteDTO = new PaqueteDTO();
        paqueteDTO.setId(paquete.getId());
        paqueteDTO.setCodigo(paquete.getCodigo());
        paqueteDTO.setPeso(paquete.getPeso());
        paqueteDTO.setVolumen(paquete.getVolumen());
        if(paquete instanceof PaqueteFragil){
            PaqueteFragil paqueteFragil = (PaqueteFragil) paquete;
            paqueteDTO.setNivelFragilidad(paqueteFragil.getNivelFragilidad().toString());
            paqueteDTO.setSeguroAdicional(paqueteFragil.getSeguroAdicional());
            paqueteDTO.setTipo("PF");
        }
        else if(paquete instanceof PaqueteRefrigerado){
            PaqueteRefrigerado paqueteRefrigerado = (PaqueteRefrigerado) paquete;
            paqueteDTO.setTemperaturaObjetivo(paqueteRefrigerado.getTemperaturaObjetivo());
            paqueteDTO.setRangoMaximo(paqueteRefrigerado.getRangoMaximo());
            paqueteDTO.setRangoMinimo(paqueteRefrigerado.getRangoMinimo());
            paqueteDTO.setMaxHsFueraFrio(paqueteRefrigerado.getMaxHsFueraFrio());
            paqueteDTO.setTipo("PR");
        }
        return paqueteDTO;

    }

    //convertimos de DTO a entidad PaqueteRefrigerado
    public static Paquete toEntity(PaqueteDTO paqueteDTO){
        if(paqueteDTO.getTipo().equalsIgnoreCase("PF")){
            PaqueteFragil paqueteFragil = new PaqueteFragil();
            paqueteFragil.setCodigo(paqueteDTO.getCodigo());
            paqueteFragil.setPeso(paqueteDTO.getPeso());
            paqueteFragil.setVolumen(paqueteDTO.getVolumen());
            paqueteFragil.setNivelFragilidad(NivelFragilidad.valueOf(paqueteDTO.getNivelFragilidad()));
            paqueteFragil.setSeguroAdicional(paqueteDTO.isSeguroAdicional());
            return paqueteFragil;
        }
        else{
            PaqueteRefrigerado paqueteRefrigerado = new PaqueteRefrigerado();
            paqueteRefrigerado.setCodigo(paqueteDTO.getCodigo());
            paqueteRefrigerado.setPeso(paqueteDTO.getPeso());
            paqueteRefrigerado.setVolumen(paqueteDTO.getVolumen());
            paqueteRefrigerado.setTemperaturaObjetivo(paqueteDTO.getTemperaturaObjetivo());
            paqueteRefrigerado.setRangoMaximo(paqueteDTO.getRangoMaximo());
            paqueteRefrigerado.setRangoMinimo(paqueteDTO.getRangoMinimo());
            paqueteRefrigerado.setMaxHsFueraFrio(paqueteDTO.getMaxHsFueraFrio());
            return paqueteRefrigerado;
        }
    }
}