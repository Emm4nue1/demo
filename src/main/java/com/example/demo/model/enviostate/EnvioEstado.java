package com.example.demo.model.enviostate;

import com.example.demo.model.Envio;
import jakarta.persistence.Embeddable;

@Embeddable
public abstract class EnvioEstado {
    public abstract void procesar(Envio envio);
}
