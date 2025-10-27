package demo.service;

import demo.dto.EnvioDTO;
import jakarta.transaction.Transactional;
import demo.mapper.EnvioMapper;
import demo.model.Envio;
import demo.model.enums.EstadoEnvio;
import org.springframework.stereotype.Service;
import demo.repository.EnvioRepository;
import demo.repository.PaqueteRepository;

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
        List<Envio> envios = envioRepository.findByEstadoEnvio(EstadoEnvio.valueOf(estado));
        List<EnvioDTO> listaEnvioDTO= new ArrayList<>();
        for (Envio envio : envios) {
            listaEnvioDTO.add(EnvioMapper.toDto(envio));
        }
        return listaEnvioDTO;
    }
}
