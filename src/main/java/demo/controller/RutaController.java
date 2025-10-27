package demo.controller;
import demo.dto.EnvioDTO;
import demo.dto.RutaDTO;
import demo.service.RutaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/rutas")
@CrossOrigin
public class RutaController {

    private final RutaService rutaService;

    public RutaController(RutaService rutaService) {
        this.rutaService = rutaService;
    }

    @PostMapping
    public ResponseEntity<RutaDTO> crear(@RequestBody RutaDTO rutaDTO) {
        try {
            RutaDTO creada = rutaService.crear(rutaDTO);
            return new ResponseEntity<>(creada, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<RutaDTO>> listar() {
        List<RutaDTO> rutas = rutaService.listar();
        return new ResponseEntity<>(rutas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RutaDTO> buscarPorId(@PathVariable Long id) {
        try {
            RutaDTO ruta = rutaService.buscarPorId(id);
            return new ResponseEntity<>(ruta, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<RutaDTO>> buscarPorFecha(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<RutaDTO> rutas = rutaService.buscarPorFecha(fecha);
        return new ResponseEntity<>(rutas, HttpStatus.OK);
    }

    @PostMapping("/{rutaId}/envios/{envioId}")
    public ResponseEntity<RutaDTO> asignarEnvio(
            @PathVariable Long rutaId,
            @PathVariable Long envioId) {
        try {
            RutaDTO rutaActualizada = rutaService.asignarEnvio(rutaId, envioId);
            return new ResponseEntity<>(rutaActualizada, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{rutaId}/estadisticas")
    public ResponseEntity<RutaDTO> obtenerEstadisticas(@PathVariable Long rutaId) {
        try {
            RutaDTO estadisticas = rutaService.calcularEstadisticas(rutaId);
            return new ResponseEntity<>(estadisticas, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{rutaId}/envios")
    public ResponseEntity<List<EnvioDTO>> obtenerEnvios(@PathVariable Long rutaId) {
        try {
            List<EnvioDTO> envios = rutaService.obtenerEnvios(rutaId);
            return new ResponseEntity<>(envios, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}