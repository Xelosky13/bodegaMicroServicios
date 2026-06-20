package com.servicio.recepcion.DTO;

import java.util.List;
import com.servicio.recepcion.model.OrdenRecepcion;
import lombok.Data;

@Data
public class ProveedorDTO {
    private String nombre;
    private String rut;
    private String telefono;
    private String nombreContacto;
    private List<OrdenRecepcion> ordenes;
}
