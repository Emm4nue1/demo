package demo.mapper;
import demo.dto.VehiculoDTO;
import demo.model.Vehiculo;


public class VehiculoMapper {
    // Convierte Entity a DTO
    public static VehiculoDTO toDTO(Vehiculo vehiculo) {
        if (vehiculo == null) return null;

        return VehiculoDTO.builder()
                .id(vehiculo.getId())
                .patente(vehiculo.getPatente())
                .capPeso(vehiculo.getCapPeso())
                .capVolumen(vehiculo.getCapVolumen())
                .refrigerado(vehiculo.getRefrigerado())
                .rangTempMin(vehiculo.getRangTempMin())
                .rangTempMax(vehiculo.getRangTempMax())
                .build();
    }

    // Convierte DTO a Entity
    public static Vehiculo toEntity(VehiculoDTO dto) {
        if (dto == null) return null;

        return Vehiculo.builder()
                .id(dto.getId())
                .patente(dto.getPatente())
                .capPeso(dto.getCapPeso())
                .capVolumen(dto.getCapVolumen())
                .refrigerado(dto.getRefrigerado())
                .rangTempMin(dto.getRangTempMin())
                .rangTempMax(dto.getRangTempMax())
                .build();
    }
}
