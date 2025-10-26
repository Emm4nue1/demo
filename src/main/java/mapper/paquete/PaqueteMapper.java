package mapper.paquete;

import dto.paquete.PaqueteDTO;
import dto.paquete.PaqueteFragilDTO;
import dto.paquete.PaqueteRefrigeradoDTO;
import model.enums.NivelFragilidad;
import model.paquete.Paquete;
import model.paquete.PaqueteFragil;
import model.paquete.PaqueteRefrigerado;

public class PaqueteMapper {

    //convertimos de entidad Paquete a DTO
    public static PaqueteDTO toSpecificDto(Paquete paquete){

        if(paquete instanceof PaqueteFragil)
            return PaqueteFragilMapper.toDto((PaqueteFragil)paquete);
        else if(paquete instanceof PaqueteRefrigerado)
            return PaqueteRefrigeradoMapper.toDto((PaqueteRefrigerado)paquete);
        return null;

    }

    //convertimos de DTO a entidad PaqueteRefrigerado
    public static Paquete toSpecificEntity(PaqueteDTO paquete){

        if(paquete instanceof PaqueteFragilDTO)
            return PaqueteFragilMapper.toEntity((PaqueteFragilDTO)paquete);
        if(paquete instanceof PaqueteRefrigeradoDTO)
            return PaqueteRefrigeradoMapper.toEntity((PaqueteRefrigeradoDTO)paquete);
        return null;

    }
}