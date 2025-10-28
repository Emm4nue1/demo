package service;

import com.example.demo.DemoApplication;
import dto.PaqueteDTO;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.platform.commons.function.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DemoApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class PaqueteServiceTestCase {

    @Autowired
    private PaqueteService service;

    PaqueteDTO paqueteDTO1;
    PaqueteDTO paqueteDTO2;

    @BeforeEach
    public void setUp(){
        paqueteDTO1= PaqueteDTO.builder().codigo("H123").peso(50).volumen(30).
                nivelFragilidad("BAJA").seguroAdicional(true).tipo("PF").build();
        paqueteDTO2= PaqueteDTO.builder().codigo("F321").peso(40).volumen(40).
                temperaturaObjetivo(30).rangoMaximo(45).rangoMinimo(20).maxHsFueraFrio(3).tipo("PR").build();
    }
    @Test
    public void testCrearPaquete(){
        PaqueteDTO paqueteDTO=service.crearPaquete(paqueteDTO1);
        assertNotNull(paqueteDTO);
        paqueteDTO2.setCodigo("H123");
        assertThrows(IllegalArgumentException.class,() -> service.crearPaquete(paqueteDTO2));
    }
    @Test
    public void testCrearPaqueteInvalidoPesoYvolumenInvalido(){
        paqueteDTO1.setPeso(-10);
        paqueteDTO1.setVolumen(-10);
        try{
            service.crearPaquete(paqueteDTO1);
        }catch(IllegalStateException e){
            assertTrue(e.getMessage().contains("Peso negativo"));
        }
    }
    @Test
    public void testCrearPaqueteRefrigeradoInvalidoPorTemperaturaObjetivo(){
        paqueteDTO2.setTemperaturaObjetivo(-200);
        try{
            service.crearPaquete(paqueteDTO2);
        }catch(IllegalStateException e){
            assertTrue(e.getMessage().contains("Temperatura objetivo invalida"));
        }
    }
    @Test
    public void testCrearPaqueteRefrigadoInvalidoPorRangos(){
        paqueteDTO2.setRangoMaximo(10);
        try{
            service.crearPaquete(paqueteDTO2);
        } catch (IllegalStateException e) {
            assertTrue(e.getMessage().contains("Error en seleccion de Rangos"));
        }
    }
    @Test
    public void testCrearPaqueteRefrigeradoInvalidoPorNivelFueraFrio(){
        paqueteDTO2.setMaxHsFueraFrio(-10);
        try{
            service.crearPaquete(paqueteDTO2);
        }catch(IllegalStateException e){
            assertTrue(e.getMessage().contains("La cantidad de horas fuera de frio no puede ser negativo"));
        }
    }
    @Test
    public void testListarPaquetes(){
        service.crearPaquete(paqueteDTO1);
        service.crearPaquete(paqueteDTO2);
        assertEquals(2,service.listarPaquetes().size());
    }
    @Test
    public void testListarPaquetesFragil(){
        paqueteDTO2= PaqueteDTO.builder().codigo("F312").peso(50).volumen(20).
                nivelFragilidad("BAJA").seguroAdicional(true).tipo("PF").build();
        service.crearPaquete(paqueteDTO1);
        service.crearPaquete(paqueteDTO2);
        assertEquals(2,service.listarPaqueteFragil().size());
    }
    @Test
    public void testListarPaquetesRefrigerados(){
        paqueteDTO1= PaqueteDTO.builder().codigo("H312").peso(50).volumen(20).
                temperaturaObjetivo(30).rangoMaximo(45).rangoMinimo(20).maxHsFueraFrio(3).tipo("PR").build();
        service.crearPaquete(paqueteDTO1);
        service.crearPaquete(paqueteDTO2);
        assertEquals(2,service.listarPaqueteRefrigerado().size());
    }
    @Test
    public void testListarPaquetesRangoPeso(){
        service.crearPaquete(paqueteDTO1);
        service.crearPaquete(paqueteDTO2);
        assertEquals(2,service.listarPaqueteRangoPeso(30,60).size());
    }
    @Test
    public void testListarPaquetesRangoVolumen(){
        service.crearPaquete(paqueteDTO1);
        service.crearPaquete(paqueteDTO2);
        assertEquals(2,service.listarPaqueteRangoVolumen(20,50).size());
    }
}
