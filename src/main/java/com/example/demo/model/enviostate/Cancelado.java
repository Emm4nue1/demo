package com.example.demo.model.enviostate;

import com.example.demo.model.Envio;
import jakarta.persistence.Embeddable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Embeddable
public class Cancelado extends EnvioEstado{

    //falto esto
    public Cancelado() {
        this.nombreEstado = "CANCELADO";
    }
    @Override
    public void procesar(Envio envio) {
        if(!(envio.getEstadoEnvio() instanceof Generado)) {
            log.error("Solo puede cancelar Siempre que haya sido generado");
            throw new IllegalStateException("Solo puede cancelar Siempre que haya sido generado");
        }else
            log.info("Se cambio a estado Cancelado");
    }
}
