package mapper;
import dto.EnvioDTO;
import dto.PaqueteDTO;
import model.Envio;
import model.enums.EstadoEnvio;
import model.paquete.Paquete;

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
                paquetesDTO.add(PaqueteMapper.toDto(p));
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
                paquetes.add(PaqueteMapper.toEntity(pDto));
            }
            envio.setPaquetes(paquetes);
        }
        return envio;
    }
}
