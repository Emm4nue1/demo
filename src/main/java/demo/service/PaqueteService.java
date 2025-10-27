package demo.service;

import demo.dto.packet.PaqueteDTO;
import jakarta.transaction.Transactional;
import demo.mapper.packet.PaqueteMapper;
import demo.model.paquete.Paquete;
import demo.model.paquete.PaqueteFragil;
import demo.model.paquete.PaqueteRefrigerado;
import org.springframework.stereotype.Service;
import demo.repository.PaqueteRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaqueteService {
    private final PaqueteRepository paqueteRepository;
    public PaqueteService(PaqueteRepository paqueteRepository) {
        this.paqueteRepository = paqueteRepository;
    }

    @Transactional
    public PaqueteDTO crearPaquete(PaqueteDTO paqueteDTO) {
        paqueteRepository.findByCodigo(paqueteDTO.getCodigo()).ifPresent(paquete1 -> {
            throw new IllegalArgumentException("Paquete existente");
        });
        Paquete paquete = PaqueteMapper.toSpecificEntity(paqueteDTO);
        if(paquete.getPeso()<0 || paquete.getVolumen()<0)
            throw new IllegalStateException("Peso negativo");
        if(paquete instanceof PaqueteRefrigerado){
            PaqueteRefrigerado paqueteRefrigerado = (PaqueteRefrigerado) paquete;
            if(paqueteRefrigerado.getTemperaturaObjetivo()<-100 || paqueteRefrigerado.getTemperaturaObjetivo()>100)
                throw new IllegalStateException("Temperatura objetivo invalida");
            if(paqueteRefrigerado.getRangoMaximo()<paqueteRefrigerado.getRangoMinimo())
                throw new IllegalStateException("Error en seleccion de Rangos");
            if(paqueteRefrigerado.getMaxHsFueraFrio()<0)
                throw new IllegalStateException("La cantidad de horas fuera de frio no puede ser negativo");
        }
        Paquete paqueteAux = paqueteRepository.save(paquete);
        return PaqueteMapper.toSpecificDto(paqueteAux);
    }

    public List<PaqueteDTO> listarPaquetes() {
        List<Paquete> listaPaquetes = paqueteRepository.findAll();
        List<PaqueteDTO> listaPaqueteDTO = new ArrayList<>();
        for(Paquete p : listaPaquetes){
            listaPaqueteDTO.add(PaqueteMapper.toSpecificDto(p));
        }
        return listaPaqueteDTO;
    }
    public List<PaqueteDTO> listarPaqueteFragil(){
        List<Paquete> listaPaqueteFragil = paqueteRepository.findAll();
        List<PaqueteDTO> listaPaqueteFragilDTO = new ArrayList<>();
        for(Paquete p : listaPaqueteFragil){
            if(p instanceof PaqueteFragil){
                listaPaqueteFragilDTO.add(PaqueteMapper.toSpecificDto(p));
            }
        }
        return listaPaqueteFragilDTO;
    }
    public List<PaqueteDTO> listarPaqueteRefrigerado(){
        List<Paquete> listaPaqueteRefrigerado = paqueteRepository.findAll();
        List<PaqueteDTO> listaPaqueteRefrigeradoDTO = new ArrayList<>();
        for(Paquete p : listaPaqueteRefrigerado){
            if(p instanceof PaqueteRefrigerado){
                listaPaqueteRefrigeradoDTO.add(PaqueteMapper.toSpecificDto(p));
            }
        }
        return listaPaqueteRefrigeradoDTO;
    }
    public List<PaqueteDTO> listarPaqueteRangoPeso(double min, double max){
        List<Paquete> listaPaqueteRangoPeso = paqueteRepository.findByPesoBetween(min,max);
        List<PaqueteDTO> listaPaqueteRangoDTO = new ArrayList<>();
        for(Paquete p :  listaPaqueteRangoPeso){
            listaPaqueteRangoDTO.add(PaqueteMapper.toSpecificDto(p));
        }
        return listaPaqueteRangoDTO;
    }
    public List<PaqueteDTO> listarPaqueteRangoVolumen(double min, double max){
        List<Paquete> listaPaqueteRangoVolumen = paqueteRepository.findByVolumenBetween(min,max);
        List<PaqueteDTO> listaPaqueteRangoDTO = new ArrayList<>();
        for(Paquete p :  listaPaqueteRangoVolumen){
            listaPaqueteRangoDTO.add(PaqueteMapper.toSpecificDto(p));
        }
        return listaPaqueteRangoDTO;
    }
}
