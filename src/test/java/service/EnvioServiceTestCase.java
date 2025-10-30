package service;
import com.example.demo.DemoApplication;
import com.example.demo.service.EnvioService;
import com.example.demo.service.PaqueteService;
import com.example.demo.dto.EnvioDTO;
import com.example.demo.dto.PaqueteDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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

    PaqueteDTO paqueteDTO1, paqueteDTO2;
    EnvioDTO envioDto, envioDto2;

    @BeforeEach
    public void setUp() {
        paqueteDTO1= PaqueteDTO.builder().codigo("H123").peso(50).volumen(30).
                nivelFragilidad("BAJA").seguroAdicional(true).tipo("PF").build();
        paqueteDTO2= PaqueteDTO.builder().codigo("GG123").peso(50).volumen(30).
                nivelFragilidad("ALTA").seguroAdicional(false).tipo("PF").build();
    }
    @Test
    public void testCrearEnvio(){
        //generamos envio 1 y lo cargamos
        List<PaqueteDTO> lista1 = new ArrayList<>();
        lista1.add(paqueteDTO1);
        envioDto = EnvioDTO.builder().remitente("persona1").destinatario("persona3").direccionEntrega("Rio Blanco").
                estadoEnvio("GENERADO").comprobanteEntrega(true).paquetes(lista1).build();
        assertNotNull(envioService.crearEnvio(envioDto));
    }
    @Test
    public void testListarEnvios(){
        //generamos envio 1 y lo cargamos
        List<PaqueteDTO> lista1 = new ArrayList<>();
        lista1.add(paqueteDTO1);
        envioDto = EnvioDTO.builder().remitente("persona1").destinatario("persona3").direccionEntrega("Rio Blanco").
                estadoEnvio("GENERADO").comprobanteEntrega(true).paquetes(lista1).build();
        assertNotNull(envioService.crearEnvio(envioDto));
        //generamos envio 2 y lo cargamos
        List<PaqueteDTO> lista2 = new ArrayList<>();
        lista2.add(paqueteDTO2);
        envioDto2 = EnvioDTO.builder().remitente("persona2").destinatario("persona4").direccionEntrega("Gorriti").
                estadoEnvio("ENTREGADO").comprobanteEntrega(false).paquetes(lista2).build();
        envioService.crearEnvio(envioDto2);
        //asserts
        assertEquals(2,envioService.listarEnvio().size());
    }
    @Test
    public void testListarEnviosPorRemitente(){
        //generamos envio 1 y lo cargamos
        List<PaqueteDTO> lista1 = new ArrayList<>();
        lista1.add(paqueteDTO1);
        envioDto = EnvioDTO.builder().remitente("persona1").destinatario("persona3").direccionEntrega("Rio Blanco").
                estadoEnvio("GENERADO").comprobanteEntrega(true).paquetes(lista1).build();
        assertNotNull(envioService.crearEnvio(envioDto));
        //generamos envio 2 y lo cargamos
        List<PaqueteDTO> lista2 = new ArrayList<>();
        lista2.add(paqueteDTO2);
        envioDto2 = EnvioDTO.builder().remitente("persona1").destinatario("persona4").direccionEntrega("Gorriti").
                estadoEnvio("ENTREGADO").comprobanteEntrega(false).paquetes(lista2).build();
        envioService.crearEnvio(envioDto2);
        assertEquals(2,envioService.listarPorRemitente("persona1").size());
    }
    @Test
    public void testListarEnviosPorDestinatario(){
        //generamos envio 1 y lo cargamos
        List<PaqueteDTO> lista1 = new ArrayList<>();
        lista1.add(paqueteDTO1);
        envioDto = EnvioDTO.builder().remitente("persona1").destinatario("persona4").direccionEntrega("Rio Blanco").
                estadoEnvio("GENERADO").comprobanteEntrega(true).paquetes(lista1).build();
        assertNotNull(envioService.crearEnvio(envioDto));
        //generamos envio 2 y lo cargamos
        List<PaqueteDTO> lista2 = new ArrayList<>();
        lista2.add(paqueteDTO2);
        envioDto2 = EnvioDTO.builder().remitente("persona2").destinatario("persona4").direccionEntrega("Gorriti").
                estadoEnvio("ENTREGADO").comprobanteEntrega(false).paquetes(lista2).build();
        envioService.crearEnvio(envioDto2);
        assertEquals(2,envioService.listarPorDestinatario("persona4").size());
    }
    @Test
    public void testListarEnviosPorEstado(){
        //generamos envio 1 y lo cargamos
        List<PaqueteDTO> lista1 = new ArrayList<>();
        lista1.add(paqueteDTO1);
        envioDto = EnvioDTO.builder().remitente("persona1").destinatario("persona3").direccionEntrega("Rio Blanco").
                estadoEnvio("GENERADO").comprobanteEntrega(true).paquetes(lista1).build();
        assertNotNull(envioService.crearEnvio(envioDto));
        //generamos envio 2 y lo cargamos
        List<PaqueteDTO> lista2 = new ArrayList<>();
        lista2.add(paqueteDTO2);
        envioDto2 = EnvioDTO.builder().remitente("persona2").destinatario("persona4").direccionEntrega("Gorriti").
                estadoEnvio("GENERADO").comprobanteEntrega(false).paquetes(lista2).build();
        envioService.crearEnvio(envioDto2);
        assertEquals(2,envioService.listarPorEstado("GENERADO").size());
    }
}
