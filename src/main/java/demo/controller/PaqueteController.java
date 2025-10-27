package demo.controller;
import demo.dto.packet.PaqueteDTO;
import demo.service.PaqueteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/paquetes")
@CrossOrigin
public class PaqueteController {

    private final PaqueteService paqueteService;

    public PaqueteController(PaqueteService paqueteService) {
        this.paqueteService = paqueteService;
    }

    @PostMapping
    public ResponseEntity<PaqueteDTO> crear(@RequestBody PaqueteDTO paqueteDTO) {
        try {
            PaqueteDTO creado = paqueteService.crearPaquete(paqueteDTO);
            return new ResponseEntity<>(creado, HttpStatus.CREATED);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<PaqueteDTO>> listar() {
        List<PaqueteDTO> paquetes = paqueteService.listarPaquetes();
        return new ResponseEntity<>(paquetes, HttpStatus.OK);
    }

    @GetMapping("/fragiles")
    public ResponseEntity<List<PaqueteDTO>> listarFragiles() {
        List<PaqueteDTO> paquetes = paqueteService.listarPaqueteFragil();
        return new ResponseEntity<>(paquetes, HttpStatus.OK);
    }

    @GetMapping("/refrigerados")
    public ResponseEntity<List<PaqueteDTO>> listarRefrigerados() {
        List<PaqueteDTO> paquetes = paqueteService.listarPaqueteRefrigerado();
        return new ResponseEntity<>(paquetes, HttpStatus.OK);
    }

    @GetMapping("/peso")
    public ResponseEntity<List<PaqueteDTO>> listarPorRangoPeso(
            @RequestParam Double min,
            @RequestParam Double max) {
        List<PaqueteDTO> paquetes = paqueteService.listarPaqueteRangoPeso(min, max);
        return new ResponseEntity<>(paquetes, HttpStatus.OK);
    }

    @GetMapping("/volumen")
    public ResponseEntity<List<PaqueteDTO>> listarPorRangoVolumen(
            @RequestParam Double min,
            @RequestParam Double max) {
        List<PaqueteDTO> paquetes = paqueteService.listarPaqueteRangoVolumen(min, max);
        return new ResponseEntity<>(paquetes, HttpStatus.OK);
    }
}