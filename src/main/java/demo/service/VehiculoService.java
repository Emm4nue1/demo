package demo.service;
import demo.dto.VehiculoDTO;
import jakarta.transaction.Transactional;
import demo.mapper.VehiculoMapper;
import demo.model.Vehiculo;
import org.springframework.stereotype.Service;
import demo.repository.VehiculoRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;

    public VehiculoService(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    @Transactional
    public VehiculoDTO crear(VehiculoDTO vehiculoDTO) {
        vehiculoRepository.findByPatente(vehiculoDTO.getPatente()).ifPresent(v -> {
            throw new IllegalArgumentException("Patente ya registrada: " + vehiculoDTO.getPatente());
        });

        if (vehiculoDTO.getCapPeso() <= 0) {
            throw new IllegalArgumentException("La capacidad de peso debe ser positiva");
        }

        if (vehiculoDTO.getCapVolumen() <= 0) {
            throw new IllegalArgumentException("La capacidad de volumen debe ser positiva");
        }

        if (vehiculoDTO.getRefrigerado()) {
            if (vehiculoDTO.getRangTempMin() >= vehiculoDTO.getRangTempMax()) {
                throw new IllegalArgumentException("El rango de temperatura mínima debe ser menor que el máximo");
            }
        }

        Vehiculo vehiculo = VehiculoMapper.toEntity(vehiculoDTO);
        vehiculo = vehiculoRepository.save(vehiculo);
        return VehiculoMapper.toDTO(vehiculo);
    }

    @Transactional
    public List<VehiculoDTO> listar() {
        List<Vehiculo> vehiculos = vehiculoRepository.findAll();
        List<VehiculoDTO> listaVehiculosDTO = new ArrayList<>();
        for (Vehiculo vehiculo : vehiculos) {
            listaVehiculosDTO.add(VehiculoMapper.toDTO(vehiculo));
        }
        return listaVehiculosDTO;
    }

    @Transactional
    public VehiculoDTO buscarPorId(Long id) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado con id: " + id));
        return VehiculoMapper.toDTO(vehiculo);
    }

    @Transactional
    public VehiculoDTO buscarPorPatente(String patente) {
        Vehiculo vehiculo = vehiculoRepository.findByPatente(patente)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado con patente: " + patente));
        return VehiculoMapper.toDTO(vehiculo);
    }

    @Transactional
    public List<VehiculoDTO> buscarPorCapacidadPeso(Double pesoMinimo) {
        List<Vehiculo> vehiculos = vehiculoRepository.findByCapPesoGreaterThanEqual(pesoMinimo);
        List<VehiculoDTO> listaVehiculosDTO = new ArrayList<>();
        for (Vehiculo vehiculo : vehiculos) {
            listaVehiculosDTO.add(VehiculoMapper.toDTO(vehiculo));
        }
        return listaVehiculosDTO;
    }

    @Transactional
    public List<VehiculoDTO> buscarPorCapacidadVolumen(Double volumenMinimo) {
        List<Vehiculo> vehiculos = vehiculoRepository.findByCapVolumenGreaterThanEqual(volumenMinimo);
        List<VehiculoDTO> listaVehiculosDTO = new ArrayList<>();
        for (Vehiculo vehiculo : vehiculos) {
            listaVehiculosDTO.add(VehiculoMapper.toDTO(vehiculo));
        }
        return listaVehiculosDTO;
    }

    @Transactional
    public List<VehiculoDTO> buscarRefrigerados() {
        List<Vehiculo> vehiculos = vehiculoRepository.findByRefrigeradoTrue();
        List<VehiculoDTO> listaVehiculosDTO = new ArrayList<>();
        for (Vehiculo vehiculo : vehiculos) {
            listaVehiculosDTO.add(VehiculoMapper.toDTO(vehiculo));
        }
        return listaVehiculosDTO;
    }

    @Transactional
    public VehiculoDTO actualizar(Long id, Double nuevoPeso, Double nuevoVolumen) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado con id: " + id));

        if (nuevoPeso != null && nuevoPeso > 0) {
            vehiculo.setCapPeso(nuevoPeso);
        }

        if (nuevoVolumen != null && nuevoVolumen > 0) {
            vehiculo.setCapVolumen(nuevoVolumen);
        }

        vehiculo = vehiculoRepository.save(vehiculo);
        return VehiculoMapper.toDTO(vehiculo);
    }
}