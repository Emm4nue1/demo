package com.example.demo.service;
import com.example.demo.dto.ClienteDTO;
import com.example.demo.dto.EnvioDTO;
import com.example.demo.dto.HistorialEstadoEnvioDTO;
import com.example.demo.dto.PaqueteDTO;
import com.example.demo.mapper.ClienteMapper;
import com.example.demo.model.Cliente;
import com.example.demo.model.HistorialEstadoEnvio;
import com.example.demo.model.enviostate.Cancelado;
import com.example.demo.model.enviostate.EnvioEstado;
import com.example.demo.model.enviostate.Generado;
import com.example.demo.model.paquete.Paquete;
import com.example.demo.model.paquete.PaqueteRefrigerado;
import com.example.demo.util.Utils;
import jakarta.transaction.Transactional;
import com.example.demo.mapper.EnvioMapper;
import com.example.demo.model.Envio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.example.demo.repository.EnvioRepository;
import com.example.demo.repository.PaqueteRepository;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
public class EnvioService {

    private final EnvioRepository envioRepository;
    private final HistorialEstadoEnvioService historialEstadoEnvioService;
    public EnvioService(EnvioRepository envioRepository, HistorialEstadoEnvioService historialEstadoEnvioService) {
        this.envioRepository = envioRepository;
        this.historialEstadoEnvioService = historialEstadoEnvioService;
    }

    @Transactional
    public EnvioDTO crearEnvio(EnvioDTO envioDTO) {
        validacionesCrearEnvio(envioDTO);
        Envio envio = EnvioMapper.toEntity(envioDTO);
        envio.cambiarEstado(new Generado());
        envio=envioRepository.save(envio);
        HistorialEstadoEnvioDTO historialEstadoEnvioNuevo = new HistorialEstadoEnvioDTO();
        historialEstadoEnvioNuevo.setEnvio(EnvioMapper.toDto(envio));
        historialEstadoEnvioNuevo.setEstadoNuevo("GENERADO");
        historialEstadoEnvioService.crearHistorialEstadoEnvio(historialEstadoEnvioNuevo);
        log.debug("3");
        return  EnvioMapper.toDto(envio);
    }

    private String generarHash(Envio envio) {
        String cod = envio.getCodPostal();
        int rand = (int)  (Math.random() * 9000) + 1000;
        return cod + "-" + rand;
    }

    private void validacionesCrearEnvio(EnvioDTO envioDTO) {
        validacionPesoyVolumen(envioDTO);
    }

    private boolean tienePackageRefrigerado(List<Paquete> paquetes) {
        if (paquetes == null) return false;
        return paquetes.stream().anyMatch(p -> p instanceof PaqueteRefrigerado);
    }
    private void validacionPesoyVolumen(EnvioDTO envioDTO) {
        List<PaqueteDTO> paqueteDTOs = envioDTO.getPaquetes();
        for(PaqueteDTO paqueteDTO : paqueteDTOs){
            if(paqueteDTO.getPeso()<=0 || paqueteDTO.getVolumen()<=0)
                throw new IllegalStateException("Los paquetes tienen peso o volumen invalidos");
        }
    }
    public boolean validacionPaqueteRefrigerado(EnvioDTO envioDTO) {
        List<PaqueteDTO> paqueteDTOs = envioDTO.getPaquetes();
        for(PaqueteDTO paqueteDTO : paqueteDTOs){
            if(paqueteDTO.getTipo().equalsIgnoreCase("PR"))
                return true;
        }
        return false;
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
    public List<EnvioDTO> listarPorRemitente(ClienteDTO remitente){
        Cliente clienteAux = ClienteMapper.toEntity(remitente);
        List<Envio> envios = envioRepository.findByRemitente(clienteAux);
        List<EnvioDTO> listaEnvioDTO= new ArrayList<>();
        for (Envio envio : envios) {
            listaEnvioDTO.add(EnvioMapper.toDto(envio));
        }
        return listaEnvioDTO;
    }

    @Transactional
    public List<EnvioDTO> listarPorDestinatario(ClienteDTO destinatario){
        Cliente clienteAux = ClienteMapper.toEntity(destinatario);
        List<Envio> envios = envioRepository.findByDestinatario(clienteAux);
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

    @Transactional
    public EnvioDTO cancelarEnvio(EnvioDTO envioDTO){
        Envio envio = envioRepository.findById(envioDTO.getId()).orElseThrow(() -> new IllegalStateException("Envio no encontrado"));
        String estadoAnterior = Utils.obtenerNombreDesdeEstado(envio.getEstadoEnvio());
        envio.cambiarEstado(new Cancelado());
        HistorialEstadoEnvioDTO historialEstadoEnvio = new HistorialEstadoEnvioDTO();
        historialEstadoEnvio.setEnvio(EnvioMapper.toDto(envio));
        historialEstadoEnvio.setEstadoAnterior(estadoAnterior);
        historialEstadoEnvio.setEstadoNuevo("CANCELADO");
        historialEstadoEnvioService.crearHistorialEstadoEnvio(historialEstadoEnvio);
        envioRepository.save(envio);
        return EnvioMapper.toDto(envio);
    }

}
