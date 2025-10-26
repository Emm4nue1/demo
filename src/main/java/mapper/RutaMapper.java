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

        return null;
    }

    // Convierte DTO a Entity
    public static Ruta toEntity(RutaDTO dto) {
        return null;
    }

    // Método auxiliar para convertir listas
    public static List<RutaDTO> toDTOList(List<Ruta> rutas) {
        if (rutas == null) return Collections.emptyList();

        return rutas.stream()
                .map(RutaMapper::toDTO)
                .collect(Collectors.toList());
    }
}
