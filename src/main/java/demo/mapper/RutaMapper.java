package demo.mapper;
import demo.dto.EnvioDTO;
import demo.dto.RutaDTO;
import demo.model.Envio;
import demo.model.Ruta;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RutaMapper {

    public static RutaDTO toDTO(Ruta ruta) {
        if (ruta == null) return null;

        List<EnvioDTO> enviosDTO = new ArrayList<>();
        if (ruta.getEnvios() != null) {
            for (Envio envio : ruta.getEnvios()) {
                enviosDTO.add(EnvioMapper.toDto(envio));
            }
        }

        return RutaDTO.builder()
                .id(ruta.getId())
                .fecha(ruta.getFecha())
                .vehiculo(VehiculoMapper.toDTO(ruta.getVehiculo()))
                .envios(enviosDTO)
                .build();
    }

    public static Ruta toEntity(RutaDTO dto) {
        if (dto == null) return null;

        List<Envio> envios = new ArrayList<>();
        if (dto.getEnvios() != null) {
            for (EnvioDTO envioDTO : dto.getEnvios()) {
                envios.add(EnvioMapper.toEntity(envioDTO));
            }
        }

        return Ruta.builder()
                .id(dto.getId())
                .fecha(dto.getFecha())
                .vehiculo(VehiculoMapper.toEntity(dto.getVehiculo()))
                .envios(envios)
                .build();
    }

    public static List<RutaDTO> toDTOList(List<Ruta> rutas) {
        if (rutas == null) return Collections.emptyList();

        return rutas.stream()
                .map(RutaMapper::toDTO)
                .collect(Collectors.toList());
    }
}