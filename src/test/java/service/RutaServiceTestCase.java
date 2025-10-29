package service;

import com.example.demo.DemoApplication;
import com.example.demo.dto.EnvioDTO;
import com.example.demo.dto.PaqueteDTO;
import com.example.demo.dto.RutaDTO;
import com.example.demo.dto.VehiculoDTO;
import com.example.demo.service.EnvioService;
import com.example.demo.service.RutaService;
import com.example.demo.service.VehiculoService;
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
@Transactional  // Cada test hace rollback automático
public class RutaServiceTestCase {

    @Autowired
    private RutaService rutaService;

    @Autowired
    private VehiculoService vehiculoService;

    @Autowired
    private EnvioService envioService;

    VehiculoDTO vehiculoNoRefrigerado;
    VehiculoDTO vehiculoRefrigerado;
    VehiculoDTO vehiculoPequeno;
    EnvioDTO envioConPaqueteFragil;
    EnvioDTO envioConPaqueteRefrigerado;

    @BeforeEach
    public void setUp() {
        // Preparar vehículo NO refrigerado (capacidad grande)
        vehiculoNoRefrigerado = VehiculoDTO.builder()
                .patente("NOREF" + System.currentTimeMillis())
                .capPeso(1000.0)
                .capVolumen(50.0)
                .refrigerado(false)
                .build();

        // Preparar vehículo refrigerado (capacidad grande)
        vehiculoRefrigerado = VehiculoDTO.builder()
                .patente("REFRI" + System.currentTimeMillis())
                .capPeso(1500.0)
                .capVolumen(80.0)
                .refrigerado(true)
                .rangTempMin(-5.0)
                .rangTempMax(5.0)
                .build();

        // Preparar vehículo PEQUEÑO (para probar exceso de capacidad)
        vehiculoPequeno = VehiculoDTO.builder()
                .patente("PEQUE" + System.currentTimeMillis())
                .capPeso(100.0)   // Solo 100kg
                .capVolumen(25.0)  // Solo 25dm³
                .refrigerado(false)
                .build();

        // Preparar envío con paquete FRÁGIL (50kg, 20dm³)
        PaqueteDTO paqueteFragil = PaqueteDTO.builder()
                .codigo("PF" + System.currentTimeMillis())
                .peso(50.0)
                .volumen(20.0)
                .tipo("PF")
                .nivelFragilidad("BAJA")
                .seguroAdicional(true)
                .build();

        List<PaqueteDTO> paquetesFragiles = new ArrayList<>();
        paquetesFragiles.add(paqueteFragil);

        envioConPaqueteFragil = EnvioDTO.builder()
                .remitente("Juan Pérez")
                .destinatario("María González")
                .direccionEntrega("Av. Libertador 1234")
                .estadoEnvio("GENERADO")
                .comprobanteEntrega(false)
                .paquetes(paquetesFragiles)
                .build();

        // Preparar envío con paquete REFRIGERADO (100kg, 30dm³)
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

        List<PaqueteDTO> paquetesRefrigerados = new ArrayList<>();
        paquetesRefrigerados.add(paqueteRefrigerado);

        envioConPaqueteRefrigerado = EnvioDTO.builder()
                .remitente("Carlos López")
                .destinatario("Ana Martínez")
                .direccionEntrega("Calle Belgrano 567")
                .estadoEnvio("GENERADO")
                .comprobanteEntrega(false)
                .paquetes(paquetesRefrigerados)
                .build();
    }

    // ========================================
    // TEST 1: Crear una ruta correctamente ✅
    // ========================================
    /**
     * OBJETIVO: Verificar que se puede crear una ruta vacía (sin envíos)
     * RESULTADO ESPERADO: La ruta se crea con un ID asignado y 0 envíos
     */
    @Test
    public void testCrearRuta_Exitoso() {
        // Preparar: Crear el vehículo primero
        VehiculoDTO vehiculoCreado = vehiculoService.crearVehiculo(vehiculoNoRefrigerado);

        // Ejecutar: Crear la ruta con el vehículo
        RutaDTO rutaDTO = RutaDTO.builder()
                .fecha(LocalDate.now())
                .vehiculo(vehiculoCreado)
                .build();
        RutaDTO resultado = rutaService.crearRuta(rutaDTO);

        // Verificar: Que se creó correctamente
        assertNotNull(resultado, "La ruta creada no debe ser null");
        assertNotNull(resultado.getId(), "Debe tener un ID asignado");
        assertEquals(LocalDate.now(), resultado.getFecha());
        assertEquals(vehiculoCreado.getId(), resultado.getVehiculo().getId());
        assertEquals(0, resultado.getCantidadEnvios(), "Debe tener 0 envíos al crearla");
    }

    // ========================================
    // TEST 2: Agregar un envío a la ruta correctamente ✅
    // ========================================
    /**
     * OBJETIVO: Verificar que se puede agregar un envío frágil a una ruta
     * RESULTADO ESPERADO: El envío se agrega y la ruta actualiza su información de carga
     */
    @Test
    public void testAgregarEnvioALaRuta_Exitoso() {
        // Preparar: Crear vehículo, ruta y envío
        VehiculoDTO vehiculoCreado = vehiculoService.crearVehiculo(vehiculoNoRefrigerado);

        RutaDTO rutaDTO = RutaDTO.builder()
                .fecha(LocalDate.now())
                .vehiculo(vehiculoCreado)
                .build();
        RutaDTO rutaCreada = rutaService.crearRuta(rutaDTO);

        EnvioDTO envioCreado = envioService.crearEnvio(envioConPaqueteFragil);

        // Ejecutar: Agregar el envío a la ruta
        RutaDTO rutaActualizada = rutaService.agregarEnvio(rutaCreada.getId(), envioCreado.getId());

        // Verificar: Que se agregó correctamente
        assertNotNull(rutaActualizada, "La ruta actualizada no debe ser null");
        assertEquals(1, rutaActualizada.getCantidadEnvios(), "Debe tener 1 envío");
        assertTrue(rutaActualizada.getPesoTotalCargado() > 0, "El peso total debe ser mayor a 0");
        assertTrue(rutaActualizada.getVolumenTotalCargado() > 0, "El volumen total debe ser mayor a 0");
    }

    // ========================================
    // TEST 3: Buscar ruta por ID correctamente ✅
    // ========================================
    /**
     * OBJETIVO: Verificar que se puede buscar una ruta por su ID
     * RESULTADO ESPERADO: Encuentra la ruta y retorna sus datos correctos
     */
    @Test
    public void testBuscarRutaPorId_Exitoso() {
        // Preparar: Crear vehículo y ruta
        VehiculoDTO vehiculoCreado = vehiculoService.crearVehiculo(vehiculoNoRefrigerado);

        RutaDTO rutaDTO = RutaDTO.builder()
                .fecha(LocalDate.now())
                .vehiculo(vehiculoCreado)
                .build();
        RutaDTO rutaCreada = rutaService.crearRuta(rutaDTO);
        Long idDeLaRuta = rutaCreada.getId();

        // Ejecutar: Buscar por ID
        RutaDTO rutaEncontrada = rutaService.buscarPorId(idDeLaRuta);

        // Verificar: Que se encontró correctamente
        assertNotNull(rutaEncontrada, "Debe encontrar la ruta");
        assertEquals(idDeLaRuta, rutaEncontrada.getId());
        assertEquals(rutaCreada.getFecha(), rutaEncontrada.getFecha());
        assertEquals(vehiculoCreado.getId(), rutaEncontrada.getVehiculo().getId());
    }

    // ========================================
    // TEST 4: Listar todas las rutas ✅
    // ========================================
    /**
     * OBJETIVO: Verificar que se pueden listar todas las rutas creadas
     * RESULTADO ESPERADO: La lista contiene las rutas creadas
     */
    @Test
    public void testListarTodasLasRutas_Exitoso() {
        // Preparar: Crear dos vehículos y dos rutas
        VehiculoDTO vehiculo1 = vehiculoService.crearVehiculo(vehiculoNoRefrigerado);
        VehiculoDTO vehiculo2 = vehiculoService.crearVehiculo(vehiculoRefrigerado);

        RutaDTO ruta1 = RutaDTO.builder()
                .fecha(LocalDate.now())
                .vehiculo(vehiculo1)
                .build();
        rutaService.crearRuta(ruta1);

        RutaDTO ruta2 = RutaDTO.builder()
                .fecha(LocalDate.now().plusDays(1))  // Diferente fecha
                .vehiculo(vehiculo2)
                .build();
        rutaService.crearRuta(ruta2);

        // Ejecutar: Listar todas
        List<RutaDTO> rutas = rutaService.listarRutas();

        // Verificar: Que la lista tiene al menos las 2 que creamos
        assertNotNull(rutas, "La lista no debe ser null");
        assertTrue(rutas.size() >= 2, "Debe haber al menos 2 rutas en la lista");
    }

    // ========================================
    // TEST 7: ERROR - Exceder capacidad de peso del vehículo ❌
    // ========================================
    /**
     * OBJETIVO: Validar que no se puede agregar un envío que exceda la capacidad de peso
     * RESULTADO ESPERADO: Lanza IllegalStateException por exceso de peso
     */
    @Test
    public void testExcederCapacidadPeso_Error() {
        // Preparar: Crear vehículo PEQUEÑO (solo 100kg) y su ruta
        VehiculoDTO vehiculoCreado = vehiculoService.crearVehiculo(vehiculoPequeno);

        RutaDTO rutaDTO = RutaDTO.builder()
                .fecha(LocalDate.now())
                .vehiculo(vehiculoCreado)
                .build();
        RutaDTO rutaCreada = rutaService.crearRuta(rutaDTO);

        // Crear primer envío de 50kg
        EnvioDTO envio1 = envioService.crearEnvio(envioConPaqueteFragil);  // 50kg

        // Agregar primer envío (OK: 50kg de 100kg disponibles)
        rutaService.agregarEnvio(rutaCreada.getId(), envio1.getId());

        // Crear SEGUNDO envío de 60kg (esto excederá: 50+60=110 > 100)
        PaqueteDTO paquetePesado = PaqueteDTO.builder()
                .codigo("PESADO" + System.currentTimeMillis())
                .peso(60.0)  // ← 60kg (total sería 110kg > 100kg)
                .volumen(10.0)
                .tipo("PF")
                .nivelFragilidad("BAJA")
                .seguroAdicional(false)
                .build();

        List<PaqueteDTO> paquetes = new ArrayList<>();
        paquetes.add(paquetePesado);

        EnvioDTO envio2 = EnvioDTO.builder()
                .remitente("Otro remitente")
                .destinatario("Otro destinatario")
                .direccionEntrega("Otra dirección")
                .estadoEnvio("GENERADO")
                .comprobanteEntrega(false)
                .paquetes(paquetes)
                .build();

        EnvioDTO envio2Creado = envioService.crearEnvio(envio2);

        // Verificar: Que lanza excepción por exceso de peso
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> rutaService.agregarEnvio(rutaCreada.getId(), envio2Creado.getId()),
                "Debe lanzar IllegalStateException por exceso de capacidad de peso"
        );

        // Verificar también el mensaje de error
        assertTrue(exception.getMessage().contains("No hay suficiente capacidad de peso"),
                "El mensaje debe indicar que no hay suficiente capacidad de peso");
    }

    // ========================================
    // TEST 8: ERROR - Exceder capacidad de volumen del vehículo ❌
    // ========================================
    /**
     * OBJETIVO: Validar que no se puede agregar un envío que exceda la capacidad de volumen
     * RESULTADO ESPERADO: Lanza IllegalStateException por exceso de volumen
     */
    @Test
    public void testExcederCapacidadVolumen_Error() {
        // Preparar: Crear vehículo PEQUEÑO (solo 25dm³) y su ruta
        VehiculoDTO vehiculoCreado = vehiculoService.crearVehiculo(vehiculoPequeno);

        RutaDTO rutaDTO = RutaDTO.builder()
                .fecha(LocalDate.now())
                .vehiculo(vehiculoCreado)
                .build();
        RutaDTO rutaCreada = rutaService.crearRuta(rutaDTO);

        // Crear primer envío de 20dm³
        EnvioDTO envio1 = envioService.crearEnvio(envioConPaqueteFragil);  // 20dm³

        // Agregar primer envío (OK: 20dm³ de 25dm³ disponibles)
        rutaService.agregarEnvio(rutaCreada.getId(), envio1.getId());

        // Crear SEGUNDO envío de 10dm³ (esto excederá: 20+10=30 > 25)
        PaqueteDTO paqueteGrande = PaqueteDTO.builder()
                .codigo("GRANDE" + System.currentTimeMillis())
                .peso(10.0)
                .volumen(10.0)  // ← 10dm³ (total sería 30dm³ > 25dm³)
                .tipo("PF")
                .nivelFragilidad("BAJA")
                .seguroAdicional(false)
                .build();

        List<PaqueteDTO> paquetes = new ArrayList<>();
        paquetes.add(paqueteGrande);

        EnvioDTO envio2 = EnvioDTO.builder()
                .remitente("Remitente 2")
                .destinatario("Destinatario 2")
                .direccionEntrega("Dirección 2")
                .estadoEnvio("GENERADO")
                .comprobanteEntrega(false)
                .paquetes(paquetes)
                .build();

        EnvioDTO envio2Creado = envioService.crearEnvio(envio2);

        // Verificar: Que lanza excepción por exceso de volumen
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> rutaService.agregarEnvio(rutaCreada.getId(), envio2Creado.getId()),
                "Debe lanzar IllegalStateException por exceso de capacidad de volumen"
        );

        // Verificar también el mensaje de error
        assertTrue(exception.getMessage().contains("No hay suficiente capacidad de volumen"),
                "El mensaje debe indicar que no hay suficiente capacidad de volumen");
    }
}