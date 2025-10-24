package model;

import model.enums.EstadoEnvio;
import model.paquete.Paquete;

import java.util.List;

public class Envio {
    private Long id;
    private String remitente;
    private String destinatario;
    private String direccionEntrega;
    private EstadoEnvio EstadoEnvio;
    private Boolean comprobanteEntrega;
    private List<Paquete> paquetes;
}
