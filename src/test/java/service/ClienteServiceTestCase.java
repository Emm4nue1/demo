package service;

import com.example.demo.DemoApplication;
import com.example.demo.dto.ClienteDTO;
import com.example.demo.service.ClienteService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = DemoApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public class ClienteServiceTestCase{

    @Autowired
    private ClienteService clienteService;

    private ClienteDTO clienteDTO1, clienteDTO2;

    @BeforeEach
    public void setUp() {
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
    public void testCrearCliente() {
        ClienteDTO creado = clienteService.crearCliente(clienteDTO1);
        assertNotNull(creado);
        assertEquals("Juan", creado.getNombre());
    }

    @Test
    public void testListarClientes() {
        clienteService.crearCliente(clienteDTO1);
        clienteService.crearCliente(clienteDTO2);

        List<ClienteDTO> lista = clienteService.listarClientes();
        assertEquals(2, lista.size());
    }

    @Test
    public void testBuscarClientePorDni() {
        clienteService.crearCliente(clienteDTO1);
        ClienteDTO encontrado = clienteService.buscarClientePorDni("45492312");
        assertEquals("Juan", encontrado.getNombre());

        assertThrows(IllegalArgumentException.class, () -> clienteService.buscarClientePorDni("00000000"));
    }

    @Test
    public void testBuscarClientePorEmail() {
        clienteService.crearCliente(clienteDTO1);
        ClienteDTO encontrado = clienteService.buscarClientePorEmail("persona1@gmail.com");
        assertEquals("Perez", encontrado.getApellido());

        assertThrows(IllegalArgumentException.class, () -> clienteService.buscarClientePorEmail("noexiste@gmail.com"));
    }
}

