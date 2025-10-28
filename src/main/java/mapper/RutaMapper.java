package mapper;
import dto.EnvioDTO;
import dto.RutaDTO;
import model.Envio;
import model.Ruta;
import model.paquete.Paquete;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RutaMapper {

    public static RutaDTO toDTO(Ruta ruta) {
        if (ruta == null) return null;

        // Calcular peso y volumen total de la ruta
        double pesoTotal = 0.0;
        double volumenTotal = 0.0;

        if (ruta.getEnvios() != null) {
            for (Envio envio : ruta.getEnvios()) {
                if (envio.getPaquetes() != null) {
                    for (Paquete paquete : envio.getPaquetes()) {
                        pesoTotal += paquete.getPeso();
                        volumenTotal += paquete.getVolumen();
                    }
                }
            }
        }

        Double porcentajePeso = (ruta.getVehiculo().getCapPeso() > 0)
                ? (pesoTotal / ruta.getVehiculo().getCapPeso()) * 100
                : 0.0;

        Double porcentajeVolumen = (ruta.getVehiculo().getCapVolumen() > 0)
                ? (volumenTotal / ruta.getVehiculo().getCapVolumen()) * 100
                : 0.0;

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
                .cantidadEnvios(ruta.getEnvios() != null ? ruta.getEnvios().size() : 0)
                .pesoTotalCargado(pesoTotal)
                .volumenTotalCargado(volumenTotal)
                .porcentajeCapacidadPeso(porcentajePeso)
                .porcentajeCapacidadVolumen(porcentajeVolumen)
                .build();
    }

    public static Ruta toEntity(RutaDTO dto) {
        if (dto == null) return null;

        Ruta ruta = new Ruta();
        ruta.setId(dto.getId());
        ruta.setFecha(dto.getFecha());

        if (dto.getVehiculo() != null) {
            ruta.setVehiculo(VehiculoMapper.toEntity(dto.getVehiculo()));
        }

        if (dto.getEnvios() != null) {
            List<Envio> envios = new ArrayList<>();
            for (EnvioDTO envioDTO : dto.getEnvios()) {
                envios.add(EnvioMapper.toEntity(envioDTO));
            }
            ruta.setEnvios(envios);
        } else {
            ruta.setEnvios(new ArrayList<>());
        }

        return ruta;
    }

    public static List<RutaDTO> toDTOList(List<Ruta> rutas) {
        if (rutas == null) return Collections.emptyList();

        return rutas.stream()
                .map(RutaMapper::toDTO)
                .collect(Collectors.toList());
    }
}