package com.example.demo.service;

import com.example.demo.dto.ClienteDTO;
import com.example.demo.mapper.ClienteMapper;
import com.example.demo.model.Cliente;
import com.example.demo.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional
    public ClienteDTO crearCliente(ClienteDTO clienteDTO) {
        validacionCrearCliente(clienteDTO);
        Cliente cliente = ClienteMapper.toEntity(clienteDTO);
        clienteRepository.save(cliente);
        return ClienteMapper.toDto(cliente);
    }

    public List<ClienteDTO> listarClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        List<ClienteDTO> clientesDTO=  new ArrayList<>();
        for (Cliente cliente : clientes) {
            clientesDTO.add(ClienteMapper.toDto(cliente));
        }
        return clientesDTO;
    }

    private void validacionCrearCliente(ClienteDTO clienteDTO){
        clienteRepository.findByDni(clienteDTO.getDni()).ifPresent(cliente -> {
            throw new IllegalArgumentException("Cliente existente");
        });
        clienteRepository.findByEmail(clienteDTO.getEmail()).ifPresent(cliente -> {
            throw new IllegalArgumentException("Cliente existente");
        });
    }

    public ClienteDTO buscarClientePorDni(String dni){
        Cliente cliente = clienteRepository.findByDni(dni).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        return ClienteMapper.toDto(cliente);
    }

    public ClienteDTO buscarClientePorEmail(String email){
        Cliente cliente = clienteRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        return ClienteMapper.toDto(cliente);
    }
}
