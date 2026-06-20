package com.servicio.recepcion.DTO;

import com.servicio.recepcion.model.OrdenRecepcion;

import lombok.Data;

@Data
public class DetalleRecepcionDTO {
    private String estado;
    private Integer cantidad;
    private Integer producto_id;
    private OrdenRecepcion orden;

}
