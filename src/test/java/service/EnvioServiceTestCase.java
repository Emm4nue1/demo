package service;
import com.example.demo.DemoApplication;
import com.example.demo.dto.ClienteDTO;
import com.example.demo.service.ClienteService;
import com.example.demo.service.EnvioService;
import com.example.demo.service.PaqueteService;
import com.example.demo.dto.EnvioDTO;
import com.example.demo.dto.PaqueteDTO;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class EnvioServiceTestCase {

    @Autowired
    private EnvioService envioService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PaqueteService paqueteService;

    PaqueteDTO paqueteDTO1, paqueteDTO2;
    EnvioDTO envioDto, envioDto2;
    ClienteDTO clienteDTO1, clienteDTO2;

    @BeforeEach
    public void setUp() {
        //solamente cargamos los paquetes
        paqueteDTO1= PaqueteDTO.builder().codigo("H123").peso(50).volumen(30).
                nivelFragilidad("BAJA").seguroAdicional(true).tipo("PF").build();
        paqueteDTO2= PaqueteDTO.builder().codigo("GG123").peso(50).volumen(30).
                nivelFragilidad("ALTA").seguroAdicional(false).tipo("PF").build();
        clienteDTO1 = ClienteDTO.builder()
                .nombre("Juan")
                .apellido("Perez")
                .dni("45492312")
                .email("persona1@gmail.com")
                .calle("Calle Falsa")
                .numCasa(123L)
                .codPostal("4116")
                .build();

        clienteDTO2 = ClienteDTO.builder()
                .nombre("Maria")
                .apellido("Gomez")
                .dni("46523942")
                .email("persona2@gmail.com")
                .calle("Av. Siempre Viva")
                .numCasa(456L)
                .codPostal("9431")
                .build();
    }
    @Test
    public void testCrearEnvio(){
        //cargamos a los clientes
       ClienteDTO clienteAux1 = clienteService.crearCliente(clienteDTO1);
        ClienteDTO clienteAux2 =clienteService.crearCliente(clienteDTO2);
        //generamos envio 1 y lo cargamos
        List<PaqueteDTO> lista1 = new ArrayList<>();
        lista1.add(paqueteDTO1);
        envioDto = EnvioDTO.builder().remitente(clienteAux1).destinatario(clienteAux2).direccionEntrega("Rio Blanco").estadoEnvio(null).comprobanteEntrega(true).paquetes(lista1).build();
        envioDto.setEstadoEnvio("");
        envioDto.setCodPostal("1234");
        assertNotNull(envioService.crearEnvio(envioDto));
    }
//    @Test
//    public void testListarEnvios(){
//        //cargamos el cliente 1 y 2
//        clienteService.crearCliente(clienteDTO1);
//        clienteService.crearCliente(clienteDTO2);
//        //generamos envio 1 y lo cargamos
//        List<PaqueteDTO> lista1 = new ArrayList<>();
//        lista1.add(paqueteDTO1);
//        envioDto = EnvioDTO.builder().remitente(clienteDTO1).destinatario(clienteDTO2).direccionEntrega("Rio Blanco").
//                estadoEnvio(null).comprobanteEntrega(true).paquetes(lista1).build();
//        assertNotNull(envioService.crearEnvio(envioDto));
//        //generamos envio 2 y lo cargamos
//        List<PaqueteDTO> lista2 = new ArrayList<>();
//        lista2.add(paqueteDTO2);
//        envioDto2 = EnvioDTO.builder().remitente(clienteDTO2).destinatario(clienteDTO1).direccionEntrega("Gorriti").
//                estadoEnvio(null).comprobanteEntrega(true).paquetes(lista2).build();
//        assertNotNull(envioService.crearEnvio(envioDto2));
//        //asserts
//        assertEquals(2,envioService.listarEnvio().size());
//    }
//    @Test
//    public void testListarEnviosPorRemitente(){
//        //generamos envio 1 y lo cargamos
//        List<PaqueteDTO> lista1 = new ArrayList<>();
//        lista1.add(paqueteDTO1);
//        envioDto = EnvioDTO.builder().remitente("persona1").destinatario("persona3").direccionEntrega("Rio Blanco").
//                estadoEnvio("GENERADO").comprobanteEntrega(true).paquetes(lista1).build();
//        assertNotNull(envioService.crearEnvio(envioDto));
//        //generamos envio 2 y lo cargamos
//        List<PaqueteDTO> lista2 = new ArrayList<>();
//        lista2.add(paqueteDTO2);
//        envioDto2 = EnvioDTO.builder().remitente("persona1").destinatario("persona4").direccionEntrega("Gorriti").
//                estadoEnvio("ENTREGADO").comprobanteEntrega(false).paquetes(lista2).build();
//        envioService.crearEnvio(envioDto2);
//        assertEquals(2,envioService.listarPorRemitente("persona1").size());
//    }
//    @Test
//    public void testListarEnviosPorDestinatario(){
//        //generamos envio 1 y lo cargamos
//        List<PaqueteDTO> lista1 = new ArrayList<>();
//        lista1.add(paqueteDTO1);
//        envioDto = EnvioDTO.builder().remitente("persona1").destinatario("persona4").direccionEntrega("Rio Blanco").
//                estadoEnvio("GENERADO").comprobanteEntrega(true).paquetes(lista1).build();
//        assertNotNull(envioService.crearEnvio(envioDto));
//        //generamos envio 2 y lo cargamos
//        List<PaqueteDTO> lista2 = new ArrayList<>();
//        lista2.add(paqueteDTO2);
//        envioDto2 = EnvioDTO.builder().remitente("persona2").destinatario("persona4").direccionEntrega("Gorriti").
//                estadoEnvio("ENTREGADO").comprobanteEntrega(false).paquetes(lista2).build();
//        envioService.crearEnvio(envioDto2);
//        assertEquals(2,envioService.listarPorDestinatario("persona4").size());
//    }
//    @Test
//    public void testListarEnviosPorEstado(){
//        //generamos envio 1 y lo cargamos
//        List<PaqueteDTO> lista1 = new ArrayList<>();
//        lista1.add(paqueteDTO1);
//        envioDto = EnvioDTO.builder().remitente("persona1").destinatario("persona3").direccionEntrega("Rio Blanco").
//                estadoEnvio("GENERADO").comprobanteEntrega(true).paquetes(lista1).build();
//        assertNotNull(envioService.crearEnvio(envioDto));
//        //generamos envio 2 y lo cargamos
//        List<PaqueteDTO> lista2 = new ArrayList<>();
//        lista2.add(paqueteDTO2);
//        envioDto2 = EnvioDTO.builder().remitente("persona2").destinatario("persona4").direccionEntrega("Gorriti").
//                estadoEnvio("GENERADO").comprobanteEntrega(false).paquetes(lista2).build();
//        envioService.crearEnvio(envioDto2);
//        assertEquals(2,envioService.listarPorEstado("GENERADO").size());
//    }
}
