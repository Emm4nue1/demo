package com.example.demo.mapper;

import com.example.demo.dto.ClienteDTO;
import com.example.demo.dto.EnvioDTO;
import com.example.demo.dto.PaqueteDTO;
import com.example.demo.model.Cliente;
import com.example.demo.model.Envio;
import com.example.demo.model.paquete.Paquete;

import java.util.ArrayList;
import java.util.List;

public class ClienteMapper {
    //convertimos de una entidad Cliente en DTO
    public static ClienteDTO toDto(Cliente cliente){
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(cliente.getId());
        clienteDTO.setDni(cliente.getDni());
        clienteDTO.setEmail(cliente.getEmail());
        clienteDTO.setCodPostal(cliente.getCodPostal());
        return clienteDTO;
    }

    //convertimos de DTO a entidad Cliente
    public static Cliente toEntity(ClienteDTO clienteDTO){
        Cliente cliente = new Cliente();
        cliente.setId(clienteDTO.getId());
        cliente.setDni(clienteDTO.getDni());
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setCodPostal(clienteDTO.getCodPostal());
        return cliente;
    }
}
