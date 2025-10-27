package demo.mapper.packet;
import demo.dto.packet.PaqueteFragilDTO;
import demo.model.enums.NivelFragilidad;
import demo.model.packet.PaqueteFragil;

public class PaqueteFragilMapper {

    public static PaqueteFragilDTO toDto(PaqueteFragil paqueteFragil) {
        PaqueteFragilDTO dto = new PaqueteFragilDTO();

        // Mapear atributos heredados
        dto.setId(paqueteFragil.getId());
        dto.setCodigo(paqueteFragil.getCodigo());
        dto.setPeso(paqueteFragil.getPeso());
        dto.setVolumen(paqueteFragil.getVolumen());

        // Mapear atributos específicos
        dto.setSeguroAdicional(paqueteFragil.getSeguroAdicional());
        dto.setNivelFragilidad(paqueteFragil.getNivelFragilidad().toString());

        return dto;
    }

    public static PaqueteFragil toEntity(PaqueteFragilDTO dto) {
        PaqueteFragil entity = new PaqueteFragil();

        // Mapear atributos heredados
        entity.setId(dto.getId());
        entity.setCodigo(dto.getCodigo());
        entity.setPeso(dto.getPeso());
        entity.setVolumen(dto.getVolumen());

        // Mapear atributos específicos
        entity.setNivelFragilidad(NivelFragilidad.valueOf(dto.getNivelFragilidad()));
        entity.setSeguroAdicional(dto.isSeguroAdicional());

        return entity;
    }
}