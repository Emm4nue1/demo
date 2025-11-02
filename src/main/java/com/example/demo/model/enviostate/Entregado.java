package com.example.demo.model.enviostate;

import com.example.demo.model.Envio;
import jakarta.persistence.Embeddable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Embeddable
public class Entregado extends EnvioEstado {
    public Entregado() {
        this.nombreEstado="ENTREGADO";
    }

    @Override
    public void procesar(Envio envio) {
        if(!(envio.getEstadoEnvio() instanceof EnRuta)) {
            log.error("Solo puede cambiar de estado En ruta a Entregado");
            throw new IllegalStateException("Solo puede cambiar de estado En ruta a Entregado");
        }else
            log.info("Se cambio a estado Entregado");
    }
}
