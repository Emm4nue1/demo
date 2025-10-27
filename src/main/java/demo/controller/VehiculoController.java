package demo.controller;
import demo.dto.VehiculoDTO;
import demo.service.VehiculoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
@CrossOrigin
public class VehiculoController {

    private final VehiculoService vehiculoService;

    public VehiculoController(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }

    @PostMapping
    public ResponseEntity<VehiculoDTO> crear(@RequestBody VehiculoDTO vehiculoDTO) {
        try {
            VehiculoDTO creado = vehiculoService.crear(vehiculoDTO);
            return new ResponseEntity<>(creado, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<VehiculoDTO>> listar() {
        List<VehiculoDTO> vehiculos = vehiculoService.listar();
        return new ResponseEntity<>(vehiculos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehiculoDTO> buscarPorId(@PathVariable Long id) {
        try {
            VehiculoDTO vehiculo = vehiculoService.buscarPorId(id);
            return new ResponseEntity<>(vehiculo, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/patente/{patente}")
    public ResponseEntity<VehiculoDTO> buscarPorPatente(@PathVariable String patente) {
        try {
            VehiculoDTO vehiculo = vehiculoService.buscarPorPatente(patente);
            return new ResponseEntity<>(vehiculo, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/capacidad-peso/{peso}")
    public ResponseEntity<List<VehiculoDTO>> buscarPorCapacidadPeso(@PathVariable Double peso) {
        List<VehiculoDTO> vehiculos = vehiculoService.buscarPorCapacidadPeso(peso);
        return new ResponseEntity<>(vehiculos, HttpStatus.OK);
    }

    @GetMapping("/capacidad-volumen/{volumen}")
    public ResponseEntity<List<VehiculoDTO>> buscarPorCapacidadVolumen(@PathVariable Double volumen) {
        List<VehiculoDTO> vehiculos = vehiculoService.buscarPorCapacidadVolumen(volumen);
        return new ResponseEntity<>(vehiculos, HttpStatus.OK);
    }

    @GetMapping("/refrigerados")
    public ResponseEntity<List<VehiculoDTO>> buscarRefrigerados() {
        List<VehiculoDTO> vehiculos = vehiculoService.buscarRefrigerados();
        return new ResponseEntity<>(vehiculos, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehiculoDTO> actualizar(
            @PathVariable Long id,
            @RequestParam(required = false) Double nuevoPeso,
            @RequestParam(required = false) Double nuevoVolumen) {
        try {
            VehiculoDTO actualizado = vehiculoService.actualizar(id, nuevoPeso, nuevoVolumen);
            return new ResponseEntity<>(actualizado, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}