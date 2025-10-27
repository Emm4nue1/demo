package demo.mapper.packet;
import demo.dto.packet.PaqueteRefrigeradoDTO;
import demo.model.packet.PaqueteRefrigerado;

public class PaqueteRefrigeradoMapper {

    public static PaqueteRefrigeradoDTO toDto(PaqueteRefrigerado paqueteRefrigerado) {
        PaqueteRefrigeradoDTO dto = new PaqueteRefrigeradoDTO();

        // Mapear atributos heredados
        dto.setId(paqueteRefrigerado.getId());
        dto.setCodigo(paqueteRefrigerado.getCodigo());
        dto.setPeso(paqueteRefrigerado.getPeso());
        dto.setVolumen(paqueteRefrigerado.getVolumen());

        // Mapear atributos específicos
        dto.setTemperaturaObjetivo(paqueteRefrigerado.getTemperaturaObjetivo());
        dto.setMaxHsFueraFrio(paqueteRefrigerado.getMaxHsFueraFrio());
        dto.setRangoMaximo(paqueteRefrigerado.getRangoMaximo());
        dto.setRangoMinimo(paqueteRefrigerado.getRangoMinimo());

        return dto;
    }

    public static PaqueteRefrigerado toEntity(PaqueteRefrigeradoDTO dto) {
        PaqueteRefrigerado paqueteRefrigerado = new PaqueteRefrigerado();

        // Mapear atributos heredados
        paqueteRefrigerado.setId(dto.getId());
        paqueteRefrigerado.setCodigo(dto.getCodigo());
        paqueteRefrigerado.setPeso(dto.getPeso());
        paqueteRefrigerado.setVolumen(dto.getVolumen());

        // Mapear atributos específicos
        paqueteRefrigerado.setTemperaturaObjetivo(dto.getTemperaturaObjetivo());
        paqueteRefrigerado.setMaxHsFueraFrio(dto.getMaxHsFueraFrio());
        paqueteRefrigerado.setRangoMaximo(dto.getRangoMaximo());
        paqueteRefrigerado.setRangoMinimo(dto.getRangoMinimo());

        return paqueteRefrigerado;
    }
}