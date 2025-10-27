package demo.mapper;
import demo.dto.EnvioDTO;
import demo.dto.packet.PaqueteDTO;
import demo.mapper.packet.PaqueteMapper;
import demo.model.Envio;
import demo.model.enums.EstadoEnvio;
import demo.model.packet.Paquete;

import java.util.ArrayList;
import java.util.List;

public class EnvioMapper {

    //convertimos de una entidad Envio en DTO
    public static EnvioDTO toDto(Envio envio){

        EnvioDTO dto= new EnvioDTO();
        dto.setRemitente(envio.getRemitente());
        dto.setDestinatario(envio.getDestinatario());
        dto.setDireccionEntrega(envio.getDireccionEntrega());
        dto.setEstadoEnvio(envio.getEstadoEnvio().toString());
        dto.setComprobanteEntrega(envio.getComprobanteEntrega());
        if (envio.getPaquetes() != null) {
            List<PaqueteDTO> paquetesDTO = new ArrayList<>();
            for (Paquete p : envio.getPaquetes()) {
                paquetesDTO.add(PaqueteMapper.toSpecificDto(p));
            }
            dto.setPaquetes(paquetesDTO);
        }

        return dto;
    }

    //convertimos de DTO a entidad Envio
    public static Envio toEntity(EnvioDTO dto){

        Envio envio= new Envio();
        envio.setRemitente(dto.getRemitente());
        envio.setDestinatario(dto.getDestinatario());
        envio.setDireccionEntrega(dto.getDireccionEntrega());
        envio.setEstadoEnvio(EstadoEnvio.valueOf(dto.getEstadoEnvio()));
        envio.setComprobanteEntrega(dto.isComprobanteEntrega());
        if (dto.getPaquetes() != null) {
            List<Paquete> paquetes = new ArrayList<>();
            for (PaqueteDTO pDto : dto.getPaquetes()) {
                paquetes.add(PaqueteMapper.toSpecificEntity(pDto));
            }
            envio.setPaquetes(paquetes);
        }
        return envio;
    }
}
