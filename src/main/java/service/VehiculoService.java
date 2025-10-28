package service;
import dto.VehiculoDTO;
import jakarta.transaction.Transactional;
import mapper.VehiculoMapper;
import model.Vehiculo;
import org.springframework.stereotype.Service;
import repository.VehiculoRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class VehiculoService {
    private final VehiculoRepository vehiculoRepository;

    public VehiculoService(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    @Transactional
    public VehiculoDTO crearVehiculo(VehiculoDTO vehiculoDTO) {
        // Validar que no exista un vehículo con la misma patente
        vehiculoRepository.findByPatente(vehiculoDTO.getPatente()).ifPresent(v -> {
            throw new IllegalArgumentException("Ya existe un vehículo con la patente: " + vehiculoDTO.getPatente());
        });
        if (vehiculoDTO.getCapPeso() <= 0) {
            throw new IllegalStateException("La capacidad de peso debe ser mayor a 0");
        }
        if (vehiculoDTO.getCapVolumen() <= 0) {
            throw new IllegalStateException("La capacidad de volumen debe ser mayor a 0");
        }

        if (vehiculoDTO.getRefrigerado()) {
            if (vehiculoDTO.getRangTempMin() == null || vehiculoDTO.getRangTempMax() == null) {
                throw new IllegalStateException("Los vehículos refrigerados deben tener rangos de temperatura");
            }
            if (vehiculoDTO.getRangTempMin() >= vehiculoDTO.getRangTempMax()) {
                throw new IllegalStateException("La temperatura mínima debe ser menor que la máxima");
            }
        }

        Vehiculo vehiculo = VehiculoMapper.toEntity(vehiculoDTO);
        vehiculo = vehiculoRepository.save(vehiculo);
        return VehiculoMapper.toDTO(vehiculo);
    }

    @Transactional
    public List<VehiculoDTO> listarVehiculos() {
        List<Vehiculo> vehiculos = vehiculoRepository.findAll();
        List<VehiculoDTO> vehiculoDTOS = new ArrayList<>();
        for (Vehiculo v : vehiculos) {
            vehiculoDTOS.add(VehiculoMapper.toDTO(v));
        }
        return vehiculoDTOS;
    }

    @Transactional
    public VehiculoDTO buscarPorId(Long id) {
        Vehiculo vehiculo = vehiculoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado con ID: " + id));
        return VehiculoMapper.toDTO(vehiculo);
    }

    @Transactional
    public VehiculoDTO buscarPorPatente(String patente) {
        Vehiculo vehiculo = vehiculoRepository.findByPatente(patente).orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado con patente: " + patente));
        return VehiculoMapper.toDTO(vehiculo);
    }

    @Transactional
    public List<VehiculoDTO> listarPorRefrigerado(Boolean refrigerado) {
        List<Vehiculo> vehiculos = vehiculoRepository.findByRefrigerado(refrigerado);
        List<VehiculoDTO> vehiculoDTOS = new ArrayList<>();
        for (Vehiculo v : vehiculos) {
            vehiculoDTOS.add(VehiculoMapper.toDTO(v));
        }
        return vehiculoDTOS;
    }

    @Transactional
    public List<VehiculoDTO> listarPorCapacidadPeso(Double pesoMinimo) {
        List<Vehiculo> vehiculos = vehiculoRepository.buscarPorCapacidadPeso(pesoMinimo);
        List<VehiculoDTO> vehiculoDTOS = new ArrayList<>();
        for (Vehiculo v : vehiculos) {
            vehiculoDTOS.add(VehiculoMapper.toDTO(v));
        }
        return vehiculoDTOS;
    }

    @Transactional
    public List<VehiculoDTO> listarPorCapacidadVolumen(Double volumenMinimo) {
        List<Vehiculo> vehiculos = vehiculoRepository.buscarPorCapacidadVolumen(volumenMinimo);
        List<VehiculoDTO> vehiculoDTOS = new ArrayList<>();
        for (Vehiculo v : vehiculos) {
            vehiculoDTOS.add(VehiculoMapper.toDTO(v));
        }
        return vehiculoDTOS;
    }

    @Transactional
    public List<VehiculoDTO> listarPorCapacidadSuficiente(Double peso, Double volumen) {
        List<Vehiculo> vehiculos = vehiculoRepository.buscarPorCapacidadSuficiente(peso, volumen);
        List<VehiculoDTO> vehiculoDTOS = new ArrayList<>();
        for (Vehiculo v : vehiculos) {
            vehiculoDTOS.add(VehiculoMapper.toDTO(v));
        }
        return vehiculoDTOS;
    }
}