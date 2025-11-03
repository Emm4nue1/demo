package service;
import com.example.demo.DemoApplication;
import com.example.demo.dto.ClienteDTO;
import com.example.demo.dto.EnvioDTO;
import com.example.demo.dto.PaqueteDTO;
import com.example.demo.dto.RutaDTO;
import com.example.demo.dto.VehiculoDTO;
import com.example.demo.service.ClienteService;
import com.example.demo.service.EnvioService;
import com.example.demo.service.RutaService;
import com.example.demo.service.VehiculoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DemoApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@Slf4j
public class RutaServiceTestCase {

    @Autowired
    private RutaService rutaService;

    @Autowired
    private VehiculoService vehiculoService;

    @Autowired
    private EnvioService envioService;

    @Autowired
    private ClienteService clienteService;

    VehiculoDTO vehiculoNoRefrigerado;
    VehiculoDTO vehiculoRefrigerado;
    VehiculoDTO vehiculoPequeno;
    EnvioDTO envioConPaqueteFragil;
    EnvioDTO envioConPaqueteRefrigerado;
    ClienteDTO clienteRemitente, clienteDestinatario;

    @BeforeEach
    public void setUp() {
        // Crear clientes primero
        clienteRemitente = ClienteDTO.builder()
                .nombre("Juan")
                .apellido("Pérez")
                .dni("12345678" + System.currentTimeMillis())
                .email("remitente" + System.currentTimeMillis() + "@test.com")
                .calle("Calle Falsa")
                .numCasa(123L)
                .codPostal("4600")
                .build();

        clienteDestinatario = ClienteDTO.builder()
                .nombre("María")
                .apellido("González")
                .dni("87654321" + System.currentTimeMillis())
                .email("destinatario" + System.currentTimeMillis() + "@test.com")
                .calle("Av. Libertador")
                .numCasa(1234L)
                .codPostal("4600")
                .build();

        // Vehículos
        vehiculoNoRefrigerado = VehiculoDTO.builder()
                .patente("NOREF" + System.currentTimeMillis())
                .capPeso(1000.0)
                .capVolumen(50.0)
                .refrigerado(false)
                .build();

        vehiculoRefrigerado = VehiculoDTO.builder()
                .patente("REFRI" + System.currentTimeMillis())
                .capPeso(1500.0)
                .capVolumen(80.0)
                .refrigerado(true)
                .rangTempMin(-5.0)
                .rangTempMax(5.0)
                .build();

        vehiculoPequeno = VehiculoDTO.builder()
                .patente("PEQUE" + System.currentTimeMillis())
                .capPeso(100.0)
                .capVolumen(25.0)
                .refrigerado(false)
                .build();

        // Paquetes
        PaqueteDTO paqueteFragil = PaqueteDTO.builder()
                .codigo("PF" + System.currentTimeMillis())
                .peso(50.0)
                .volumen(20.0)
                .tipo("PF")
                .nivelFragilidad("BAJA")
                .seguroAdicional(true)
                .build();

        PaqueteDTO paqueteRefrigerado = PaqueteDTO.builder()
                .codigo("PR" + System.currentTimeMillis())
                .peso(100.0)
                .volumen(30.0)
                .tipo("PR")
                .temperaturaObjetivo(2.0)
                .rangoMinimo(0.0)
                .rangoMaximo(4.0)
                .maxHsFueraFrio(2)
                .build();

        // Los envíos se crearán en cada test después de crear los clientes
    }

    @Test
    public void testCrearRuta_Exitoso() {
        VehiculoDTO vehiculoCreado = vehiculoService.crearVehiculo(vehiculoNoRefrigerado);

        RutaDTO rutaDTO = RutaDTO.builder()
                .fecha(LocalDate.now())
                .vehiculo(vehiculoCreado)
                .build();

        RutaDTO resultado = rutaService.crearRuta(rutaDTO);

        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertEquals(LocalDate.now(), resultado.getFecha());
        assertEquals(0, resultado.getCantidadEnvios());

        log.info("Ruta creada con ID: {}", resultado.getId());
    }

    @Test
    public void testAgregarEnvioALaRuta_Exitoso() {
        // Crear clientes
        ClienteDTO remitente = clienteService.crearCliente(clienteRemitente);
        ClienteDTO destinatario = clienteService.crearCliente(clienteDestinatario);

        // Crear paquete y envío
        PaqueteDTO paquete = PaqueteDTO.builder()
                .codigo("PF_TEST" + System.currentTimeMillis())
                .peso(50.0)
                .volumen(20.0)
                .tipo("PF")
                .nivelFragilidad("BAJA")
                .seguroAdicional(true)
                .build();

        List<PaqueteDTO> paquetes = new ArrayList<>();
        paquetes.add(paquete);

        EnvioDTO envioDTO = EnvioDTO.builder()
                .remitente(remitente)
                .destinatario(destinatario)
                .direccionEntrega("Av. Libertador 1234")
                .estadoEnvio(null)
                .comprobanteEntrega(false)
                .paquetes(paquetes)
                .codPostal("4600")
                .build();

        EnvioDTO envioCreado = envioService.crearEnvio(envioDTO);

        // Crear vehículo y ruta
        VehiculoDTO vehiculoCreado = vehiculoService.crearVehiculo(vehiculoNoRefrigerado);
        RutaDTO rutaDTO = RutaDTO.builder()
                .fecha(LocalDate.now())
                .vehiculo(vehiculoCreado)
                .build();
        RutaDTO rutaCreada = rutaService.crearRuta(rutaDTO);

        // Agregar envío a la ruta
        RutaDTO rutaActualizada = rutaService.agregarEnvio(rutaCreada.getId(), envioCreado.getId());

        assertNotNull(rutaActualizada);
        assertEquals(1, rutaActualizada.getCantidadEnvios());
        assertTrue(rutaActualizada.getPesoTotalCargado() > 0);
        assertTrue(rutaActualizada.getVolumenTotalCargado() > 0);

        log.info("Envío agregado - Peso: {}, Volumen: {}",
                rutaActualizada.getPesoTotalCargado(), rutaActualizada.getVolumenTotalCargado());
    }

    @Test
    public void testExcederCapacidadPeso_Error() {
        ClienteDTO remitente = clienteService.crearCliente(clienteRemitente);
        ClienteDTO destinatario = clienteService.crearCliente(clienteDestinatario);

        VehiculoDTO vehiculoCreado = vehiculoService.crearVehiculo(vehiculoPequeno);
        RutaDTO rutaDTO = RutaDTO.builder()
                .fecha(LocalDate.now())
                .vehiculo(vehiculoCreado)
                .build();
        RutaDTO rutaCreada = rutaService.crearRuta(rutaDTO);

        // Primer envío (50kg)
        PaqueteDTO paquete1 = PaqueteDTO.builder()
                .codigo("P1_" + System.currentTimeMillis())
                .peso(50.0)
                .volumen(10.0)
                .tipo("PF")
                .nivelFragilidad("BAJA")
                .seguroAdicional(false)
                .build();

        List<PaqueteDTO> paquetes1 = new ArrayList<>();
        paquetes1.add(paquete1);

        EnvioDTO envio1 = EnvioDTO.builder()
                .remitente(remitente)
                .destinatario(destinatario)
                .direccionEntrega("Dirección 1")
                .estadoEnvio(null)
                .comprobanteEntrega(false)
                .paquetes(paquetes1)
                .codPostal("4600")
                .build();

        EnvioDTO envio1Creado = envioService.crearEnvio(envio1);
        rutaService.agregarEnvio(rutaCreada.getId(), envio1Creado.getId());

        // Segundo envío (60kg) - Debe fallar
        PaqueteDTO paquete2 = PaqueteDTO.builder()
                .codigo("P2_" + System.currentTimeMillis())
                .peso(60.0)
                .volumen(10.0)
                .tipo("PF")
                .nivelFragilidad("BAJA")
                .seguroAdicional(false)
                .build();

        List<PaqueteDTO> paquetes2 = new ArrayList<>();
        paquetes2.add(paquete2);

        EnvioDTO envio2 = EnvioDTO.builder()
                .remitente(remitente)
                .destinatario(destinatario)
                .direccionEntrega("Dirección 2")
                .estadoEnvio(null)
                .comprobanteEntrega(false)
                .paquetes(paquetes2)
                .codPostal("4600")
                .build();

        EnvioDTO envio2Creado = envioService.crearEnvio(envio2);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> rutaService.agregarEnvio(rutaCreada.getId(), envio2Creado.getId())
        );

        assertTrue(exception.getMessage().contains("No hay suficiente capacidad de peso"));
        log.info("Error capturado correctamente: {}", exception.getMessage());
    }

    @Test
    public void testAgregarPaqueteRefrigeradoAVehiculoNoRefrigerado_Error() {
        ClienteDTO remitente = clienteService.crearCliente(clienteRemitente);
        ClienteDTO destinatario = clienteService.crearCliente(clienteDestinatario);

        VehiculoDTO vehiculo = vehiculoService.crearVehiculo(vehiculoNoRefrigerado);
        RutaDTO ruta = RutaDTO.builder()
                .fecha(LocalDate.now())
                .vehiculo(vehiculo)
                .build();
        RutaDTO rutaCreada = rutaService.crearRuta(ruta);

        // Crear envío con paquete refrigerado
        PaqueteDTO paqueteRefrig = PaqueteDTO.builder()
                .codigo("PR_" + System.currentTimeMillis())
                .peso(100.0)
                .volumen(30.0)
                .tipo("PR")
                .temperaturaObjetivo(2.0)
                .rangoMinimo(0.0)
                .rangoMaximo(4.0)
                .maxHsFueraFrio(2)
                .build();

        List<PaqueteDTO> paquetes = new ArrayList<>();
        paquetes.add(paqueteRefrig);

        EnvioDTO envioRefrigerado = EnvioDTO.builder()
                .remitente(remitente)
                .destinatario(destinatario)
                .direccionEntrega("Calle Belgrano 567")
                .estadoEnvio(null)
                .comprobanteEntrega(false)
                .paquetes(paquetes)
                .codPostal("4600")
                .build();

        EnvioDTO envioCreado = envioService.crearEnvio(envioRefrigerado);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> rutaService.agregarEnvio(rutaCreada.getId(), envioCreado.getId())
        );

        assertTrue(exception.getMessage().contains("no es refrigerado"));
        log.info("Error capturado: {}", exception.getMessage());
    }

    @Test
    public void testValidacionTemperaturaIncompatible_Error() {
        ClienteDTO remitente = clienteService.crearCliente(clienteRemitente);
        ClienteDTO destinatario = clienteService.crearCliente(clienteDestinatario);

        // Vehículo refrigerado con rango [-5°C a 5°C]
        VehiculoDTO vehiculo = vehiculoService.crearVehiculo(vehiculoRefrigerado);
        RutaDTO ruta = RutaDTO.builder()
                .fecha(LocalDate.now())
                .vehiculo(vehiculo)
                .build();
        RutaDTO rutaCreada = rutaService.crearRuta(ruta);

        // Paquete que requiere [-15°C a -10°C] - INCOMPATIBLE
        PaqueteDTO paqueteIncompatible = PaqueteDTO.builder()
                .codigo("PR_INCOMP_" + System.currentTimeMillis())
                .peso(50.0)
                .volumen(20.0)
                .tipo("PR")
                .temperaturaObjetivo(-12.0)
                .rangoMinimo(-15.0)  // Fuera del rango del vehículo
                .rangoMaximo(-10.0)
                .maxHsFueraFrio(1)
                .build();

        List<PaqueteDTO> paquetes = new ArrayList<>();
        paquetes.add(paqueteIncompatible);

        EnvioDTO envio = EnvioDTO.builder()
                .remitente(remitente)
                .destinatario(destinatario)
                .direccionEntrega("Test Temperatura")
                .estadoEnvio(null)
                .comprobanteEntrega(false)
                .paquetes(paquetes)
                .codPostal("4600")
                .build();

        EnvioDTO envioCreado = envioService.crearEnvio(envio);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> rutaService.agregarEnvio(rutaCreada.getId(), envioCreado.getId())
        );

        assertTrue(exception.getMessage().contains("Temperatura incompatible"));
        log.info("Validación de temperatura funcionando: {}", exception.getMessage());
    }

    @Test
    public void testValidacionTemperaturaCompatible_Exitoso() {
        ClienteDTO remitente = clienteService.crearCliente(clienteRemitente);
        ClienteDTO destinatario = clienteService.crearCliente(clienteDestinatario);

        // Vehículo refrigerado con rango [-5°C a 5°C]
        VehiculoDTO vehiculo = vehiculoService.crearVehiculo(vehiculoRefrigerado);
        RutaDTO ruta = RutaDTO.builder()
                .fecha(LocalDate.now())
                .vehiculo(vehiculo)
                .build();
        RutaDTO rutaCreada = rutaService.crearRuta(ruta);

        // Paquete que requiere [0°C a 4°C] - COMPATIBLE
        PaqueteDTO paqueteCompatible = PaqueteDTO.builder()
                .codigo("PR_COMP_" + System.currentTimeMillis())
                .peso(50.0)
                .volumen(20.0)
                .tipo("PR")
                .temperaturaObjetivo(2.0)
                .rangoMinimo(0.0)   // Dentro del rango del vehículo
                .rangoMaximo(4.0)    // Dentro del rango del vehículo
                .maxHsFueraFrio(2)
                .build();

        List<PaqueteDTO> paquetes = new ArrayList<>();
        paquetes.add(paqueteCompatible);

        EnvioDTO envio = EnvioDTO.builder()
                .remitente(remitente)
                .destinatario(destinatario)
                .direccionEntrega("Test Temperatura Compatible")
                .estadoEnvio(null)
                .comprobanteEntrega(false)
                .paquetes(paquetes)
                .codPostal("4600")
                .build();

        EnvioDTO envioCreado = envioService.crearEnvio(envio);

        // No debe lanzar excepción
        assertDoesNotThrow(() -> {
            RutaDTO rutaActualizada = rutaService.agregarEnvio(rutaCreada.getId(), envioCreado.getId());
            assertNotNull(rutaActualizada);
            assertEquals(1, rutaActualizada.getCantidadEnvios());
        });

        log.info("Paquete refrigerado compatible agregado correctamente");
    }

    @Test
    public void testListarRutas() {
        VehiculoDTO vehiculo1 = vehiculoService.crearVehiculo(vehiculoNoRefrigerado);
        VehiculoDTO vehiculo2 = vehiculoService.crearVehiculo(vehiculoRefrigerado);

        RutaDTO ruta1 = RutaDTO.builder()
                .fecha(LocalDate.now())
                .vehiculo(vehiculo1)
                .build();
        rutaService.crearRuta(ruta1);

        RutaDTO ruta2 = RutaDTO.builder()
                .fecha(LocalDate.now().plusDays(1))
                .vehiculo(vehiculo2)
                .build();
        rutaService.crearRuta(ruta2);

        List<RutaDTO> rutas = rutaService.listarRutas();

        assertNotNull(rutas);
        assertTrue(rutas.size() >= 2);
        log.info("Total rutas: {}", rutas.size());
    }

    @Test
    public void testCrearRutaConVehiculoYaAsignadoMismaFecha_Error() {
        VehiculoDTO vehiculo = vehiculoService.crearVehiculo(vehiculoNoRefrigerado);
        LocalDate fecha = LocalDate.now();

        RutaDTO ruta1 = RutaDTO.builder()
                .fecha(fecha)
                .vehiculo(vehiculo)
                .build();
        rutaService.crearRuta(ruta1);

        RutaDTO ruta2 = RutaDTO.builder()
                .fecha(fecha)
                .vehiculo(vehiculo)
                .build();

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> rutaService.crearRuta(ruta2)
        );

        assertTrue(exception.getMessage().contains("ya tiene una ruta asignada"));
        log.info("Error capturado: {}", exception.getMessage());
    }
}