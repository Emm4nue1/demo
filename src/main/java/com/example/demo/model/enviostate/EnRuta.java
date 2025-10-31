package com.example.demo.model.enviostate;

import com.example.demo.model.Envio;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnRuta extends EnvioEstado {
    @Override
    public void procesar(Envio envio) {
        if(!(envio.getEstadoEnvio() instanceof EnAlmacen)) {
            log.error("Solo puede cambiar de estado En almacen a En ruta");
            throw new IllegalStateException("Solo puede cambiar de estado En almacen a En ruta");
        }else
            log.info("Se cambio a estado En ruta");
    }
}
