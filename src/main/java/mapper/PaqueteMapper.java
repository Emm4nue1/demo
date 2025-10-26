package mapper;
import dto.PaqueteDTO;
import model.paquete.Paquete;
import model.paquete.PaqueteFragil;
import model.paquete.PaqueteRefrigerado;

public class PaqueteMapper {

    // Convierte Entity a DTO
    public static PaqueteDTO toDTO(Paquete paquete) {
        if (paquete == null) return null;

        PaqueteDTO dto = PaqueteDTO.builder()
                .id(paquete.getId())
                .codigo(paquete.getCodigo())
                .peso(paquete.getPeso())
                .volumen(paquete.getVolumen())
                .build();

        // Identificar tipo y agregar atributos específicos
        if (paquete instanceof PaqueteFragil fragil) {
            dto.setTipo("FRAGIL");
            dto.setNivelFragilidad(fragil.getNivelFragilidad());
            dto.setSeguroAdicional(fragil.getSeguroAdicional());
        } else if (paquete instanceof PaqueteRefrigerado refrigerado) {
            dto.setTipo("REFRIGERADO");
            dto.setTemperaturaObjetivo(refrigerado.getTemperaturaObjetivo());
            dto.setRangoMinimo(refrigerado.getRangoMinimo());
            dto.setRangoMaximo(refrigerado.getRangoMaximo());
            dto.setMaxHsFueraFrio(refrigerado.getMaxHsFueraFrio());
        }

        return dto;
    }

    // Convierte DTO a Entity
    public static Paquete toEntity(PaqueteDTO dto) {
        if (dto == null) return null;

        if ("FRAGIL".equals(dto.getTipo())) {
            PaqueteFragil fragil = new PaqueteFragil();
            fragil.setId(dto.getId());
            fragil.setCodigo(dto.getCodigo());
            fragil.setPeso(dto.getPeso());
            fragil.setVolumen(dto.getVolumen());
            fragil.setNivelFragilidad(dto.getNivelFragilidad());
            fragil.setSeguroAdicional(dto.getSeguroAdicional());
            return fragil;
        } else if ("REFRIGERADO".equals(dto.getTipo())) {
            PaqueteRefrigerado refrigerado = new PaqueteRefrigerado();
            refrigerado.setId(dto.getId());
            refrigerado.setCodigo(dto.getCodigo());
            refrigerado.setPeso(dto.getPeso());
            refrigerado.setVolumen(dto.getVolumen());
            refrigerado.setTemperaturaObjetivo(dto.getTemperaturaObjetivo());
            refrigerado.setRangoMinimo(dto.getRangoMinimo());
            refrigerado.setRangoMaximo(dto.getRangoMaximo());
            refrigerado.setMaxHsFueraFrio(dto.getMaxHsFueraFrio());
            return refrigerado;
        }

        return null;
    }
}