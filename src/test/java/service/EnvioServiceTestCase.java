package service;
import com.example.demo.DemoApplication;
import com.example.demo.dto.ClienteDTO;
import com.example.demo.service.ClienteService;
import com.example.demo.service.EnvioService;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DemoApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@Slf4j
public class EnvioServiceTestCase {

    @Autowired
    private EnvioService envioService;

    @Autowired
    private ClienteService clienteService;

    PaqueteDTO paqueteDTO1, paqueteDTO2, paqueteRefrigerado;
    EnvioDTO envioDto, envioDto2;
    ClienteDTO clienteDTO1, clienteDTO2;

    @BeforeEach
    public void setUp() {
        // Paquetes frágiles con códigos únicos
        paqueteDTO1 = PaqueteDTO.builder()
                .codigo("H123" + System.currentTimeMillis())
                .peso(50.0)
                .volumen(30.0)
                .nivelFragilidad("BAJA")
                .seguroAdicional(true)
                .tipo("PF")
                .build();

        paqueteDTO2 = PaqueteDTO.builder()
                .codigo("GG123" + System.currentTimeMillis())
                .peso(50.0)
                .volumen(30.0)
                .nivelFragilidad("ALTA")
                .seguroAdicional(false)
                .tipo("PF")
                .build();

        // Paquete refrigerado para probar requiereFrio
        paqueteRefrigerado = PaqueteDTO.builder()
                .codigo("REF123" + System.currentTimeMillis())
                .peso(40.0)
                .volumen(25.0)
                .tipo("PR")
                .temperaturaObjetivo(-5.0)
                .rangoMinimo(-10.0)
                .rangoMaximo(0.0)
                .maxHsFueraFrio(2)
                .build();

        // Clientes con DNI y email únicos
        clienteDTO1 = ClienteDTO.builder()
                .nombre("Juan")
                .apellido("Perez")
                .dni("45492312" + System.currentTimeMillis())
                .email("persona1" + System.currentTimeMillis() + "@gmail.com")
                .calle("Calle Falsa")
                .numCasa(123L)
                .codPostal("4116")
                .build();

        clienteDTO2 = ClienteDTO.builder()
                .nombre("Maria")
                .apellido("Gomez")
                .dni("46523942" + System.currentTimeMillis())
                .email("persona2" + System.currentTimeMillis() + "@gmail.com")
                .calle("Av. Siempre Viva")
                .numCasa(456L)
                .codPostal("9431")
                .build();
    }

    @Test
    public void testCrearEnvio() {
        // Crear clientes primero
        ClienteDTO clienteAux1 = clienteService.crearCliente(clienteDTO1);
        ClienteDTO clienteAux2 = clienteService.crearCliente(clienteDTO2);

        // Crear envío con paquete frágil
        List<PaqueteDTO> lista1 = new ArrayList<>();
        lista1.add(paqueteDTO1);

        envioDto = EnvioDTO.builder()
                .remitente(clienteAux1)
                .destinatario(clienteAux2)
                .direccionEntrega("Rio Blanco 123")
                .estadoEnvio(null)  // Se asigna automáticamente
                .comprobanteEntrega(false)
                .paquetes(lista1)
                .codPostal("4600")
                .build();

        EnvioDTO envioCreado = envioService.crearEnvio(envioDto);

        assertNotNull(envioCreado);
        assertNotNull(envioCreado.getId());
        assertEquals("GENERADO", envioCreado.getEstadoEnvio());
        assertNotNull(envioCreado.getIdentificadorUnico());
        assertFalse(envioCreado.getRequiereFrio()); // No requiere frío (paquete frágil)

        log.info("Envío creado con ID: {} e Identificador: {}",
                envioCreado.getId(), envioCreado.getIdentificadorUnico());
    }

    @Test
    public void testCrearEnvioConPaqueteRefrigerado() {
        ClienteDTO clienteAux1 = clienteService.crearCliente(clienteDTO1);
        ClienteDTO clienteAux2 = clienteService.crearCliente(clienteDTO2);

        List<PaqueteDTO> listaPaquetes = new ArrayList<>();
        listaPaquetes.add(paqueteRefrigerado);

        envioDto = EnvioDTO.builder()
                .remitente(clienteAux1)
                .destinatario(clienteAux2)
                .direccionEntrega("Av. Libertador 456")
                .estadoEnvio(null)
                .comprobanteEntrega(false)
                .paquetes(listaPaquetes)
                .codPostal("4600")
                .build();

        EnvioDTO envioCreado = envioService.crearEnvio(envioDto);

        assertNotNull(envioCreado);
        assertNotNull(envioCreado.getIdentificadorUnico());
        assertTrue(envioCreado.getRequiereFrio()); // Debe ser true
        assertEquals("GENERADO", envioCreado.getEstadoEnvio());

        log.info("Envío refrigerado - Requiere Frío: {}", envioCreado.getRequiereFrio());
    }

    @Test
    public void testIdentificadorUnicoEsUnico() {
        ClienteDTO clienteAux1 = clienteService.crearCliente(clienteDTO1);
        ClienteDTO clienteAux2 = clienteService.crearCliente(clienteDTO2);

        List<PaqueteDTO> lista1 = new ArrayList<>();
        lista1.add(paqueteDTO1);

        envioDto = EnvioDTO.builder()
                .remitente(clienteAux1)
                .destinatario(clienteAux2)
                .direccionEntrega("Calle 1")
                .estadoEnvio(null)
                .comprobanteEntrega(false)
                .paquetes(lista1)
                .codPostal("4600")
                .build();

        EnvioDTO envio1 = envioService.crearEnvio(envioDto);

        List<PaqueteDTO> lista2 = new ArrayList<>();
        lista2.add(paqueteDTO2);

        envioDto2 = EnvioDTO.builder()
                .remitente(clienteAux2)
                .destinatario(clienteAux1)
                .direccionEntrega("Calle 2")
                .estadoEnvio(null)
                .comprobanteEntrega(false)
                .paquetes(lista2)
                .codPostal("4600")
                .build();

        EnvioDTO envio2 = envioService.crearEnvio(envioDto2);

        // Los identificadores deben ser diferentes
        assertNotEquals(envio1.getIdentificadorUnico(), envio2.getIdentificadorUnico());

        log.info(" ID1: {} - ID2: {}",
                envio1.getIdentificadorUnico(), envio2.getIdentificadorUnico());
    }

    @Test
    public void testListarEnvios() {
        ClienteDTO clienteAux1 = clienteService.crearCliente(clienteDTO1);
        ClienteDTO clienteAux2 = clienteService.crearCliente(clienteDTO2);

        List<PaqueteDTO> lista1 = new ArrayList<>();
        lista1.add(paqueteDTO1);

        envioDto = EnvioDTO.builder()
                .remitente(clienteAux1)
                .destinatario(clienteAux2)
                .direccionEntrega("Rio Blanco")
                .estadoEnvio(null)
                .comprobanteEntrega(false)
                .paquetes(lista1)
                .codPostal("4600")
                .build();

        envioService.crearEnvio(envioDto);

        List<PaqueteDTO> lista2 = new ArrayList<>();
        lista2.add(paqueteDTO2);

        envioDto2 = EnvioDTO.builder()
                .remitente(clienteAux2)
                .destinatario(clienteAux1)
                .direccionEntrega("Gorriti")
                .estadoEnvio(null)
                .comprobanteEntrega(false)
                .paquetes(lista2)
                .codPostal("9431")
                .build();

        envioService.crearEnvio(envioDto2);

        List<EnvioDTO> envios = envioService.listarEnvio();
        assertTrue(envios.size() >= 2);

        log.info("Total envíos listados: {}", envios.size());
    }

    @Test
    public void testCancelarEnvio() {
        ClienteDTO clienteAux1 = clienteService.crearCliente(clienteDTO1);
        ClienteDTO clienteAux2 = clienteService.crearCliente(clienteDTO2);

        List<PaqueteDTO> lista1 = new ArrayList<>();
        lista1.add(paqueteDTO1);

        envioDto = EnvioDTO.builder()
                .remitente(clienteAux1)
                .destinatario(clienteAux2)
                .direccionEntrega("Test Cancelar")
                .estadoEnvio(null)
                .comprobanteEntrega(false)
                .paquetes(lista1)
                .codPostal("4600")
                .build();

        EnvioDTO envioCreado = envioService.crearEnvio(envioDto);
        assertEquals("GENERADO", envioCreado.getEstadoEnvio());

        EnvioDTO envioCancelado = envioService.cancelarEnvio(envioCreado);
        assertEquals("CANCELADO", envioCancelado.getEstadoEnvio());

        log.info("Envío cancelado correctamente");
    }

    @Test
    public void testListarPorRemitente() {
        ClienteDTO clienteAux1 = clienteService.crearCliente(clienteDTO1);
        ClienteDTO clienteAux2 = clienteService.crearCliente(clienteDTO2);

        List<PaqueteDTO> lista1 = new ArrayList<>();
        lista1.add(paqueteDTO1);

        envioDto = EnvioDTO.builder()
                .remitente(clienteAux1)
                .destinatario(clienteAux2)
                .direccionEntrega("Calle 1")
                .estadoEnvio(null)
                .comprobanteEntrega(false)
                .paquetes(lista1)
                .codPostal("4600")
                .build();

        envioService.crearEnvio(envioDto);

        List<EnvioDTO> envios = envioService.listarPorRemitente(clienteAux1);
        assertTrue(envios.size() >= 1);

        log.info("Envíos del remitente: {}", envios.size());
    }

    @Test
    public void testListarPorDestinatario() {
        ClienteDTO clienteAux1 = clienteService.crearCliente(clienteDTO1);
        ClienteDTO clienteAux2 = clienteService.crearCliente(clienteDTO2);

        List<PaqueteDTO> lista1 = new ArrayList<>();
        lista1.add(paqueteDTO1);

        envioDto = EnvioDTO.builder()
                .remitente(clienteAux1)
                .destinatario(clienteAux2)
                .direccionEntrega("Calle 1")
                .estadoEnvio(null)
                .comprobanteEntrega(false)
                .paquetes(lista1)
                .codPostal("4600")
                .build();

        envioService.crearEnvio(envioDto);

        List<EnvioDTO> envios = envioService.listarPorDestinatario(clienteAux2);
        assertTrue(envios.size() >= 1);

        log.info("Envíos al destinatario: {}", envios.size());
    }
}