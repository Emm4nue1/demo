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
@Transactional  // Cada test hace rollback automático para no afectar la BD
public class VehiculoServiceTestCase {

    @Autowired
    private VehiculoService vehiculoService;

    VehiculoDTO vehiculoNoRefrigerado;
    VehiculoDTO vehiculoRefrigerado;

    @BeforeEach
    public void setUp() {
        // Preparar vehículo NO refrigerado para pruebas
        vehiculoNoRefrigerado = VehiculoDTO.builder()
                .patente("TEST" + System.currentTimeMillis())  // Patente única para evitar duplicados
                .capPeso(1000.0)
                .capVolumen(50.0)
                .refrigerado(false)
                .rangTempMin(null)
                .rangTempMax(null)
                .build();

        // Preparar vehículo refrigerado para pruebas
        vehiculoRefrigerado = VehiculoDTO.builder()
                .patente("REFRI" + System.currentTimeMillis())  // Patente única
                .capPeso(1500.0)
                .capVolumen(80.0)
                .refrigerado(true)
                .rangTempMin(-5.0)
                .rangTempMax(5.0)
                .build();
    }

    // ========================================
    // TEST 1: Crear un vehículo NO refrigerado correctamente ✅
    // ========================================
    /**
     * OBJETIVO: Verificar que se puede crear un vehículo NO refrigerado sin errores
     * RESULTADO ESPERADO: El vehículo se crea y tiene un ID asignado
     */
    @Test
    public void testCrearVehiculoNoRefrigerado_Exitoso() {
        // Ejecutar: Crear el vehículo
        VehiculoDTO resultado = vehiculoService.crearVehiculo(vehiculoNoRefrigerado);

        // Verificar: Que se creó correctamente
        assertNotNull(resultado, "El vehículo creado no debe ser null");
        assertNotNull(resultado.getId(), "Debe tener un ID asignado");
        assertEquals(vehiculoNoRefrigerado.getPatente(), resultado.getPatente());
        assertEquals(1000.0, resultado.getCapPeso());
        assertEquals(50.0, resultado.getCapVolumen());
        assertFalse(resultado.getRefrigerado(), "No debe estar marcado como refrigerado");
    }

    // ========================================
    // TEST 2: Crear un vehículo refrigerado correctamente ✅
    // ========================================
    /**
     * OBJETIVO: Verificar que se puede crear un vehículo refrigerado con rangos de temperatura
     * RESULTADO ESPERADO: El vehículo se crea con los rangos de temperatura correctos
     */
    @Test
    public void testCrearVehiculoRefrigerado_Exitoso() {
        // Ejecutar: Crear el vehículo refrigerado
        VehiculoDTO resultado = vehiculoService.crearVehiculo(vehiculoRefrigerado);

        // Verificar: Que se creó correctamente con sus temperaturas
        assertNotNull(resultado, "El vehículo creado no debe ser null");
        assertNotNull(resultado.getId(), "Debe tener un ID asignado");
        assertTrue(resultado.getRefrigerado(), "Debe estar marcado como refrigerado");
        assertEquals(-5.0, resultado.getRangTempMin(), "La temperatura mínima debe ser -5°C");
        assertEquals(5.0, resultado.getRangTempMax(), "La temperatura máxima debe ser 5°C");
    }

    // ========================================
    // TEST 3: Buscar vehículo por ID correctamente ✅
    // ========================================
    /**
     * OBJETIVO: Verificar que se puede buscar un vehículo por su ID
     * RESULTADO ESPERADO: Encuentra el vehículo y retorna sus datos correctos
     */
    @Test
    public void testBuscarVehiculoPorId_Exitoso() {
        // Preparar: Primero crear un vehículo
        VehiculoDTO vehiculoCreado = vehiculoService.crearVehiculo(vehiculoNoRefrigerado);
        Long idDelVehiculo = vehiculoCreado.getId();

        // Ejecutar: Buscar por ID
        VehiculoDTO vehiculoEncontrado = vehiculoService.buscarPorId(idDelVehiculo);

        // Verificar: Que se encontró y tiene los datos correctos
        assertNotNull(vehiculoEncontrado, "Debe encontrar el vehículo");
        assertEquals(idDelVehiculo, vehiculoEncontrado.getId());
        assertEquals(vehiculoCreado.getPatente(), vehiculoEncontrado.getPatente());
        assertEquals(vehiculoCreado.getCapPeso(), vehiculoEncontrado.getCapPeso());
    }

    // ========================================
    // TEST 4: Listar todos los vehículos ✅
    // ========================================
    /**
     * OBJETIVO: Verificar que se pueden listar todos los vehículos creados
     * RESULTADO ESPERADO: La lista contiene los vehículos creados
     */
    @Test
    public void testListarTodosLosVehiculos_Exitoso() {
        // Preparar: Crear dos vehículos
        vehiculoService.crearVehiculo(vehiculoNoRefrigerado);
        vehiculoService.crearVehiculo(vehiculoRefrigerado);

        // Ejecutar: Listar todos
        List<VehiculoDTO> vehiculos = vehiculoService.listarVehiculos();

        // Verificar: Que la lista no está vacía y tiene al menos los 2 que creamos
        assertNotNull(vehiculos, "La lista no debe ser null");
        assertTrue(vehiculos.size() >= 2, "Debe haber al menos 2 vehículos en la lista");
    }

    // ========================================
    // TEST 5: ERROR - Intentar crear vehículo con patente duplicada ❌
    // ========================================
    /**
     * OBJETIVO: Validar que no se pueden crear dos vehículos con la misma patente
     * RESULTADO ESPERADO: Lanza IllegalArgumentException al intentar duplicar patente
     */
    @Test
    public void testCrearVehiculoPatentesDuplicadas_Error() {
        // Preparar: Crear el primer vehículo
        vehiculoService.crearVehiculo(vehiculoNoRefrigerado);

        // Intentar: Crear otro vehículo con la MISMA patente
        VehiculoDTO vehiculoDuplicado = VehiculoDTO.builder()
                .patente(vehiculoNoRefrigerado.getPatente())  // ← MISMA PATENTE
                .capPeso(800.0)
                .capVolumen(40.0)
                .refrigerado(false)
                .build();

        // Verificar: Que lanza excepción por patente duplicada
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> vehiculoService.crearVehiculo(vehiculoDuplicado),
                "Debe lanzar IllegalArgumentException por patente duplicada"
        );

        // Verificar también el mensaje de error
        assertTrue(exception.getMessage().contains("Ya existe un vehículo con la patente"),
                "El mensaje debe indicar que la patente ya existe");
    }

    // ========================================
    // TEST 6: ERROR - Crear vehículo con capacidad de peso negativa ❌
    // ========================================
    /**
     * OBJETIVO: Validar que no se pueden crear vehículos con peso negativo
     * RESULTADO ESPERADO: Lanza IllegalStateException por peso inválido
     */
    @Test
    public void testCrearVehiculoPesoNegativo_Error() {
        // Preparar: Modificar el DTO para que tenga peso negativo
        vehiculoNoRefrigerado.setCapPeso(-100.0);  // ← PESO NEGATIVO (inválido)

        // Verificar: Que lanza excepción por peso negativo
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> vehiculoService.crearVehiculo(vehiculoNoRefrigerado),
                "Debe lanzar IllegalStateException por peso negativo"
        );

        // Verificar también el mensaje de error
        assertTrue(exception.getMessage().contains("capacidad de peso debe ser mayor a 0"),
                "El mensaje debe indicar que el peso debe ser positivo");
    }
}