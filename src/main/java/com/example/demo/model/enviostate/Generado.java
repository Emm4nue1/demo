package com.example.demo.model.enviostate;

import com.example.demo.model.Envio;
import jakarta.persistence.Embeddable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Embeddable
public class Generado extends EnvioEstado {
    public Generado() {
        this.nombreEstado="GENERADO";
    }

    @Override
    public void procesar(Envio envio){
        if(envio.getEstadoEnvio() != null) {
            log.error("No puede tener un estado previo");
            throw new IllegalStateException("No puede tener un estado previo");
        }else
            log.info("Se cambio a estado generado");
    }
}
