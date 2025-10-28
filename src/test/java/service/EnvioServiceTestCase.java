package service;

import com.example.demo.DemoApplication;
import dto.EnvioDTO;
import dto.PaqueteDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.internal.matchers.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = DemoApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class EnvioServiceTestCase {

    @Autowired
    private EnvioService envioService;

    @Autowired
    private PaqueteService paqueteService;

    PaqueteDTO paqueteDTO1;
    EnvioDTO envioDtoAux;
    EnvioDTO envioDTO1;
    List<PaqueteDTO> paquetesDto;

    @BeforeEach
    public void setUp() {
        paqueteDTO1= PaqueteDTO.builder().codigo("H123").peso(50).volumen(30).
                nivelFragilidad("BAJA").seguroAdicional(true).tipo("PF").build();
        paquetesDto = new ArrayList<>();
        paquetesDto.add(paqueteDTO1);
        envioDTO1 = EnvioDTO.builder().remitente("persona1").destinatario("persona2").direccionEntrega("palpala")
                .estadoEnvio("GENERADO").comprobanteEntrega(true).paquetes(paquetesDto).build();
        envioDtoAux= null;
    }
    @Test
    public void testCrearEnvio(){
        envioDtoAux =envioService.crearEnvio(envioDTO1);
        assertNotNull(envioDtoAux);
    }
    @Test
    public void testListarEnvios(){

        envioDtoAux = EnvioDTO.builder().remitente("persona3").destinatario("persona4").direccionEntrega("canal de Beagle")
                .estadoEnvio("DEVUELTO").comprobanteEntrega(true).paquetes(paquetesDto).build();
        envioService.crearEnvio(envioDTO1);
        envioService.crearEnvio(envioDtoAux);
        assertEquals(2,envioService.listarEnvio().size());

    }
    @Test
    public void testListarEnviosPorRemitente(){
        envioDtoAux = EnvioDTO.builder().remitente("persona1").destinatario("persona4").direccionEntrega("canal de Beagle")
                .estadoEnvio("ENTREGADO").comprobanteEntrega(true).paquetes(paquetesDto).build();
        envioService.crearEnvio(envioDTO1);
        envioService.crearEnvio(envioDtoAux);
        assertEquals(2,envioService.listarPorRemitente("persona1").size());
    }
    @Test
    public void testListarEnviosPorDestinatario(){
        envioDtoAux = EnvioDTO.builder().remitente("persona3").destinatario("persona2").direccionEntrega("gorriti")
                .estadoEnvio("CANCELADO").comprobanteEntrega(true).paquetes(paquetesDto).build();
        envioService.crearEnvio(envioDTO1);
        envioService.crearEnvio(envioDtoAux);
        assertEquals(2,envioService.listarPorDestinatario("persona2").size());
    }
    @Test
    public void testListarEnviosPorEstado(){
        envioDtoAux = EnvioDTO.builder().remitente("persona3").destinatario("persona4").direccionEntrega("nieva")
                .estadoEnvio("GENERADO").comprobanteEntrega(true).paquetes(paquetesDto).build();
        envioService.crearEnvio(envioDTO1);
        envioService.crearEnvio(envioDtoAux);
        assertEquals(2,envioService.listarPorEstado("GENERADO").size());
    }
}
