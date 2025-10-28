package service;
import dto.RutaDTO;
import jakarta.transaction.Transactional;
import mapper.RutaMapper;
import model.Envio;
import model.Ruta;
import model.Vehiculo;
import model.paquete.Paquete;
import model.paquete.PaqueteRefrigerado;
import org.springframework.stereotype.Service;
import repository.EnvioRepository;
import repository.RutaRepository;
import repository.VehiculoRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RutaService {

    private final RutaRepository rutaRepository;
    private final VehiculoRepository vehiculoRepository;
    private final EnvioRepository envioRepository;

    public RutaService(RutaRepository rutaRepository, VehiculoRepository vehiculoRepository, EnvioRepository envioRepository) {
        this.rutaRepository = rutaRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.envioRepository = envioRepository;
    }

    @Transactional
    public RutaDTO crearRuta(RutaDTO rutaDTO) {
        Vehiculo vehiculo = vehiculoRepository.findById(rutaDTO.getVehiculo().getId())
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado"));
        rutaRepository.findByVehiculoAndFecha(vehiculo, rutaDTO.getFecha()).ifPresent(r -> {
            throw new IllegalStateException("El vehículo ya tiene una ruta asignada para la fecha: " + rutaDTO.getFecha());
        });

        // Crear la ruta
        Ruta ruta = new Ruta();
        ruta.setFecha(rutaDTO.getFecha());
        ruta.setVehiculo(vehiculo);
        ruta.setEnvios(new ArrayList<>());

        ruta = rutaRepository.save(ruta);
        return RutaMapper.toDTO(ruta);
    }

    @Transactional
    public RutaDTO agregarEnvio(Long rutaId, Long envioId) {
        Ruta ruta = rutaRepository.findById(rutaId).orElseThrow(() -> new IllegalArgumentException("Ruta no encontrada"));

        Envio envio = envioRepository.findById(envioId).orElseThrow(() -> new IllegalArgumentException("Envío no encontrado"));

        if (ruta.getEnvios().contains(envio)) {
            throw new IllegalStateException("El envío ya está asignado a esta ruta");
        }

        double pesoEnvio = 0.0;
        double volumenEnvio = 0.0;
        boolean tieneRefrigerados = false;

        for (Paquete paquete : envio.getPaquetes()) {
            pesoEnvio += paquete.getPeso();
            volumenEnvio += paquete.getVolumen();
            if (paquete instanceof PaqueteRefrigerado) {
                tieneRefrigerados = true;
            }
        }

        if (tieneRefrigerados && !ruta.getVehiculo().getRefrigerado()) {
            throw new IllegalStateException("El vehículo no es refrigerado pero el envío contiene paquetes refrigerados");
        }

        double pesoActual = calcularPesoTotal(ruta);
        double volumenActual = calcularVolumenTotal(ruta);

        if (pesoActual + pesoEnvio > ruta.getVehiculo().getCapPeso()) {
            throw new IllegalStateException("No hay suficiente capacidad de peso. Disponible: " +
                    (ruta.getVehiculo().getCapPeso() - pesoActual) + " kg, Requerido: " + pesoEnvio + " kg");
        }

        if (volumenActual + volumenEnvio > ruta.getVehiculo().getCapVolumen()) {
            throw new IllegalStateException("No hay suficiente capacidad de volumen. Disponible: " +
                    (ruta.getVehiculo().getCapVolumen() - volumenActual) + " dm³, Requerido: " + volumenEnvio + " dm³");
        }

        ruta.getEnvios().add(envio);
        ruta = rutaRepository.save(ruta);

        return RutaMapper.toDTO(ruta);
    }

    @Transactional
    public List<RutaDTO> listarRutas() {
        List<Ruta> rutas = rutaRepository.findAll();
        return RutaMapper.toDTOList(rutas);
    }

    @Transactional
    public RutaDTO buscarPorId(Long id) {
        Ruta ruta = rutaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Ruta no encontrada con ID: " + id));
        return RutaMapper.toDTO(ruta);
    }

    @Transactional
    public List<RutaDTO> listarPorFecha(LocalDate fecha) {
        List<Ruta> rutas = rutaRepository.findByFecha(fecha);
        return RutaMapper.toDTOList(rutas);
    }

    @Transactional
    public List<RutaDTO> listarPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Ruta> rutas = rutaRepository.findByFechaBetween(fechaInicio, fechaFin);
        return RutaMapper.toDTOList(rutas);
    }

    @Transactional
    public List<RutaDTO> listarRutasConEnvios() {
        List<Ruta> rutas = rutaRepository.findRutasConEnvios();
        return RutaMapper.toDTOList(rutas);
    }

    private double calcularPesoTotal(Ruta ruta) {
        double total = 0.0;
        for (Envio envio : ruta.getEnvios()) {
            for (Paquete paquete : envio.getPaquetes()) {
                total += paquete.getPeso();
            }
        }
        return total;
    }

    private double calcularVolumenTotal(Ruta ruta) {
        double total = 0.0;
        for (Envio envio : ruta.getEnvios()) {
            for (Paquete paquete : envio.getPaquetes()) {
                total += paquete.getVolumen();
            }
        }
        return total;
    }
}