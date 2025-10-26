package mapper.paquete;

import dto.paquete.PaqueteFragilDTO;
import model.enums.NivelFragilidad;
import model.paquete.PaqueteFragil;

public class PaqueteFragilMapper{
    //convertimos de una entidad PaqueteRefrigerado en DTO
    public static PaqueteFragilDTO toDto(PaqueteFragil paqueteFragil){

        PaqueteFragilDTO dto= new PaqueteFragilDTO();
        dto.setSeguroAdicional(paqueteFragil.getSeguroAdicional());
        dto.setNivelFragilidad(paqueteFragil.getNivelFragilidad().toString());
        return dto;
    }

    //convertimos de DTO a entidad PaqueteRefrigerado
    public static PaqueteFragil toEntity(PaqueteFragilDTO dto){

        PaqueteFragil entity= new PaqueteFragil();
        entity.setNivelFragilidad(NivelFragilidad.valueOf(dto.getNivelFragilidad()));
        entity.setSeguroAdicional(dto.isSeguroAdicional());
        return entity;

    }
}
