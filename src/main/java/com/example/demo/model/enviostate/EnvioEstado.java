package com.example.demo.model.enviostate;

import com.example.demo.model.Envio;
import jakarta.persistence.Embeddable;

@Embeddable
public abstract class EnvioEstado {
    //definimos el campo nombre para que se guarde en la tabla
    protected String nombreEstado;
    //para que se pueda mostrar el nombre del estado
    public String getNombreEstado() {
        return nombreEstado;
    }
    //validacion de los cambio de estados
    public abstract void procesar(Envio envio);
}
