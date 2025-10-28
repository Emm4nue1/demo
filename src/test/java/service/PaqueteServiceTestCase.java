package service;

import com.example.demo.DemoApplication;
import dto.PaqueteDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.platform.commons.function.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DemoApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PaqueteServiceTestCase {

    @Autowired
    private PaqueteService service;

    PaqueteDTO paqueteDTO1;
    PaqueteDTO paqueteDTO2;

    @BeforeEach
    public void setUp(){
        paqueteDTO1= PaqueteDTO.builder().codigo("123").peso(50).volumen(20).
                nivelFragilidad("BAJA").seguroAdicional(true).build();
        paqueteDTO2= PaqueteDTO.builder().codigo("123").peso(50).volumen(20).
                temperaturaObjetivo(30).rangoMaximo(45).rangoMinimo(20).maxHsFueraFrio(3).build();
    }
    @Test
    public void testCrearPaquete(){
        PaqueteDTO paqueteDTO=service.crearPaquete(paqueteDTO1);
        assertNotNull(paqueteDTO);
        assertThrows(IllegalArgumentException.class,() -> service.crearPaquete(paqueteDTO2));
    }
    @Test
    public void testCrearPaqueteInvalidoPesoYvolumenInvalido(){
        paqueteDTO1.setPeso(-10);
        paqueteDTO1.setVolumen(-10);
        try{service.crearPaquete(paqueteDTO1);
        }catch(IllegalStateException e){assertTrue(e.getMessage().contains("Peso negativo"));
        }
    }
    @Test
    public void testCrearPaqueteRefrigeradoInvalidoPorTemperaturaObjetivo(){

    }
    @Test
    public void testCrearPaqueteRefrigadoInvalidoPorRangos(){

    }
    @Test
    public void testCrearPaqueteRefrigeradoInvalidoPorNivelFueraFrio(){

    }
    @Test
    public void testListarPaquetes(){

    }
    @Test
    public void testListarPaquetesFragil(){

    }
    @Test
    public void testListarPaquetesRefrigerados(){

    }
    @Test
    public void testListarPaquetesRangoPeso(){

    }
    @Test
    public void testListarPaquetesRangoVolumen(){

    }
}
