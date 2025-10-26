package mapper;
import dto.EnvioDTO;
import dto.RutaDTO;
import model.Envio;
import model.Ruta;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RutaMapper {

    // Convierte Entity a DTO
    public static RutaDTO toDTO(Ruta ruta) {
        if (ruta == null) return null;

        List<EnvioDTO> enviosDTO = ruta.getEnvios() != null
                ? ruta.getEnvios().stream()
                .map(EnvioMapper::toDTO)
                .collect(Collectors.toList())
                : Collections.emptyList();

        // Calcular totales de peso y volumen
        double pesoTotal = enviosDTO.stream()
                .mapToDouble(EnvioDTO::getPesoTotal)
                .sum();

        double volumenTotal = enviosDTO.stream()
                .mapToDouble(EnvioDTO::getVolumenTotal)
                .sum();

        // Calcular porcentajes de capacidad
        double porcentajePeso = ruta.getVehiculo() != null && ruta.getVehiculo().getCapPeso() > 0
                ? (pesoTotal / ruta.getVehiculo().getCapPeso()) * 100
                : 0.0;

        double porcentajeVolumen = ruta.getVehiculo() != null && ruta.getVehiculo().getCapVolumen() > 0
                ? (volumenTotal / ruta.getVehiculo().getCapVolumen()) * 100
                : 0.0;

        return RutaDTO.builder()
                .id(ruta.getId())
                .fecha(ruta.getFecha())
                .vehiculo(VehiculoMapper.toDTO(ruta.getVehiculo()))
                .envios(enviosDTO)
                .cantidadEnvios(enviosDTO.size())
                .pesoTotalCargado(pesoTotal)
                .volumenTotalCargado(volumenTotal)
                .porcentajeCapacidadPeso(porcentajePeso)
                .porcentajeCapacidadVolumen(porcentajeVolumen)
                .build();
    }

    // Convierte DTO a Entity
    public static Ruta toEntity(RutaDTO dto) {
        if (dto == null) return null;

        List<Envio> envios = dto.getEnvios() != null
                ? dto.getEnvios().stream()
                .map(EnvioMapper::toEntity)
                .collect(Collectors.toList())
                : Collections.emptyList();

        return Ruta.builder()
                .id(dto.getId())
                .fecha(dto.getFecha())
                .vehiculo(VehiculoMapper.toEntity(dto.getVehiculo()))
                .envios(envios)
                .build();
    }

    // Método auxiliar para convertir listas
    public static List<RutaDTO> toDTOList(List<Ruta> rutas) {
        if (rutas == null) return Collections.emptyList();

        return rutas.stream()
                .map(RutaMapper::toDTO)
                .collect(Collectors.toList());
    }
}
