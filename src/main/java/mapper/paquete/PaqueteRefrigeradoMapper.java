package mapper.paquete;

import dto.paquete.PaqueteRefrigeradoDTO;
import model.paquete.PaqueteRefrigerado;

public class PaqueteRefrigeradoMapper{

    //convertimos una entidad PaqueteRefrigerado en DTO
    public static PaqueteRefrigeradoDTO toDto(PaqueteRefrigerado paqueteRefrigerado){

        PaqueteRefrigeradoDTO dto= new PaqueteRefrigeradoDTO();
        dto.setMaxHsFueraFrio(paqueteRefrigerado.getMaxHsFueraFrio());
        dto.setRangoMaximo(paqueteRefrigerado.getRangoMaximo());
        dto.setRangoMinimo(paqueteRefrigerado.getRangoMinimo());
        return dto;

    }

    //convertimos un DTO en una entidad Paquete
    public static PaqueteRefrigerado toEntity(PaqueteRefrigeradoDTO dto){

        PaqueteRefrigerado paqueteRefrigerado= new PaqueteRefrigerado();
        paqueteRefrigerado.setMaxHsFueraFrio(dto.getMaxHsFueraFrio());
        paqueteRefrigerado.setRangoMaximo(dto.getRangoMaximo());
        paqueteRefrigerado.setRangoMinimo(dto.getRangoMinimo());
        return paqueteRefrigerado;

    }
}
