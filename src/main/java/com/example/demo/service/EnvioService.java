package com.example.demo.service;

import com.example.demo.dto.EnvioDTO;
import com.example.demo.model.enviostate.EnvioEstado;
import com.example.demo.util.Utils;
import jakarta.transaction.Transactional;
import com.example.demo.mapper.EnvioMapper;
import com.example.demo.model.Envio;
import org.springframework.stereotype.Service;
import com.example.demo.repository.EnvioRepository;
import com.example.demo.repository.PaqueteRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnvioService {

    private final EnvioRepository envioRepository;
    public EnvioService(EnvioRepository envioRepository, PaqueteRepository paqueteRepository) {
        this.envioRepository = envioRepository;
    }

    @Transactional
    public EnvioDTO crearEnvio(EnvioDTO envioDTO) {
        Envio envio = EnvioMapper.toEntity(envioDTO);
        envio=envioRepository.save(envio);
        return  EnvioMapper.toDto(envio);
    }

    @Transactional
    public List<EnvioDTO> listarEnvio(){
        List<Envio> envios = envioRepository.findAll();
        List<EnvioDTO> listaEnvioDTO= new ArrayList<>();
        for (Envio envio : envios) {
            listaEnvioDTO.add(EnvioMapper.toDto(envio));
        }
        return listaEnvioDTO;
    }

    @Transactional
    public List<EnvioDTO> listarPorRemitente(String remitente){
        List<Envio> envios = envioRepository.findByRemitente(remitente);
        List<EnvioDTO> listaEnvioDTO= new ArrayList<>();
        for (Envio envio : envios) {
            listaEnvioDTO.add(EnvioMapper.toDto(envio));
        }
        return listaEnvioDTO;
    }

    @Transactional
    public List<EnvioDTO> listarPorDestinatario(String destinatario){
        List<Envio> envios = envioRepository.findByDestinatario(destinatario);
        List<EnvioDTO> listaEnvioDTO= new ArrayList<>();
        for (Envio envio : envios) {
            listaEnvioDTO.add(EnvioMapper.toDto(envio));
        }
        return listaEnvioDTO;
    }

    @Transactional
    public List<EnvioDTO> listarPorEstado(String estado){
        EnvioEstado estadoAux = Utils.crearEstadoDesdeNombre(estado);
        List<Envio> envios = envioRepository.findByEstadoEnvio(estadoAux);
        List<EnvioDTO> listaEnvioDTO= new ArrayList<>();
        for (Envio envio : envios) {
            listaEnvioDTO.add(EnvioMapper.toDto(envio));
        }
        return listaEnvioDTO;
    }
}
