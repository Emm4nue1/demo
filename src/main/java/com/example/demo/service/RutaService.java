package com.example.demo.service;
import com.example.demo.dto.RutaDTO;
import jakarta.transaction.Transactional;
import com.example.demo.mapper.RutaMapper;
import com.example.demo.model.Envio;
import com.example.demo.model.Ruta;
import com.example.demo.model.Vehiculo;
import com.example.demo.model.paquete.Paquete;
import com.example.demo.model.paquete.PaqueteRefrigerado;
import org.springframework.stereotype.Service;
import com.example.demo.repository.EnvioRepository;
import com.example.demo.repository.RutaRepository;
import com.example.demo.repository.VehiculoRepository;
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

        Ruta ruta = new Ruta();
        ruta.setFecha(rutaDTO.getFecha());
        ruta.setVehiculo(vehiculo);
        ruta.setEnvios(new ArrayList<>());

        ruta = rutaRepository.save(ruta);
        return RutaMapper.toDTO(ruta);
    }

    @Transactional
    public RutaDTO agregarEnvio(Long rutaId, Long envioId) {
        Ruta ruta = rutaRepository.findById(rutaId)
                .orElseThrow(() -> new IllegalArgumentException("Ruta no encontrada"));

        Envio envio = envioRepository.findById(envioId)
                .orElseThrow(() -> new IllegalArgumentException("Envío no encontrado"));

        if (ruta.getEnvios().contains(envio)) {
            throw new IllegalStateException("El envío ya está asignado a esta ruta");
        }

        // Calcular peso, volumen y detectar paquetes refrigerados
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

        // ========== VALIDACIÓN 1: Vehículo debe ser refrigerado si hay paquetes refrigerados ==========
        if (tieneRefrigerados && !ruta.getVehiculo().getRefrigerado()) {
            throw new IllegalStateException("El vehículo no es refrigerado pero el envío contiene paquetes refrigerados");
        }

        // ========== VALIDACIÓN 2: COMPATIBILIDAD DE TEMPERATURA (TU PARTE PRINCIPAL) ==========
        if (tieneRefrigerados && ruta.getVehiculo().getRefrigerado()) {
            for (Paquete paquete : envio.getPaquetes()) {
                if (paquete instanceof PaqueteRefrigerado) {
                    PaqueteRefrigerado paqueteRefrig = (PaqueteRefrigerado) paquete;

                    Double rangoMinPaquete = paqueteRefrig.getRangoMinimo();
                    Double rangoMaxPaquete = paqueteRefrig.getRangoMaximo();
                    Double rangTempMinVehiculo = ruta.getVehiculo().getRangTempMin();
                    Double rangTempMaxVehiculo = ruta.getVehiculo().getRangTempMax();

                    // Validar que el rango del paquete esté completamente dentro del rango del vehículo
                    if (rangoMinPaquete < rangTempMinVehiculo || rangoMaxPaquete > rangTempMaxVehiculo) {
                        throw new IllegalStateException(
                                String.format("Temperatura incompatible. Paquete requiere [%.1f°C a %.1f°C] pero el vehículo soporta [%.1f°C a %.1f°C]",
                                        rangoMinPaquete, rangoMaxPaquete, rangTempMinVehiculo, rangTempMaxVehiculo)
                        );
                    }
                }
            }
        }

        // ========== VALIDACIÓN 3: Capacidad de peso ==========
        double pesoActual = calcularPesoTotal(ruta);
        if (pesoActual + pesoEnvio > ruta.getVehiculo().getCapPeso()) {
            throw new IllegalStateException("No hay suficiente capacidad de peso. Disponible: " +
                    (ruta.getVehiculo().getCapPeso() - pesoActual) + " kg, Requerido: " + pesoEnvio + " kg");
        }

        // ========== VALIDACIÓN 4: Capacidad de volumen ==========
        double volumenActual = calcularVolumenTotal(ruta);
        if (volumenActual + volumenEnvio > ruta.getVehiculo().getCapVolumen()) {
            throw new IllegalStateException("No hay suficiente capacidad de volumen. Disponible: " +
                    (ruta.getVehiculo().getCapVolumen() - volumenActual) + " dm³, Requerido: " + volumenEnvio + " dm³");
        }

        // Si todas las validaciones pasaron, agregar el envío
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
        Ruta ruta = rutaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ruta no encontrada con ID: " + id));
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

    // Métodos auxiliares para cálculos
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