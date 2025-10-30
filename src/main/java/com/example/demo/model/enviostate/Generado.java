package com.example.demo.model.enviostate;

import com.example.demo.model.Envio;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Generado extends EnvioEstado {
    @Override
    public void procesar(Envio envio){
        if(envio != null) {
            log.error("No puede tener un estado previo");
            throw new IllegalStateException("No puede tener un estado previo");
        }else
            log.info("Se cambio a estado generado");
    }
}
