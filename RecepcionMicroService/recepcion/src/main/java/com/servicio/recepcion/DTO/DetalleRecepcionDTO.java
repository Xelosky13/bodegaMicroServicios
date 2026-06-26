package com.servicio.recepcion.DTO;

import com.servicio.recepcion.model.OrdenRecepcion;

import lombok.Data;

@Data
public class DetalleRecepcionDTO {
    private String estado;
    private Integer cantidad;
    private OrdenRecepcion orden;
    private ProductoExternoDTO producto;

}
