package service;

import com.example.demo.DemoApplication;
import com.example.demo.dto.VehiculoDTO;
import com.example.demo.service.VehiculoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DemoApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class VehiculoServiceTestCase {

    @Autowired
    private VehiculoService vehiculoService;

    VehiculoDTO vehiculoNoRefrigerado;
    VehiculoDTO vehiculoRefrigerado;

    @BeforeEach
    public void setUp() {
        vehiculoNoRefrigerado = VehiculoDTO.builder()
                .patente("TEST" + System.currentTimeMillis())
                .capPeso(1000.0)
                .capVolumen(50.0)
                .refrigerado(false)
                .rangTempMin(null)
                .rangTempMax(null)
                .build();

        vehiculoRefrigerado = VehiculoDTO.builder()
                .patente("REFRI" + System.currentTimeMillis())
                .capPeso(1500.0)
                .capVolumen(80.0)
                .refrigerado(true)
                .rangTempMin(-5.0)
                .rangTempMax(5.0)
                .build();
    }

    // TEST 1: Crear vehículo no refrigerado
    @Test
    public void testCrearVehiculoNoRefrigerado_Exitoso() {
        VehiculoDTO resultado = vehiculoService.crearVehiculo(vehiculoNoRefrigerado);

        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertEquals(vehiculoNoRefrigerado.getPatente(), resultado.getPatente());
        assertEquals(1000.0, resultado.getCapPeso());
        assertFalse(resultado.getRefrigerado());
    }

    // TEST 2: Crear vehículo refrigerado
    @Test
    public void testCrearVehiculoRefrigerado_Exitoso() {
        VehiculoDTO resultado = vehiculoService.crearVehiculo(vehiculoRefrigerado);

        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertTrue(resultado.getRefrigerado());
        assertEquals(-5.0, resultado.getRangTempMin());
        assertEquals(5.0, resultado.getRangTempMax());
    }

    // TEST 3: Listar todos los vehículos
    @Test
    public void testListarTodosLosVehiculos_Exitoso() {
        vehiculoService.crearVehiculo(vehiculoNoRefrigerado);
        vehiculoService.crearVehiculo(vehiculoRefrigerado);

        List<VehiculoDTO> vehiculos = vehiculoService.listarVehiculos();

        assertNotNull(vehiculos);
        assertTrue(vehiculos.size() >= 2);
    }

    // TEST 5: Buscar vehículo por patente
    @Test
    public void testBuscarVehiculoPorPatente() {
        VehiculoDTO vehiculoCreado = vehiculoService.crearVehiculo(vehiculoNoRefrigerado);

        VehiculoDTO vehiculoEncontrado = vehiculoService.buscarPorPatente(vehiculoCreado.getPatente());

        assertNotNull(vehiculoEncontrado);
        assertEquals(vehiculoCreado.getPatente(), vehiculoEncontrado.getPatente());
        assertEquals(vehiculoCreado.getId(), vehiculoEncontrado.getId());
    }

    // TEST 6: Buscar vehículos por capacidad de peso (PEDIDO EN TP)
    @Test
    public void testBuscarVehiculosPorCapacidadPeso() {
        vehiculoService.crearVehiculo(vehiculoNoRefrigerado);
        vehiculoService.crearVehiculo(vehiculoRefrigerado);

        List<VehiculoDTO> vehiculos = vehiculoService.listarPorCapacidadPeso(900.0);

        assertNotNull(vehiculos);
        assertFalse(vehiculos.isEmpty());
        for (VehiculoDTO v : vehiculos) {
            assertTrue(v.getCapPeso() >= 900.0);
        }
    }

    // TEST 7: Buscar vehículos por capacidad de volumen (PEDIDO EN TP)
    @Test
    public void testBuscarVehiculosPorCapacidadVolumen() {
        vehiculoService.crearVehiculo(vehiculoNoRefrigerado);
        vehiculoService.crearVehiculo(vehiculoRefrigerado);

        List<VehiculoDTO> vehiculos = vehiculoService.listarPorCapacidadVolumen(40.0);

        assertNotNull(vehiculos);
        assertFalse(vehiculos.isEmpty());
        for (VehiculoDTO v : vehiculos) {
            assertTrue(v.getCapVolumen() >= 40.0);
        }
    }

    // TEST 8: Buscar vehículos refrigerados (PEDIDO EN TP)
    @Test
    public void testBuscarVehiculosRefrigerados() {
        vehiculoService.crearVehiculo(vehiculoNoRefrigerado);
        vehiculoService.crearVehiculo(vehiculoRefrigerado);

        List<VehiculoDTO> refrigerados = vehiculoService.listarPorRefrigerado(true);

        assertNotNull(refrigerados);
        assertFalse(refrigerados.isEmpty());
        for (VehiculoDTO v : refrigerados) {
            assertTrue(v.getRefrigerado());
        }
    }

    // TEST 9: Buscar vehículos NO refrigerados (PEDIDO EN TP)
    @Test
    public void testBuscarVehiculosNoRefrigerados() {
        vehiculoService.crearVehiculo(vehiculoNoRefrigerado);
        vehiculoService.crearVehiculo(vehiculoRefrigerado);

        List<VehiculoDTO> noRefrigerados = vehiculoService.listarPorRefrigerado(false);

        assertNotNull(noRefrigerados);
        assertFalse(noRefrigerados.isEmpty());
        for (VehiculoDTO v : noRefrigerados) {
            assertFalse(v.getRefrigerado());
        }
    }

    // NUEVO: Buscar vehículos con capacidad suficiente (peso y volumen)
    @Test
    public void testBuscarVehiculosPorCapacidadSuficiente() {
        vehiculoService.crearVehiculo(vehiculoNoRefrigerado);
        vehiculoService.crearVehiculo(vehiculoRefrigerado);

        List<VehiculoDTO> vehiculos = vehiculoService.listarPorCapacidadSuficiente(800.0, 40.0);

        assertNotNull(vehiculos);
        assertFalse(vehiculos.isEmpty());
        for (VehiculoDTO v : vehiculos) {
            assertTrue(v.getCapPeso() >= 800.0);
            assertTrue(v.getCapVolumen() >= 40.0);
        }
    }

    // TEST 11: Error al crear vehículo con patente duplicada
    @Test
    public void testCrearVehiculoPatentesDuplicadas_Error() {
        vehiculoService.crearVehiculo(vehiculoNoRefrigerado);

        VehiculoDTO vehiculoDuplicado = VehiculoDTO.builder()
                .patente(vehiculoNoRefrigerado.getPatente())
                .capPeso(800.0)
                .capVolumen(40.0)
                .refrigerado(false)
                .build();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> vehiculoService.crearVehiculo(vehiculoDuplicado)
        );

        assertTrue(exception.getMessage().contains("Ya existe un vehículo con la patente"));
    }

    // TEST 12: Error al crear vehículo con peso negativo
    @Test
    public void testCrearVehiculoPesoNegativo_Error() {
        vehiculoNoRefrigerado.setCapPeso(-100.0);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> vehiculoService.crearVehiculo(vehiculoNoRefrigerado)
        );

        assertTrue(exception.getMessage().contains("capacidad de peso debe ser mayor a 0"));
    }

    // NUEVO: Error al crear vehículo con volumen negativo
    @Test
    public void testCrearVehiculoVolumenNegativo_Error() {
        vehiculoNoRefrigerado.setCapVolumen(-50.0);

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> vehiculoService.crearVehiculo(vehiculoNoRefrigerado)
        );

        assertTrue(exception.getMessage().contains("capacidad de volumen debe ser mayor a 0"));
    }

    // NUEVO: Error al crear vehículo refrigerado sin rangos de temperatura
    @Test
    public void testCrearVehiculoRefrigeradoSinRangosTemperatura_Error() {
        VehiculoDTO vehiculoMalConfigurado = VehiculoDTO.builder()
                .patente("MAL" + System.currentTimeMillis())
                .capPeso(1000.0)
                .capVolumen(50.0)
                .refrigerado(true)
                .rangTempMin(null)
                .rangTempMax(null)
                .build();

        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> vehiculoService.crearVehiculo(vehiculoMalConfigurado)
        );

        assertTrue(exception.getMessage().contains("deben tener rangos de temperatura"));
    }
}