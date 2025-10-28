package service;

import com.example.demo.DemoApplication;
import dto.EnvioDTO;
import dto.PaqueteDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = DemoApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class EnvioServiceTestCase {

    @Autowired
    private EnvioService envioService;

    @Autowired
    private PaqueteService paqueteService;

    PaqueteDTO paqueteDTO1;
    PaqueteDTO paqueteDTO2;
    EnvioDTO envioDTO1;
    @BeforeEach
    public void setUp() {
        paqueteDTO1= PaqueteDTO.builder().codigo("H123").peso(50).volumen(30).
                nivelFragilidad("BAJA").seguroAdicional(true).tipo("PF").build();
        paqueteService.crearPaquete(paqueteDTO1);
        envioDTO1.builder().remitente("persona1").destinatario("persona2").direccionEntrega("palpala")
                .estadoEnvio("GENERADO").comprobanteEntrega(true).paquetes(paqueteService.listarPaquetes()).build();
    }
    @Test
    public void testCrearEnvio(){

    }
    @Test
    public void testListarEnvios(){

    }
    @Test
    public void testListarEnviosPorRemitente(){

    }
    @Test
    public void testListarEnviosPorDestinatario(){

    }
    @Test
    public void testListarEnviosPorEstado(){

    }
}
