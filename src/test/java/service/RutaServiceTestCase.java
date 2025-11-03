//package service;
//import com.example.demo.DemoApplication;
//import com.example.demo.dto.EnvioDTO;
//import com.example.demo.dto.PaqueteDTO;
//import com.example.demo.dto.RutaDTO;
//import com.example.demo.dto.VehiculoDTO;
//import com.example.demo.service.EnvioService;
//import com.example.demo.service.RutaService;
//import com.example.demo.service.VehiculoService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest(classes = DemoApplication.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@Transactional
//public class RutaServiceTestCase {
//
//    @Autowired
//    private RutaService rutaService;
//
//    @Autowired
//    private VehiculoService vehiculoService;
//
//    @Autowired
//    private EnvioService envioService;
//
//    VehiculoDTO vehiculoNoRefrigerado;
//    VehiculoDTO vehiculoRefrigerado;
//    VehiculoDTO vehiculoPequeno;
//    EnvioDTO envioConPaqueteFragil;
//    EnvioDTO envioConPaqueteRefrigerado;
//
//    @BeforeEach
//    public void setUp() {
//        vehiculoNoRefrigerado = VehiculoDTO.builder()
//                .patente("NOREF" + System.currentTimeMillis())
//                .capPeso(1000.0)
//                .capVolumen(50.0)
//                .refrigerado(false)
//                .build();
//
//        vehiculoRefrigerado = VehiculoDTO.builder()
//                .patente("REFRI" + System.currentTimeMillis())
//                .capPeso(1500.0)
//                .capVolumen(80.0)
//                .refrigerado(true)
//                .rangTempMin(-5.0)
//                .rangTempMax(5.0)
//                .build();
//
//        vehiculoPequeno = VehiculoDTO.builder()
//                .patente("PEQUE" + System.currentTimeMillis())
//                .capPeso(100.0)
//                .capVolumen(25.0)
//                .refrigerado(false)
//                .build();
//
//        PaqueteDTO paqueteFragil = PaqueteDTO.builder()
//                .codigo("PF" + System.currentTimeMillis())
//                .peso(50.0)
//                .volumen(20.0)
//                .tipo("PF")
//                .nivelFragilidad("BAJA")
//                .seguroAdicional(true)
//                .build();
//
//        List<PaqueteDTO> paquetesFragiles = new ArrayList<>();
//        paquetesFragiles.add(paqueteFragil);
//
//        envioConPaqueteFragil = EnvioDTO.builder()
//                .remitente("Juan Pérez")
//                .destinatario("María González")
//                .direccionEntrega("Av. Libertador 1234")
//                .estadoEnvio("GENERADO")
//                .comprobanteEntrega(false)
//                .paquetes(paquetesFragiles)
//                .build();
//
//        PaqueteDTO paqueteRefrigerado = PaqueteDTO.builder()
//                .codigo("PR" + System.currentTimeMillis())
//                .peso(100.0)
//                .volumen(30.0)
//                .tipo("PR")
//                .temperaturaObjetivo(2.0)
//                .rangoMinimo(0.0)
//                .rangoMaximo(4.0)
//                .maxHsFueraFrio(2)
//                .build();
//
//        List<PaqueteDTO> paquetesRefrigerados = new ArrayList<>();
//        paquetesRefrigerados.add(paqueteRefrigerado);
//
//        envioConPaqueteRefrigerado = EnvioDTO.builder()
//                .remitente("Carlos López")
//                .destinatario("Ana Martínez")
//                .direccionEntrega("Calle Belgrano 567")
//                .estadoEnvio("GENERADO")
//                .comprobanteEntrega(false)
//                .paquetes(paquetesRefrigerados)
//                .build();
//    }
//
//    // TEST 1: Crear una ruta vacía
//    @Test
//    public void testCrearRuta_Exitoso() {
//        VehiculoDTO vehiculoCreado = vehiculoService.crearVehiculo(vehiculoNoRefrigerado);
//
//        RutaDTO rutaDTO = RutaDTO.builder()
//                .fecha(LocalDate.now())
//                .vehiculo(vehiculoCreado)
//                .build();
//        RutaDTO resultado = rutaService.crearRuta(rutaDTO);
//
//        assertNotNull(resultado);
//        assertNotNull(resultado.getId());
//        assertEquals(LocalDate.now(), resultado.getFecha());
//        assertEquals(0, resultado.getCantidadEnvios());
//    }
//
//    // TEST 2: Agregar envío a una ruta
//    @Test
//    public void testAgregarEnvioALaRuta_Exitoso() {
//        VehiculoDTO vehiculoCreado = vehiculoService.crearVehiculo(vehiculoNoRefrigerado);
//
//        RutaDTO rutaDTO = RutaDTO.builder()
//                .fecha(LocalDate.now())
//                .vehiculo(vehiculoCreado)
//                .build();
//        RutaDTO rutaCreada = rutaService.crearRuta(rutaDTO);
//
//        EnvioDTO envioCreado = envioService.crearEnvio(envioConPaqueteFragil);
//
//        RutaDTO rutaActualizada = rutaService.agregarEnvio(rutaCreada.getId(), envioCreado.getId());
//
//        assertNotNull(rutaActualizada);
//        assertEquals(1, rutaActualizada.getCantidadEnvios());
//        assertTrue(rutaActualizada.getPesoTotalCargado() > 0);
//        assertTrue(rutaActualizada.getVolumenTotalCargado() > 0);
//    }
//
//    // TEST 4: Listar todas las rutas
//    @Test
//    public void testListarTodasLasRutas_Exitoso() {
//        VehiculoDTO vehiculo1 = vehiculoService.crearVehiculo(vehiculoNoRefrigerado);
//        VehiculoDTO vehiculo2 = vehiculoService.crearVehiculo(vehiculoRefrigerado);
//
//        RutaDTO ruta1 = RutaDTO.builder()
//                .fecha(LocalDate.now())
//                .vehiculo(vehiculo1)
//                .build();
//        rutaService.crearRuta(ruta1);
//
//        RutaDTO ruta2 = RutaDTO.builder()
//                .fecha(LocalDate.now().plusDays(1))
//                .vehiculo(vehiculo2)
//                .build();
//        rutaService.crearRuta(ruta2);
//
//        List<RutaDTO> rutas = rutaService.listarRutas();
//
//        assertNotNull(rutas);
//        assertTrue(rutas.size() >= 2);
//    }
//
//    // TEST 5: Consultar envíos por ruta y fecha (PEDIDO EN TP)
//    @Test
//    public void testConsultarEnviosPorRutaYFecha() {
//        VehiculoDTO vehiculo = vehiculoService.crearVehiculo(vehiculoNoRefrigerado);
//
//        LocalDate fechaHoy = LocalDate.now();
//        RutaDTO ruta = RutaDTO.builder()
//                .fecha(fechaHoy)
//                .vehiculo(vehiculo)
//                .build();
//        RutaDTO rutaCreada = rutaService.crearRuta(ruta);
//
//        EnvioDTO envio = envioService.crearEnvio(envioConPaqueteFragil);
//        rutaService.agregarEnvio(rutaCreada.getId(), envio.getId());
//
//        List<RutaDTO> rutasPorFecha = rutaService.listarPorFecha(fechaHoy);
//
//        assertNotNull(rutasPorFecha);
//        assertFalse(rutasPorFecha.isEmpty());
//        assertEquals(fechaHoy, rutasPorFecha.get(0).getFecha());
//        assertTrue(rutasPorFecha.get(0).getCantidadEnvios() >= 1);
//    }
//
//    // TEST 6: Listar rutas por rango de fechas
//    @Test
//    public void testListarRutasPorRangoFechas() {
//        VehiculoDTO vehiculo = vehiculoService.crearVehiculo(vehiculoNoRefrigerado);
//
//        LocalDate hoy = LocalDate.now();
//        LocalDate manana = hoy.plusDays(1);
//
//        RutaDTO ruta1 = RutaDTO.builder()
//                .fecha(hoy)
//                .vehiculo(vehiculo)
//                .build();
//        rutaService.crearRuta(ruta1);
//
//        List<RutaDTO> rutasEnRango = rutaService.listarPorRangoFechas(hoy, manana);
//
//        assertNotNull(rutasEnRango);
//        assertFalse(rutasEnRango.isEmpty());
//    }
//
//    // TEST 7: Listar rutas que tienen envíos
//    @Test
//    public void testListarRutasConEnvios() {
//        VehiculoDTO vehiculo = vehiculoService.crearVehiculo(vehiculoNoRefrigerado);
//
//        RutaDTO rutaSinEnvios = RutaDTO.builder()
//                .fecha(LocalDate.now())
//                .vehiculo(vehiculo)
//                .build();
//        rutaService.crearRuta(rutaSinEnvios);
//
//        VehiculoDTO vehiculo2 = vehiculoService.crearVehiculo(vehiculoRefrigerado);
//        RutaDTO rutaConEnvios = RutaDTO.builder()
//                .fecha(LocalDate.now().plusDays(1))
//                .vehiculo(vehiculo2)
//                .build();
//        RutaDTO rutaCreada = rutaService.crearRuta(rutaConEnvios);
//
//        EnvioDTO envio = envioService.crearEnvio(envioConPaqueteFragil);
//        rutaService.agregarEnvio(rutaCreada.getId(), envio.getId());
//
//        List<RutaDTO> rutasConEnvios = rutaService.listarRutasConEnvios();
//
//        assertNotNull(rutasConEnvios);
//        assertFalse(rutasConEnvios.isEmpty());
//        assertTrue(rutasConEnvios.get(0).getCantidadEnvios() > 0);
//    }
//
//    // TEST 8: Error al exceder capacidad de peso
//    @Test
//    public void testExcederCapacidadPeso_Error() {
//        VehiculoDTO vehiculoCreado = vehiculoService.crearVehiculo(vehiculoPequeno);
//
//        RutaDTO rutaDTO = RutaDTO.builder()
//                .fecha(LocalDate.now())
//                .vehiculo(vehiculoCreado)
//                .build();
//        RutaDTO rutaCreada = rutaService.crearRuta(rutaDTO);
//
//        EnvioDTO envio1 = envioService.crearEnvio(envioConPaqueteFragil);
//        rutaService.agregarEnvio(rutaCreada.getId(), envio1.getId());
//
//        PaqueteDTO paquetePesado = PaqueteDTO.builder()
//                .codigo("PESADO" + System.currentTimeMillis())
//                .peso(60.0)
//                .volumen(10.0)
//                .tipo("PF")
//                .nivelFragilidad("BAJA")
//                .seguroAdicional(false)
//                .build();
//
//        List<PaqueteDTO> paquetes = new ArrayList<>();
//        paquetes.add(paquetePesado);
//
//        EnvioDTO envio2 = EnvioDTO.builder()
//                .remitente("Otro remitente")
//                .destinatario("Otro destinatario")
//                .direccionEntrega("Otra dirección")
//                .estadoEnvio("GENERADO")
//                .comprobanteEntrega(false)
//                .paquetes(paquetes)
//                .build();
//
//        EnvioDTO envio2Creado = envioService.crearEnvio(envio2);
//
//        IllegalStateException exception = assertThrows(
//                IllegalStateException.class,
//                () -> rutaService.agregarEnvio(rutaCreada.getId(), envio2Creado.getId())
//        );
//
//        assertTrue(exception.getMessage().contains("No hay suficiente capacidad de peso"));
//    }
//
//    // NUEVO: Error al agregar paquete refrigerado a vehículo no refrigerado
//    @Test
//    public void testAgregarPaqueteRefrigeradoAVehiculoNoRefrigerado_Error() {
//        VehiculoDTO vehiculo = vehiculoService.crearVehiculo(vehiculoNoRefrigerado);
//
//        RutaDTO ruta = RutaDTO.builder()
//                .fecha(LocalDate.now())
//                .vehiculo(vehiculo)
//                .build();
//        RutaDTO rutaCreada = rutaService.crearRuta(ruta);
//
//        EnvioDTO envioRefrigerado = envioService.crearEnvio(envioConPaqueteRefrigerado);
//
//        IllegalStateException exception = assertThrows(
//                IllegalStateException.class,
//                () -> rutaService.agregarEnvio(rutaCreada.getId(), envioRefrigerado.getId())
//        );
//
//        assertTrue(exception.getMessage().contains("no es refrigerado"));
//    }
//
//    // NUEVO: Error al crear ruta con vehículo ya asignado en la misma fecha
//    @Test
//    public void testCrearRutaConVehiculoYaAsignadoMismaFecha_Error() {
//        VehiculoDTO vehiculo = vehiculoService.crearVehiculo(vehiculoNoRefrigerado);
//        LocalDate fecha = LocalDate.now();
//
//        RutaDTO ruta1 = RutaDTO.builder()
//                .fecha(fecha)
//                .vehiculo(vehiculo)
//                .build();
//        rutaService.crearRuta(ruta1);
//
//        RutaDTO ruta2 = RutaDTO.builder()
//                .fecha(fecha)
//                .vehiculo(vehiculo)
//                .build();
//
//        IllegalStateException exception = assertThrows(
//                IllegalStateException.class,
//                () -> rutaService.crearRuta(ruta2)
//        );
//
//        assertTrue(exception.getMessage().contains("ya tiene una ruta asignada"));
//    }
//}