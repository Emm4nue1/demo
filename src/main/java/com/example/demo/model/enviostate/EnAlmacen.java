package com.example.demo.model.enviostate;

import com.example.demo.model.Envio;
import jakarta.persistence.Embeddable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Embeddable
public class EnAlmacen extends EnvioEstado {
    @Override
    public void procesar(Envio envio){
        if(!(envio.getEstadoEnvio() instanceof Generado)) {
            log.error("Solo puede cambiar de estado Generado a En almacen");
            throw new IllegalStateException("Solo puede cambiar de estado Generado a En almacen");
        }else
            log.info("Se cambio a estado En Almacen");
    }
}
