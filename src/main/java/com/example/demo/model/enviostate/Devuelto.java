package com.example.demo.model.enviostate;

import com.example.demo.model.Envio;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Devuelto extends EnvioEstado {
    @Override
    public void procesar(Envio envio) {
        if(!(envio.getEstadoEnvio() instanceof Entregado)) {
            log.error("Solo puede devolver Siempre que haya sido entregado");
            throw new IllegalStateException("Solo puede devolver Siempre que haya sido entregado");
        }else
            log.info("Se cambio a estado Devuelto");
    }
}
