package com.example.demo.util;

import com.example.demo.model.enviostate.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Utils {
    public static EnvioEstado crearEstadoDesdeNombre(String nombre){
        if (nombre == null)
            throw new IllegalArgumentException("El nombre del estado no puede ser nulo");
        if(nombre.equalsIgnoreCase("Generado"))
            return new Generado();
        else if (nombre.equalsIgnoreCase("EnAlmacen"))
            return new EnAlmacen();
        else if (nombre.equalsIgnoreCase("Cancelado"))
            return new Cancelado();
        else if (nombre.equalsIgnoreCase("Devuelto"))
            return new Devuelto();
        else if (nombre.equalsIgnoreCase("EnRuta"))
            return new EnRuta();
        else if (nombre.equalsIgnoreCase("Entregado"))
            return new Entregado();
        else
            throw new NullPointerException("Nombre invalido de conversion: " + nombre);
    }
}
