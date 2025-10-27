package demo.controller;
import demo.dto.EnvioDTO;
import demo.service.EnvioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/envios")
@CrossOrigin
public class EnvioController {

    private final EnvioService envioService;

    public EnvioController(EnvioService envioService) {
        this.envioService = envioService;
    }

    @PostMapping
    public ResponseEntity<EnvioDTO> crear(@RequestBody EnvioDTO envioDTO) {
        try {
            EnvioDTO creado = envioService.crearEnvio(envioDTO);
            return new ResponseEntity<>(creado, HttpStatus.CREATED);
        } catch (IllegalStateException | IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<EnvioDTO>> listar() {
        List<EnvioDTO> envios = envioService.listarEnvio();
        return new ResponseEntity<>(envios, HttpStatus.OK);
    }

    @GetMapping("/remitente/{remitente}")
    public ResponseEntity<List<EnvioDTO>> listarPorRemitente(@PathVariable String remitente) {
        List<EnvioDTO> envios = envioService.listarPorRemitente(remitente);
        return new ResponseEntity<>(envios, HttpStatus.OK);
    }

    @GetMapping("/destinatario/{destinatario}")
    public ResponseEntity<List<EnvioDTO>> listarPorDestinatario(@PathVariable String destinatario) {
        List<EnvioDTO> envios = envioService.listarPorDestinatario(destinatario);
        return new ResponseEntity<>(envios, HttpStatus.OK);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<EnvioDTO>> listarPorEstado(@PathVariable String estado) {
        try {
            List<EnvioDTO> envios = envioService.listarPorEstado(estado);
            return new ResponseEntity<>(envios, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}