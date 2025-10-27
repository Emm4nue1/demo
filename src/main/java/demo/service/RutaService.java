package demo.service;
import demo.dto.EnvioDTO;
import demo.dto.RutaDTO;
import jakarta.transaction.Transactional;
import demo.mapper.EnvioMapper;
import demo.mapper.VehiculoMapper;
import demo.model.Envio;
import demo.model.Ruta;
import demo.model.Vehiculo;
import demo.model.enums.EstadoEnvio;
import demo.model.paquete.Paquete;
import demo.model.paquete.PaqueteRefrigerado;
import org.springframework.stereotype.Service;
import demo.repository.EnvioRepository;
import demo.repository.RutaRepository;
import demo.repository.VehiculoRepository;

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
    public RutaDTO crear(RutaDTO rutaDTO) {
        if (rutaDTO.getVehiculo() == null || rutaDTO.getVehiculo().getId() == null) {
            throw new IllegalArgumentException("Debe especificar un vehículo para la ruta");
        }

        Vehiculo vehiculo = vehiculoRepository.findById(rutaDTO.getVehiculo().getId())
                .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado con id: " + rutaDTO.getVehiculo().getId()));

        rutaRepository.findByVehiculoId(vehiculo.getId()).ifPresent(r -> {
            throw new IllegalArgumentException("El vehículo ya está asignado a otra ruta");
        });

        Ruta ruta = Ruta.builder()
                .fecha(rutaDTO.getFecha() != null ? rutaDTO.getFecha() : LocalDate.now())
                .vehiculo(vehiculo)
                .envios(new ArrayList<>())
                .build();

        ruta = rutaRepository.save(ruta);
        return calcularEstadisticas(ruta.getId());
    }

    @Transactional
    public List<RutaDTO> listar() {
        List<Ruta> rutas = rutaRepository.findAll();
        List<RutaDTO> listaRutasDTO = new ArrayList<>();
        for (Ruta ruta : rutas) {
            listaRutasDTO.add(calcularEstadisticas(ruta.getId()));
        }
        return listaRutasDTO;
    }

    @Transactional
    public RutaDTO buscarPorId(Long id) {
        rutaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ruta no encontrada con id: " + id));
        return calcularEstadisticas(id);
    }

    @Transactional
    public List<RutaDTO> buscarPorFecha(LocalDate fecha) {
        List<Ruta> rutas = rutaRepository.findByFecha(fecha);
        List<RutaDTO> listaRutasDTO = new ArrayList<>();
        for (Ruta ruta : rutas) {
            listaRutasDTO.add(calcularEstadisticas(ruta.getId()));
        }
        return listaRutasDTO;
    }

    @Transactional
    public RutaDTO asignarEnvio(Long rutaId, Long envioId) {
        Ruta ruta = rutaRepository.findById(rutaId)
                .orElseThrow(() -> new IllegalArgumentException("Ruta no encontrada con id: " + rutaId));

        Envio envio = envioRepository.findById(envioId)
                .orElseThrow(() -> new IllegalArgumentException("Envío no encontrado con id: " + envioId));

        Vehiculo vehiculo = ruta.getVehiculo();

        // Validar compatibilidad de refrigeración
        for (Paquete paquete : envio.getPaquetes()) {
            if (paquete instanceof PaqueteRefrigerado) {
                if (!vehiculo.getRefrigerado()) {
                    throw new IllegalArgumentException("El envío contiene paquetes refrigerados pero el vehículo no tiene refrigeración");
                }

                PaqueteRefrigerado paqueteRef = (PaqueteRefrigerado) paquete;
                if (paqueteRef.getRangoMinimo() < vehiculo.getRangTempMin() ||
                        paqueteRef.getRangoMaximo() > vehiculo.getRangTempMax()) {
                    throw new IllegalArgumentException("El rango de temperatura del packet " + paquete.getCodigo() +
                            " no es compatible con el vehículo");
                }
            }
        }

        // Calcular pesos y volúmenes actuales de la ruta
        double pesoActual = 0;
        double volumenActual = 0;
        if (ruta.getEnvios() != null) {
            for (Envio e : ruta.getEnvios()) {
                for (Paquete p : e.getPaquetes()) {
                    pesoActual += p.getPeso();
                    volumenActual += p.getVolumen();
                }
            }
        }

        // Calcular peso y volumen del nuevo envío
        double pesoNuevo = 0;
        double volumenNuevo = 0;
        for (Paquete p : envio.getPaquetes()) {
            pesoNuevo += p.getPeso();
            volumenNuevo += p.getVolumen();
        }

        // Validar capacidades
        if ((pesoActual + pesoNuevo) > vehiculo.getCapPeso()) {
            throw new IllegalArgumentException("Se supera la capacidad de peso del vehículo. " +
                    "Capacidad: " + vehiculo.getCapPeso() + " kg, " +
                    "Cargado: " + pesoActual + " kg, " +
                    "Nuevo envío: " + pesoNuevo + " kg");
        }

        if ((volumenActual + volumenNuevo) > vehiculo.getCapVolumen()) {
            throw new IllegalArgumentException("Se supera la capacidad de volumen del vehículo. " +
                    "Capacidad: " + vehiculo.getCapVolumen() + " dm³, " +
                    "Cargado: " + volumenActual + " dm³, " +
                    "Nuevo envío: " + volumenNuevo + " dm³");
        }

        // Asignar envío a la ruta
        envio.setEstadoEnvio(EstadoEnvio.EN_RUTA);
        if (ruta.getEnvios() == null) {
            ruta.setEnvios(new ArrayList<>());
        }
        ruta.getEnvios().add(envio);

        envioRepository.save(envio);
        rutaRepository.save(ruta);

        return calcularEstadisticas(ruta.getId());
    }

    @Transactional
    public RutaDTO calcularEstadisticas(Long rutaId) {
        Ruta ruta = rutaRepository.findById(rutaId)
                .orElseThrow(() -> new IllegalArgumentException("Ruta no encontrada con id: " + rutaId));

        double pesoTotal = 0;
        double volumenTotal = 0;
        int cantidadEnvios = 0;

        if (ruta.getEnvios() != null) {
            cantidadEnvios = ruta.getEnvios().size();
            for (Envio envio : ruta.getEnvios()) {
                if (envio.getPaquetes() != null) {
                    for (Paquete paquete : envio.getPaquetes()) {
                        pesoTotal += paquete.getPeso();
                        volumenTotal += paquete.getVolumen();
                    }
                }
            }
        }

        Vehiculo vehiculo = ruta.getVehiculo();
        double porcentajePeso = (pesoTotal / vehiculo.getCapPeso()) * 100;
        double porcentajeVolumen = (volumenTotal / vehiculo.getCapVolumen()) * 100;

        List<EnvioDTO> enviosDTO = new ArrayList<>();
        if (ruta.getEnvios() != null) {
            for (Envio envio : ruta.getEnvios()) {
                enviosDTO.add(EnvioMapper.toDto(envio));
            }
        }

        return RutaDTO.builder()
                .id(ruta.getId())
                .fecha(ruta.getFecha())
                .vehiculo(VehiculoMapper.toDTO(vehiculo))
                .envios(enviosDTO)
                .cantidadEnvios(cantidadEnvios)
                .pesoTotalCargado(pesoTotal)
                .volumenTotalCargado(volumenTotal)
                .porcentajeCapacidadPeso(porcentajePeso)
                .porcentajeCapacidadVolumen(porcentajeVolumen)
                .build();
    }

    @Transactional
    public List<EnvioDTO> obtenerEnvios(Long rutaId) {
        Ruta ruta = rutaRepository.findById(rutaId)
                .orElseThrow(() -> new IllegalArgumentException("Ruta no encontrada con id: " + rutaId));

        List<EnvioDTO> enviosDTO = new ArrayList<>();
        if (ruta.getEnvios() != null) {
            for (Envio envio : ruta.getEnvios()) {
                enviosDTO.add(EnvioMapper.toDto(envio));
            }
        }
        return enviosDTO;
    }
}