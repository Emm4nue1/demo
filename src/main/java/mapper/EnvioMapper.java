package mapper;
import dto.EnvioDTO;
import dto.PaqueteDTO;
import model.Envio;
import model.paquete.Paquete;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EnvioMapper {

    // Convierte Entity a DTO
    public static EnvioDTO toDTO(Envio envio) {
        if (envio == null) return null;

        List<PaqueteDTO> paquetesDTO = envio.getPaquetes() != null
                ? envio.getPaquetes().stream()
                .map(PaqueteMapper::toDTO)
                .collect(Collectors.toList())
                : Collections.emptyList();

        // Calcular totales
        double pesoTotal = paquetesDTO.stream()
                .mapToDouble(PaqueteDTO::getPeso)
                .sum();

        double volumenTotal = paquetesDTO.stream()
                .mapToDouble(PaqueteDTO::getVolumen)
                .sum();

        return EnvioDTO.builder()
                .id(envio.getId())
                .remitente(envio.getRemitente())
                .destinatario(envio.getDestinatario())
                .direccionEntrega(envio.getDireccionEntrega())
                .estadoEnvio(envio.getEstadoEnvio())
                .comprobanteEntrega(envio.getComprobanteEntrega())
                .paquetes(paquetesDTO)
                .cantidadPaquetes(paquetesDTO.size())
                .pesoTotal(pesoTotal)
                .volumenTotal(volumenTotal)
                .build();
    }

    // Convierte DTO a Entity
    public static Envio toEntity(EnvioDTO dto) {
        if (dto == null) return null;

        List<Paquete> paquetes = dto.getPaquetes() != null
                ? dto.getPaquetes().stream()
                .map(PaqueteMapper::toEntity)
                .collect(Collectors.toList())
                : Collections.emptyList();

        return Envio.builder()
                .id(dto.getId())
                .remitente(dto.getRemitente())
                .destinatario(dto.getDestinatario())
                .direccionEntrega(dto.getDireccionEntrega())
                .estadoEnvio(dto.getEstadoEnvio())
                .comprobanteEntrega(dto.getComprobanteEntrega())
                .paquetes(paquetes)
                .build();
    }

    // Método auxiliar para convertir listas
    public static List<EnvioDTO> toDTOList(List<Envio> envios) {
        if (envios == null) return Collections.emptyList();

        return envios.stream()
                .map(EnvioMapper::toDTO)
                .collect(Collectors.toList());
    }
}
